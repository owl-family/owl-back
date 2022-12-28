package com.project.owlback.studygroup.service;

import com.project.owlback.studygroup.model.StudyGroup;
import com.project.owlback.studygroup.model.StudyMember;
import com.project.owlback.studygroup.model.StudyMemberStatus;
import com.project.owlback.codereview.model.User;
import com.project.owlback.codereview.repository.UserRepository;
import com.project.owlback.studygroup.dto.StudyGroupCondition;
import com.project.owlback.studygroup.dto.req.StudyInviteReqDto;
import com.project.owlback.studygroup.repository.StudyGroupRepository;
import com.project.owlback.studygroup.repository.StudyMemberRepositoryCustom;
import com.project.owlback.studygroup.repository.StudyMemberStatusRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class StudyGroupServiceImpl implements StudyGroupService {

    private final StudyMemberRepository studyMemberRepository;
    private final StudyMemberRepositoryCustom studyMemberRepositoryCustom;
    private final StudyGroupRepository studyGroupRepository;
    private final UserRepository userRepository;
    private final StudyMemberStatusRepository studyMemberStatusRepository;

    private final StudyGroupCondition condition;

    @Override
    @Transactional
    public long joinRequest(long studyId, long userId, String message) {
        final StudyMember member = StudyMember.builder()
                .joinMessage(message)
                .build();
        final StudyGroup studyGroup = studyGroupRepository.findById(studyId).orElseThrow();
        final User user = userRepository.findById(userId).orElseThrow();
        final StudyMemberStatus status = studyMemberStatusRepository.findByDescription("승인대기");

        log.info("found studyGroup : {}", studyGroup);
        log.info("found user : {}", user);
        log.info("found status : {}", status);

        member.setStudyGroup(studyGroup);
        member.setUser(user);
        member.setStudyMemberStatus(status);

        log.info("member : {}", member);

        return studyMemberRepository.save(member).getId();
    }

    @Override
    public boolean isJoinRequest(long studyId, long userId) {
        condition.setStudyId(studyId);
        condition.setUserId(userId);
        final Optional<StudyMember> member = studyMemberRepositoryCustom.getStudyMember(condition);
        return member.isPresent();
    }

    @Override
    @Transactional
    public long joinAccept(long studyId, long userId) {
        condition.setStudyId(studyId);
        condition.setUserId(userId);
        StudyMember member = studyMemberRepositoryCustom.getStudyMember(condition).orElseThrow();
        log.info("before member : {}", member);

        StudyMemberStatus status = studyMemberStatusRepository.findByDescription("가입완료");
        log.info("status : {}", status);

        member.setStudyMemberStatus(status);
        log.info("after member : {}", member);

        return studyMemberRepository.save(member).getId();
    }

    @Override
    @Transactional
    public List<String> inviteRequest(long studyId, StudyInviteReqDto reqDto) {

        final StudyGroup studyGroup = studyGroupRepository.findById(studyId).orElseThrow();
        final StudyMemberStatus status = studyMemberStatusRepository.findByDescription("초대중");

        log.info("found studyGroup : {}", studyGroup);
        log.info("found status : {}", status);

        List<String> emails = new ArrayList<>();

        for (String email : reqDto.getEmails()) {
            StudyMember member = new StudyMember();
            member.setStudyGroup(studyGroup);
            member.setStudyMemberStatus(status);

            final User user = userRepository.findByEmail(email).orElseThrow();
            log.info("found user : {}", user);
            // 이미 초대한 유저인지 확인
            condition.setStudyId(studyId);
            condition.setUserId(user.getId());

            if (studyMemberRepositoryCustom.getStudyMember(condition).isPresent()) {
                log.info("already invite user id : {}", user.getId());
                continue;
            }
            member.setUser(user);
            log.info("member : {}", member);

            long id = studyMemberRepository.save(member).getId();
            log.info("member saved successfully id : {}", id);

            emails.add(user.getEmail());
        }

        return emails;
    }

    @Override
    public StudyGroup getStudyGroup(long studyId) {
        StudyGroup studyGroup = studyGroupRepository.findById(studyId).orElseThrow();
        log.info("found studyGroup : {}", studyGroup);

        return studyGroup;
    }

    @Override
    @Transactional
    public long inviteAccept(long studyId, long userId, String joinCode) {
        // 초대한 유저인지 판단
        condition.setStudyId(studyId);
        condition.setUserId(userId);
        condition.setDescription("초대중");
        StudyMember member = studyMemberRepositoryCustom.getStudyMember(condition).orElseThrow();
        log.info("member : [memberId : {}, nickname : {}, memberStatus : {}]", member.getId(), member.getStudyGroup().getName(), member.getStudyMemberStatus().getDescription());

        // 초대코드가 올바르지 않으면,
        Optional<StudyGroup> studyGroup = studyGroupRepository.findByIdAndJoinCode(studyId, joinCode);
        if (studyGroup.isEmpty()) {
            log.info("found studyGroup : {}", studyGroup);
            return 0L;
        }
        // 초대코드가 올바르면,
        // 멤버 정보 변경
        StudyMemberStatus status = studyMemberStatusRepository.findByDescription("가입완료");
        member.setStudyMemberStatus(status);
        log.info("member : [memberId : {}, userId : {}, studyName : {}, memberStatus : {}]",
                member.getId(), member.getUser().getId(), member.getStudyGroup().getName(), member.getStudyMemberStatus().getDescription());

        return studyMemberRepository.save(member).getId();
    }
}

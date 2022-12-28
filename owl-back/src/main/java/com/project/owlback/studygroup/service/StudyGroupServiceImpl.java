package com.project.owlback.studygroup.service;

import com.project.owlback.studygroup.model.*;
import com.project.owlback.studygroup.dto.StudyGroupCondition;
import com.project.owlback.studygroup.dto.req.StudyInviteReqDto;
import com.project.owlback.studygroup.repository.*;
import com.project.owlback.user.model.User;
import com.project.owlback.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.owlback.studygroup.dto.res.AppliedMemberRes;
import com.project.owlback.studygroup.dto.res.StudyMemberRes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
public class StudyGroupServiceImpl implements StudyGroupService {

    private final StudyMemberRepository studyMemberRepository;
    private final StudyMemberRepositoryCustom studyMemberRepositoryCustom;
    private final StudyGroupRepository studyGroupRepository;
    private final StudyMemberStatusRepository studyMemberStatusRepository;
    private final StudyGroupCondition condition;
    private final StudyStatusRepository studyStatusRepository;
    private final StudyCriteriaRepository studyCriteriaRepository;
    private final StudyJoinProcessRepository studyJoinProcessRepository;

    private final UserRepository userRepository;

    @Override
    @Transactional
    public long joinRequest(long studyId, long userId, String message) {
        final StudyMember member = StudyMember.builder()
                .joinMessage(message)
                .build();
        final StudyGroup studyGroup = studyGroupRepository.findById(studyId).orElseThrow();
        final User user = userRepository.findById(userId).orElseThrow();
        final MemberStatus status = studyMemberStatusRepository.findByDescription("승인대기");

        log.info("found studyGroup : {}", studyGroup);
        log.info("found user : {}", user);
        log.info("found status : {}", status);

        member.setStudyGroup(studyGroup);
        member.setUser(user);
        member.setMemberStatus(status);

        log.info("member : {}", member);

        return studyMemberRepository.save(member).getMemberId();
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

        MemberStatus status = studyMemberStatusRepository.findByDescription("가입완료");
        log.info("status : {}", status);

        member.setMemberStatus(status);
        log.info("after member : {}", member);

        return studyMemberRepository.save(member).getMemberId();
    }

    @Override
    @Transactional
    public List<String> inviteRequest(long studyId, StudyInviteReqDto reqDto) {

        final StudyGroup studyGroup = studyGroupRepository.findByStudyGroupId(studyId).orElseThrow();
        final MemberStatus status = studyMemberStatusRepository.findByDescription("초대중");

        log.info("found studyGroup : {}", studyGroup);
        log.info("found status : {}", status);

        List<String> emails = new ArrayList<>();

        for (String email : reqDto.getEmails()) {
            StudyMember member = new StudyMember();
            member.setStudyGroup(studyGroup);
            member.setMemberStatus(status);

            final User user = userRepository.findByEmail(email).orElseThrow();
            log.info("found user : {}", user);
            // 이미 초대한 유저인지 확인
            condition.setStudyId(studyId);
            condition.setUserId(user.getUserId());

            if (studyMemberRepositoryCustom.getStudyMember(condition).isPresent()) {
                log.info("already invite user id : {}", user.getUserId());
                continue;
            }
            member.setUser(user);
            log.info("member : {}", member);

            long id = studyMemberRepository.save(member).getMemberId();
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
        log.info("member : [memberId : {}, nickname : {}, memberStatus : {}]", member.getMemberId(), member.getStudyGroup().getName(), member.getMemberStatus().getDescription());

        // 초대코드가 올바르지 않으면,
        Optional<StudyGroup> studyGroup = studyGroupRepository.findByStudyGroupIdAndJoinCode(studyId, joinCode);
        if (studyGroup.isEmpty()) {
            log.info("found studyGroup : {}", studyGroup);
            return 0L;
        }
        // 초대코드가 올바르면,
        // 멤버 정보 변경
        MemberStatus status = studyMemberStatusRepository.findByDescription("가입완료");
        member.setMemberStatus(status);
        log.info("member : [memberId : {}, userId : {}, studyName : {}, memberStatus : {}]",
                member.getMemberId(), member.getUser().getUserId(), member.getStudyGroup().getName(), member.getMemberStatus().getDescription());

        return studyMemberRepository.save(member).getMemberId();

    }

    @Override
    public Optional<ArrayList<StudyMemberRes>> members(Long studyGroupId) {
        StudyGroup group = studyGroupRepository.findById(studyGroupId).orElse(null);
        // 해당 그룹이 존재하지 않는 그룹이면 null 리턴
        if (group == null) return Optional.empty();

        // 해당 그룹이 존재하면, study_member table에서 주어진 스터디에 해당하는 멤버 찾기
        List<StudyMember> members = studyMemberRepository.findByStudyGroup(group);

        ArrayList<StudyMemberRes> list = new ArrayList<>();
        for (StudyMember member : members) {
            User user = userRepository.findById(member.getUser().getUserId()).get();
            String nickname = user.getNickname();
            String imgFile = "no image";
            if (user.getUserImg() != null)
                imgFile = user.getUserImg().getFileUrl();
            StudyMemberRes mem = StudyMemberRes.builder()
                    .nickname(nickname)
                    .imgFile(imgFile)
                    .memberStatusId(member.getMemberStatus().getMemberStatusId())
                    .build();

            list.add(mem);
        }

        return Optional.of(list);
    }

    @Override
    public void expire(Long studyGroupId) {
        StudyGroup group = studyGroupRepository.findById(studyGroupId).orElseThrow();

        StudyStatus expireStatus = studyStatusRepository.findById(2L).get();

        group.expire(expireStatus);
        studyGroupRepository.save(group);
    }

    @Override
    public Optional<List<StudyCriteria>> criteria() {
        List<StudyCriteria> criteria = studyCriteriaRepository.findAll();

        // 기준 목록이 존재하지 않으면 null 리턴
        if (criteria.size() == 0) return Optional.empty();

        return Optional.of(criteria);
    }

    @Override
    public Optional<List<StudyJoinProcess>> joinProcesses() {
        List<StudyJoinProcess> joinProcesses = studyJoinProcessRepository.findAll();

        // 가입 방식 목록이 존재하지 않으면 null 리턴
        if (joinProcesses.size() == 0) return Optional.empty();

        return Optional.of(joinProcesses);
    }

    @Override
    public Optional<ArrayList<AppliedMemberRes>> applied(Long studyGroupId) {
        StudyGroup group = studyGroupRepository.findById(studyGroupId).orElse(null);
        // 해당 그룹이 존재하지 않는 그룹이면 null 리턴
        if (group == null) return Optional.empty();

        // 해당 그룹이 존재하면, study_member table에서 주어진 스터디에 해당하는 승인 대기중인 멤버 찾기
        MemberStatus appliedStatus = studyMemberStatusRepository.findById(2L).get();
        List<StudyMember> members = studyMemberRepository.findByStudyGroupAndMemberStatus(group, appliedStatus);

        ArrayList<AppliedMemberRes> list = new ArrayList<>();
        for (StudyMember member : members) {
            User user = userRepository.findById(member.getUser().getUserId()).get();
            String nickname = user.getNickname();
            String imgFile = "no image";
            if (user.getUserImg() != null)
                imgFile = user.getUserImg().getFileUrl();
            AppliedMemberRes mem = AppliedMemberRes.builder()
                    .userId(user.getUserId())
                    .nickname(nickname)
                    .imgFile(imgFile)
                    .joinMessage(member.getJoinMessage())
                    .build();

            list.add(mem);
        }

        return Optional.of(list);
    }
}

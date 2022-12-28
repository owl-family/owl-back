package com.project.owlback.studygroup.service;

import com.project.owlback.studygroup.dto.*;
import com.project.owlback.studygroup.dto.res.AppliedMemberRes;
import com.project.owlback.studygroup.dto.res.StudyMemberRes;
import com.project.owlback.studygroup.repository.*;
import com.project.owlback.user.dto.User;
import com.project.owlback.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudyGroupServiceImpl implements StudyGroupService {

    private final StudyGroupRepository studyGroupRepository;
    private final StudyMemberRepository studyMemberRepository;
    private final StudyStatusRepository studyStatusRepository;
    private final StudyCriteriaRepository studyCriteriaRepository;
    private final StudyJoinProcessRepository studyJoinProcessRepository;
    private final StudyMemberStatusRepository studyMemberStatusRepository;
    private final UserRepository userRepository;

    @Override
    public Optional<ArrayList<StudyMemberRes>> members(Long studyGroupId) {
        StudyGroup group = studyGroupRepository.findById(studyGroupId).orElse(null);
        // 해당 그룹이 존재하지 않는 그룹이면 null 리턴
        if(group == null) return Optional.empty();

        // 해당 그룹이 존재하면, study_member table에서 주어진 스터디에 해당하는 멤버 찾기
        List<StudyMember> members = studyMemberRepository.findByStudyGroup(group);

        ArrayList<StudyMemberRes> list = new ArrayList<>();
        for(StudyMember member : members){
            User user = userRepository.findById(member.getUserId()).get();
            String nickname = user.getNickname();
            String imgFile = "no image";
            if(user.getUserImg() != null)
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
        if(criteria.size() == 0) return Optional.empty();

        return Optional.of(criteria);
    }

    @Override
    public Optional<List<StudyJoinProcess>> joinProcesses() {
        List<StudyJoinProcess> joinProcesses = studyJoinProcessRepository.findAll();

        // 가입 방식 목록이 존재하지 않으면 null 리턴
        if(joinProcesses.size() == 0) return Optional.empty();

        return Optional.of(joinProcesses);
    }

    @Override
    public Optional<ArrayList<AppliedMemberRes>> applied(Long studyGroupId) {
        StudyGroup group = studyGroupRepository.findById(studyGroupId).orElse(null);
        // 해당 그룹이 존재하지 않는 그룹이면 null 리턴
        if(group == null) return Optional.empty();

        // 해당 그룹이 존재하면, study_member table에서 주어진 스터디에 해당하는 승인 대기중인 멤버 찾기
        StudyMemberStatus appliedStatus = studyMemberStatusRepository.findById(2L).get();
        List<StudyMember> members = studyMemberRepository.findByStudyGroupAndMemberStatus(group, appliedStatus);

        ArrayList<AppliedMemberRes> list = new ArrayList<>();
        for(StudyMember member : members){
            User user = userRepository.findById(member.getUserId()).get();
            String nickname = user.getNickname();
            String imgFile = "no image";
            if(user.getUserImg() != null)
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

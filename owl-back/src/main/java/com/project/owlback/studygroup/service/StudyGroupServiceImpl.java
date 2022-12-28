package com.project.owlback.studygroup.service;

import com.project.owlback.studygroup.dto.StudyGroup;
import com.project.owlback.studygroup.dto.StudyMember;
import com.project.owlback.studygroup.dto.res.StudyMemberRes;
import com.project.owlback.studygroup.repository.StudyGroupRepository;
import com.project.owlback.studygroup.repository.StudyMemberRepository;
import com.project.owlback.user.dto.User;
import com.project.owlback.user.repository.UserImgRepository;
import com.project.owlback.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudyGroupServiceImpl implements StudyGroupService {

    private final StudyGroupRepository studyGroupRepository;
    private final StudyMemberRepository studyMemberRepository;
    private final UserRepository userRepository;

    @Override
    public Optional<ArrayList<StudyMemberRes>> memberList(Long studyGroupId) {
        StudyGroup group = studyGroupRepository.findById(studyGroupId).orElse(null);
        // 해당 그룹이 존재하지 않는 그룹이면 null 리턴
        if(group == null) return null;

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
}

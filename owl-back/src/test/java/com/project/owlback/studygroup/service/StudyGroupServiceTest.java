package com.project.owlback.studygroup.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.owlback.EnableMockMvc;
import com.project.owlback.studygroup.model.StudyGroup;
import com.project.owlback.studygroup.model.StudyMember;
import com.project.owlback.studygroup.dto.req.StudyInviteReqDto;
import com.project.owlback.studygroup.dto.req.StudyJoinReqDto;
import com.project.owlback.studygroup.repository.StudyGroupRepository;
import com.project.owlback.studygroup.repository.StudyMemberStatusRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@EnableMockMvc
@Transactional
@SpringBootTest
class StudyGroupServiceTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StudyGroupService studyGroupService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private StudyMemberRepository studyMemberRepository;

    @Autowired
    private StudyMemberStatusRepository studyMemberStatusRepository;

    @Autowired
    private StudyGroupRepository studyGroupRepository;


    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("스터디_가입_요청_성공")
    public void JoinRequest() throws Exception {
        //given
        StudyJoinReqDto reqDto = new StudyJoinReqDto();
        String message = "테스트 메세지";
        reqDto.setMessage(message);
        long studyId = 2L;
        long userId = 4L;

        //when
        final long id = studyGroupService.joinRequest(studyId, userId, reqDto.getMessage());

        //then
        final Optional<StudyMember> member = studyMemberRepository.findById(id);
        assertThat(member.isPresent()).isTrue();
        assertThat(member.get().getJoinMessage()).isEqualTo(message);
    }

    @Test
    @DisplayName("스터디_가입_요청_실패")
    public void JoinRequestFail() throws Exception {
        //given
        StudyJoinReqDto reqDto = new StudyJoinReqDto();
        String message = "테스트 메세지";
        reqDto.setMessage(message);
        long studyId = 0L;
        long userId = 4L;

        //when

        //then
        assertThatThrownBy(() -> studyGroupService.joinRequest(studyId, userId, reqDto.getMessage()))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("MVC_스터디_가입_요청_성공")
    public void JoinRequestMvc() throws Exception {
        //given
        Map<String, String> json = new HashMap<>();
        json.put("message", "테스트 메세지");

        //when
        mockMvc.perform(
                        post("/api/studies/2/join/4")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(json)))
                //then
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("스터디_가입_승인_성공")
    public void JoinAccept() throws Exception {
        //given
        long studyId = 2L;
        long userId = 4L;
        JoinRequest();

        //when
        long id = studyGroupService.joinAccept(studyId, userId);

        //then
        final Optional<StudyMember> member = studyMemberRepository.findById(id);
        assertThat(member.isPresent()).isTrue();
        assertThat(member.get().getStudyMemberStatus().getDescription()).isEqualTo("가입완료");

    }

    @Test
    @DisplayName("스터디_가입_승인_실패")
    public void JoinAcceptFail() throws Exception {
        //given
        long studyId = 0L;
        long userId = 4L;

        //when

        //then
        assertThatThrownBy(() -> studyGroupService.joinAccept(studyId, userId))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("MVC_스터디_가입_승인_성공")
    public void JoinAcceptMvc() throws Exception {
        //given
        String url = "/api/studies/2/join/4";
        long studyId = 2L;
        long userId = 4L;
        JoinRequest();

        //when
        mockMvc.perform(MockMvcRequestBuilders.put(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8"))
                //then
                .andExpect(status().isOk());

    }

    @Test
    @DisplayName("스터디_가입_초대_성공")
    public void InviteRequest() throws Exception {
        //given
        long studyId = 2L;

        List<String> inputs = new ArrayList<>();
        inputs.add("dnzma13@naver.com");
        inputs.add("dnzma13@gmail.com");

        StudyInviteReqDto reqDto = new StudyInviteReqDto();
        reqDto.setEmails(inputs);

        //when
        final List<String> emails = studyGroupService.inviteRequest(studyId, reqDto);
        final StudyGroup studyGroup = studyGroupService.getStudyGroup(studyId);
        emailService.sendInviteEmail(emails, studyGroup);

        //then
        for (String email : emails) {
            final Optional<StudyMember> member = studyMemberRepository.findByUserEmail(email);

            assertThat(member.isPresent()).isTrue();
            assertThat(member.get().getStudyMemberStatus().getDescription()).isEqualTo("초대중");
        }

    }

    @Test
    @DisplayName("스터디_가입_초대_실패")
    public void InviteRequestFail() throws Exception {
        //given
        long studyId = 0L;

        List<String> inputs = new ArrayList<>();
        inputs.add("dnzma13@naver.com");

        StudyInviteReqDto reqDto = new StudyInviteReqDto();
        reqDto.setEmails(inputs);

        //when

        //then
        assertThatThrownBy(() -> studyGroupService.inviteRequest(studyId, reqDto))
                .isInstanceOf(NoSuchElementException.class);

    }

    @Test
    @DisplayName("MVC_스터디_가입_초대_성공")
    public void InviteRequestMvc() throws Exception {
        //given
        String url = "/api/studies/2/invite";

        HashMap<String, List<String>> json = new HashMap<>();
        List<String> inputs = new ArrayList<>();
        inputs.add("dnzma13@naver.com");
        inputs.add("dnzma13@gmail.com");
        json.put("emails", inputs);

        //when
        mockMvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(json)))
                //then
                .andExpect(status().isCreated());

    }

    @Test
    @DisplayName("스터디_가입_초대_승인_성공")
    public void InviteAccept() throws Exception {
        //given
        long studyId = 2L;
        long userId = 4L;
        String joinCode = "ABCDE12345";
        InviteRequest();

        //when
        long id = studyGroupService.inviteAccept(studyId, userId, joinCode);

        //then
        final Optional<StudyMember> member = studyMemberRepository.findById(id);
        assertThat(member.isPresent()).isTrue();
        assertThat(member.get().getStudyMemberStatus().getDescription()).isEqualTo("가입완료");

    }

    @Test
    @DisplayName("스터디_가입_초대_승인_실패")
    public void InviteAcceptFail() throws Exception {
        //given
        long studyId = 2L;
        long userId = 4L;
        String joinCode = "ABCDE1234";
        InviteRequest();

        //when
        long id = studyGroupService.inviteAccept(studyId, userId, joinCode);

        //then
        assertThatThrownBy(() -> studyGroupRepository.findById(id).orElseThrow())
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("MVC_스터디_가입_초대_승인_성공")
    public void InviteAcceptMvc() throws Exception {
        //given
        String url = "/api/studies/2/invite/4/ABCDE12345";
        InviteRequest();

        //when
        mockMvc.perform(MockMvcRequestBuilders.put(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8"))
                //then
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("MVC_스터디_가입_초대_승인_실패")
    public void InviteAcceptMvcFail() throws Exception {
        //given
        String url = "/api/studies/2/invite/4/ABCDE12345";

        //when
        mockMvc.perform(MockMvcRequestBuilders.put(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8"))
                //then
                .andExpect(status().isNoContent());
    }

}

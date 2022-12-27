package com.project.owlback.studygroup.service;

import com.project.owlback.codereview.model.StudyGroup;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.mail.Message;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender emailSender;

    @Override
    public void sendInviteEmail(List<String> emails, final StudyGroup studyGroup) throws Exception {
        MimeMessage message = emailSender.createMimeMessage();

        message.setFrom("owl-admin@gmail.com");
        message.setSubject("OWL 스터디 - " + studyGroup.getName() + " 초대 메일");
        message.setText(createInviteEmail(studyGroup), "UTF-8", "html");
        for (String email : emails) {
            message.addRecipients(Message.RecipientType.TO, String.valueOf(new InternetAddress(email)));

            log.info("email message : {}", message);
            emailSender.send(message);
        }

    }

    private String createInviteEmail(final StudyGroup studyGroup) throws Exception {
        StringBuilder msg = new StringBuilder();

        msg.append("<div style='margin:20px;'>")
                .append("<p>안녕하세요. owl 스터디 " + studyGroup.getName() + " 초대 메일입니다.</p>")
                .append("<p>아래 코드를 복사해 입력해주세요.</p>")
                .append("<p>감사합니다.</p>")
                .append("<br>")
                .append("<div align='center' style='border:1px solid black; font-family:verdana';>")
                .append("<div style='font-size:130%'>")
                .append("CODE : <strong>" + studyGroup.getJoinCode() + "</strong>")
                .append("<div>")
                .append("</div>");

        return msg.toString();
    }
}

package com.project.owlback.user.service;

import jakarta.mail.Message;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class EmailServiceImpl implements EmailService{
    // 인증 코드 길이
    public static final int CODE_LENGTH = 20;
    // 인증 코드
    public static final String CODE = createKey(CODE_LENGTH);

    JavaMailSender emailSender;

    @Autowired
    public EmailServiceImpl(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    public static String createKey(int length) {
        StringBuilder key = new StringBuilder();
        Random rnd = new Random();

        for (int i = 0; i < length; i++) {
            int index = rnd.nextInt(3); // 0~2 까지 랜덤

            switch (index) {
                case 0: //  a~z  (ex. 1+97=98 => (char)98 = 'b')
                    key.append((char) ((int) (rnd.nextInt(26)) + 97));
                    break;
                case 1: //  A~Z
                    key.append((char) ((int) (rnd.nextInt(26)) + 65));
                    break;
                case 2: // 0~9
                    key.append((rnd.nextInt(10)));
                    break;
            }
        }
        return key.toString();
    }

    private String createSignupMessage() throws Exception{
        StringBuilder msg = new StringBuilder();

        msg.append("<div style='margin:20px;'>")
                .append("<p>안녕하세요. owl 회원가입 인증 메일입니다.</p>")
                .append("<p>아래 코드를 복사해 입력해주세요.</p>")
                .append("<p>감사합니다.</p>")
                .append("<br>")
                .append("<div align='center' style='border:1px solid black; font-family:verdana';>")
                .append("<div style='font-size:130%'>")
                .append("CODE : <strong>" + CODE + "</strong>")
                .append("<div>")
                .append("</div>");

        return msg.toString();
    }

    private String createPasswordMessage() throws Exception{
        StringBuilder msg = new StringBuilder();

        msg.append("<div style='margin:20px;'>")
                .append("<p>안녕하세요. owl 임시 비밀번호 발송 메일입니다.</p>")
                .append("<p>아래 임시 비밀번호를 사용해주세요.</p>")
                .append("<p>감사합니다.</p>")
                .append("<br>")
                .append("<div align='center' style='border:1px solid black; font-family:verdana';>")
                .append("<div style='font-size:130%'>")
                .append("CODE : <strong>" + CODE + "</strong>")
                .append("<div>")
                .append("</div>");

        return msg.toString();
    }

    @Override
    public String sendSignupEmail(String email) throws Exception {
        MimeMessage message = emailSender.createMimeMessage();

        message.setFrom("owl-admin@gmail.com");
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
        message.setSubject("OWL 회원가입 인증 메일");
        message.setText(createSignupMessage(), "UTF-8", "html");
        emailSender.send(message);

        return CODE;
    }

    @Override
    public String sendPasswordEmail(String email) throws Exception {
        MimeMessage message = emailSender.createMimeMessage();

        message.setFrom("owl-admin@gmail.com");
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
        message.setSubject("OWL 임시 비밀번호 발송");
        message.setText(createSignupMessage(), "UTF-8", "html");
        emailSender.send(message);

        return CODE;
    }
}

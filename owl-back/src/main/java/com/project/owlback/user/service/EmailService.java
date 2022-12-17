package com.project.owlback.user.service;

public interface EmailService {
    String sendSignupEmail(String to) throws Exception;

    String sendPasswordEmail(String email) throws Exception;

//    String sendPasswordEmail(String email);
}

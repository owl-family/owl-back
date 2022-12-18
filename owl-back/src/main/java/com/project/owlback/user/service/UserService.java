package com.project.owlback.user.service;


import com.project.owlback.user.dto.CreateUserReq;
import com.project.owlback.user.dto.User;

import java.util.Optional;

public interface UserService {
    boolean findByEmail(String email);

    boolean signupEmail(String email);

    boolean findPassword();

    User updatePassword(User user, String password);

    Optional<User> findByEmailAndName(User user);

    User findByUserId(long userId);


    void createUser(CreateUserReq createUserReq);

    boolean findByNickname(String nickname);
}

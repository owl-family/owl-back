package com.project.owlback.user.service;


import com.project.owlback.user.dto.CreateUserReq;
import com.project.owlback.user.dto.UpdateInfo;
import com.project.owlback.user.dto.User;

import java.util.Optional;

public interface UserService {
    boolean findByEmail(String email);

    boolean signupEmail(String email);

    boolean findPassword();

    User updatePassword(User user, String password);

    Optional<User> findByEmailAndName(User user);

    User findByUserId(Long userId);

    void createUser(CreateUserReq createUserReq);

    boolean findByNickname(String nickname);

    void updateInfo(Long userId, UpdateInfo updateInfo);

    void deleteUser(Long userId);
}

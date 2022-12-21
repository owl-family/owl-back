package com.project.owlback.user.service;


import com.project.owlback.user.dto.UserImg;
import com.project.owlback.user.dto.req.PostUserReq;
import com.project.owlback.user.dto.User;
import com.project.owlback.user.dto.req.PutUserInfoReq;

import java.util.Optional;

public interface UserService {
    boolean findByEmail(String email);

    boolean signupEmail(String email);

    boolean findPassword();

    User updatePassword(User user, String password);

    Optional<User> findByEmailAndName(User user);

    User findByUserId(Long userId);

    void createUser(PostUserReq postUserReq);

    boolean findByNickname(String nickname);

    void updateInfo(Long userId, PutUserInfoReq putUserInfoReq);

    void deleteUser(Long userId);

    UserImg findUserImgByUserId(Long userId);
}

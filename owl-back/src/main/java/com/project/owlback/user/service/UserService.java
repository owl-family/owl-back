package com.project.owlback.user.service;


import com.project.owlback.user.model.UserImg;
import com.project.owlback.user.dto.req.PostUserReq;
import com.project.owlback.user.model.User;
import com.project.owlback.user.dto.req.PutUserInfoReq;
import com.project.owlback.user.dto.req.UserFindPasswordDto;

import java.util.Optional;

public interface UserService {
    boolean findByEmail(String email);

    User updatePassword(User user, String password);

    Optional<User> findByEmailAndName(UserFindPasswordDto userFindPasswordDto);

    User findByUserId(Long userId);

    void createUser(PostUserReq postUserReq);

    boolean findByNickname(String nickname);

    void updateInfo(Long userId, PutUserInfoReq putUserInfoReq);

    void deleteUser(Long userId);

    UserImg findUserImgByUserId(Long userId);
}

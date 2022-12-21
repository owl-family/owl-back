package com.project.owlback.user.service;
import com.project.owlback.user.dto.UserImg;

public interface UserImgService {


    public void save(UserImg userImg);
    public void save(Long userId,UserImg uSerImg);
}

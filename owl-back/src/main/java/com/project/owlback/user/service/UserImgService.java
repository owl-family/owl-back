package com.project.owlback.user.service;
import com.project.owlback.user.model.UserImg;

public interface UserImgService {


    public void save(UserImg userImg);
    public void save(Long userId,UserImg uSerImg);
}

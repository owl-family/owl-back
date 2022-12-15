package com.project.owlback.user.service;

import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
    @Override
    public boolean checkEmail(String email) {
        return false;
    }
}

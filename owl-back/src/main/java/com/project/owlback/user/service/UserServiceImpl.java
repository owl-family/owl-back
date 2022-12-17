package com.project.owlback.user.service;

import com.project.owlback.user.dto.User;
import com.project.owlback.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{
    private static UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean findByEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    @Override
    public boolean signupEmail(String email) {
        return false;
    }

    @Override
    public boolean findPassword() {
        return false;
    }

    @Override
    public User updatePassword(User user, String password) {
        user.updatePassword(password);
        return userRepository.save(user);
    }

    @Override
    public Optional<User> findByEmailAndName(User user) {
        return userRepository.findByEmailAndName(user.getEmail(), user.getName());
    }

    @Override
    public User findByUserId(long userId) {
        return userRepository.findById(userId).get();
    }

}

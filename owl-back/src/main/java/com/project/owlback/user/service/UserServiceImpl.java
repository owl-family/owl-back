package com.project.owlback.user.service;

import com.project.owlback.user.dto.CreateUserReq;
import com.project.owlback.user.dto.UpdateInfo;
import com.project.owlback.user.dto.User;
import com.project.owlback.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private PasswordEncoder passwordEncoder;
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


    @Override
    public void createUser(CreateUserReq createUserReq) {

        User user = User.builder()
                .name(createUserReq.getName())
                .nickname(createUserReq.getNickname())
                .email(createUserReq.getEmail())
                .password(passwordEncoder.encode(createUserReq.getPassword()))
                .status(2)
                .build();

        userRepository.save(user);
    }

    @Override
    public boolean findByNickname(String nickname) {
        return userRepository.findByNickname(nickname).isPresent();
    }

    @Override
    public User updateInfo(User user, UpdateInfo updateInfo) {
        user.updateInfo(updateInfo);
        return userRepository.save(user);
    }

    @Override
    public User deleteUser(User user) {
        user.deleteUser();
        return userRepository.save(user);
    }


}

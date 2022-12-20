package com.project.owlback.user.service;

import com.project.owlback.goal.dto.Goal;
import com.project.owlback.goal.repository.GoalRepository;
import com.project.owlback.user.dto.CreateUserReq;
import com.project.owlback.user.dto.UpdateInfo;
import com.project.owlback.user.dto.User;
import com.project.owlback.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    private UserRepository userRepository;
    private GoalRepository goalRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, GoalRepository goalRepository) {
        this.userRepository = userRepository;
        this.goalRepository = goalRepository;
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
    @Transactional
    public User updatePassword(User user, String password) {
        user.updatePassword(password);
        return userRepository.save(user);
    }

    @Override
    public Optional<User> findByEmailAndName(User user) {
        return userRepository.findByEmailAndName(user.getEmail(), user.getName());
    }

    @Override
    public User findByUserId(Long userId) {
        return userRepository.findById(userId).get();
    }


    @Override
    @Transactional
    public void createUser(CreateUserReq createUserReq) {

        User user = User.builder()
                .name(createUserReq.getName())
                .nickname(createUserReq.getNickname())
                .email(createUserReq.getEmail())
                .password(passwordEncoder.encode(createUserReq.getPassword()))
                .build();
        log.info("new user : {}", user);
        Long userId = userRepository.save(user).getUserId();
        log.info("userId : {}", userId);

        // 회원가입시, 유저 1명당 1개의 goal 생성
        Goal goal = Goal.builder()
                .user(user)
                .build();

        log.info("goal : {}", goal);
        goalRepository.save(goal);

    }

    @Override
    public boolean findByNickname(String nickname) {
        return userRepository.findByNickname(nickname).isPresent();
    }

    @Override
    @Transactional
    public void updateInfo(Long userId, UpdateInfo updateInfo) {
        User user = userRepository.findById(userId).orElseThrow();
        user.updateInfo(updateInfo);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow();
        user.deleteUser();
        userRepository.save(user);
    }


}

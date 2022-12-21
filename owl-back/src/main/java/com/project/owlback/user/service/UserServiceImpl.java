package com.project.owlback.user.service;

import com.project.owlback.goal.dto.Goal;
import com.project.owlback.goal.repository.GoalRepository;
import com.project.owlback.user.dto.UserImg;
import com.project.owlback.user.dto.req.PostUserReq;
import com.project.owlback.user.dto.User;
import com.project.owlback.user.dto.req.PutUserInfoReq;
import com.project.owlback.user.repository.UserImgRepository;
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

    private UserImgRepository userImgRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, GoalRepository goalRepository,UserImgRepository userImgRepository) {
        this.userRepository = userRepository;
        this.goalRepository = goalRepository;
        this.userImgRepository=userImgRepository;
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
    public void createUser(PostUserReq postUserReq) {

        User user = User.builder()
                .name(postUserReq.getName())
                .nickname(postUserReq.getNickname())
                .email(postUserReq.getEmail())
                .password(passwordEncoder.encode(postUserReq.getPassword()))
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
    public void updateInfo(Long userId, PutUserInfoReq putUserInfoReq) {
        User user = userRepository.findById(userId).orElseThrow();
        user.updateInfo(putUserInfoReq);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow();
        user.deleteUser();
        userRepository.save(user);
    }

    @Override
    public UserImg findUserImgByUserId(Long userId) {
        User user = userRepository.findById(userId).orElseThrow();
        UserImg userImg = user.getUserImg();
        return userImg;

    }


}

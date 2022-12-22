package com.project.owlback.user.service;

import com.project.owlback.goal.dto.Goal;
import com.project.owlback.goal.repository.GoalRepository;
import com.project.owlback.user.dto.Role;
import com.project.owlback.user.dto.UserImg;
import com.project.owlback.user.dto.req.PostUserReq;
import com.project.owlback.user.dto.User;
import com.project.owlback.user.dto.req.PutUserInfoReq;
import com.project.owlback.user.dto.req.UserFindPasswordDto;
import com.project.owlback.user.repository.UserImgRepository;
import com.project.owlback.user.repository.UserRepository;
import com.project.owlback.user.repository.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final GoalRepository goalRepository;

    private final UserImgRepository userImgRepository;

    private final UserRoleRepository userRoleRepository;

    @Override
    public boolean findByEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }


    @Override
    @Transactional
    public User updatePassword(User user, String password) {
        user.updatePassword(password);
        return userRepository.save(user);
    }

    @Override
    public Optional<User> findByEmailAndName(UserFindPasswordDto userFindPasswordDto) {
        return userRepository.findByEmailAndName(userFindPasswordDto.getEmail(), userFindPasswordDto.getName());
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

        // 회원가입시, 유저에게 ROLE_USER 권한 부여
        Role role = Role.builder()
                .role("ROLE_USER")
                .userId(userId)
                .build();

        log.info("goal : {}", goal);
        goalRepository.save(goal);
        userRoleRepository.save(role);

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

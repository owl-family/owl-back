package com.project.owlback.user.service;

import com.project.owlback.user.dto.User;
import com.project.owlback.user.dto.UserImg;
import com.project.owlback.user.repository.UserImgRepository;
import com.project.owlback.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(readOnly = true)
public class UserImgServiceImpl implements UserImgService {
    @Autowired
    UserImgRepository userImgRepository;
    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public void save(UserImg userImg) {
        UserImg f = new UserImg();
        f.setFileName(userImg.getFileName());
        f.setFileOriginalName(userImg.getFileOriginalName());
        f.setFileUrl(userImg.getFileUrl());

        Long imgId = userImgRepository.save(f).getImgId();
        f.setImgId(imgId);

        System.out.println("imgId: "+imgId);
    }

    @Override
    @Transactional
    public void save(Long userId,UserImg userImg) {
        UserImg f = new UserImg();
        f.setFileName(userImg.getFileName());
        f.setFileOriginalName(userImg.getFileOriginalName());
        f.setFileUrl(userImg.getFileUrl());

        Long imgId = userImgRepository.save(f).getImgId();
        f.setImgId(imgId);

        System.out.println("imgId: "+imgId);

        // user의 img를 새로 변경한 img로 변경
        User user = userRepository.findById(userId).orElseThrow();
        user.updateImg(f);

    }
}

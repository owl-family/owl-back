package com.project.owlback.user.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.project.owlback.user.repository.UserRepository;

@Service
// authenticate 메소드 실행시 필요
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userRepository.findByEmail(username)
				.map(this::createUserDetails)
				.orElseThrow(() -> new UsernameNotFoundException("해당하는 유저를 찾을 수 없습니다."));
	}
	
	// 해당 User 데이터가 존재하면 userDetails 객체로 만들어서 리턴
	private UserDetails createUserDetails(com.project.owlback.user.dto.User user) {
		System.out.println(user.getUsername());
		return new User(user.getUsername(), user.getPassword(), user.getAuthorities());
	}

}

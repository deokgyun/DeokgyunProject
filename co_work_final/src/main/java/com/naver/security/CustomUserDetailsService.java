package com.naver.security;

import java.util.ArrayList;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.naver.cowork.domain.Member;
import com.naver.cowork.mybatis.mapper.MemberMapper;

public class CustomUserDetailsService implements UserDetailsService {
	private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);

	// 교체 : mapper 클래스로 세팅 이후
	@Autowired
	private MemberMapper dao;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		logger.info("username은 로그인시 입력한 값 : " + username);
		System.out.println("username은 로그인시 입력한 값 : " + username);
				Member users = dao.isId(username);
		if (users == null) {
			logger.info("username " + username + " not found");
			throw new UsernameNotFoundException("username " + username + " not found");

		}
		
		Collection<SimpleGrantedAuthority> roles = new ArrayList<SimpleGrantedAuthority>();
		
		roles.add(new SimpleGrantedAuthority(users.getUSER_IS_ADMIN()));

		System.out.println(users.getUSER_IS_ADMIN());
		System.out.println(username);
		System.out.println(roles.toString());
		System.out.println(users.getUSER_PASSWORD());
		UserDetails user = new User(username, users.getUSER_PASSWORD(), roles);
		return user;
	}
}
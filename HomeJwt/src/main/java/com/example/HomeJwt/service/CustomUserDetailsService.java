package com.example.HomeJwt.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.HomeJwt.exception.CUserNotFoundException;
import com.example.HomeJwt.repository.UserRepository;

import java.util.Collections;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {
   
	//JwtTokenProvider가 제공한 사용자 정보로 DB에서 알맞은 사용자 정보를 가져와 UserDetails생성
    private final UserRepository UserRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String userPk) throws UsernameNotFoundException {
        return UserRepository.findById(Long.parseLong(userPk))
                .orElseThrow(CUserNotFoundException::new);
    }
}

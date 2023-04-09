package com.example.HomeJwt.dto;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.HomeJwt.domain.Users;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserLoginRequestDto {
      //로그인 시 요청하는 dto들 
	private String email;
	private String password;
	
	public Users toUser(PasswordEncoder passwordEncoder) {
	   return Users.builder()
			   .email(email)
			   .password(password)
			   .build();
	}
}

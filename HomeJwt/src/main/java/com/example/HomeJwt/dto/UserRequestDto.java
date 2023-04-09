package com.example.HomeJwt.dto;

import org.springframework.security.core.userdetails.User;

import com.example.HomeJwt.domain.Users;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDto {
	
	private String email;
	private String name;
	private String nickname;
	
	public Users toEntity() {
		return Users.builder()
				.email(email)
				.name(name)
			     .nickname(nickname)
			     .build();
	}

}

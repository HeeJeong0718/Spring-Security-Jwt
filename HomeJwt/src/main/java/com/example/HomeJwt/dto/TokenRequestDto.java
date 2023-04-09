package com.example.HomeJwt.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TokenRequestDto {
     //만료된 acceessToken을 다시 받기위해서 받
	Long id;
	String accessToken;
	String refreshToken;
	
	
	@Builder
	public TokenRequestDto(Long id ,String accessToken, String refreshToken) {
		this.id = id;
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
	}
}

package com.example.HomeJwt.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class RefreshToken {
    
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long userkey;
	private String token;
	
	public RefreshToken updateToken(String token) {
		this.token = token;
		return this; 
	}
	
	@Builder
	public RefreshToken(Long userkey , String token) {
		this.userkey = userkey;
		this.token = token; 
	}
}

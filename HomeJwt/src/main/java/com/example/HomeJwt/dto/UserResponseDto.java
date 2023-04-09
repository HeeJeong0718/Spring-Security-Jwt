package com.example.HomeJwt.dto;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;

import com.example.HomeJwt.domain.Users;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
//유저전체조회
public class UserResponseDto {
   private final Long userId;
   private final String email;
   private final String name;
   private final String nickname;
   private List<String> roles;
   private Collection<? extends GrantedAuthority> authorieis;
   
   
   public UserResponseDto(Users user) {
	   this.userId = user.getUserId();
	   this.email = user.getEmail();
	   this.name = user.getEmail();
	   this.nickname = user.getNickname();
	   this.roles = user.getRoles();
	   this.authorieis = user.getAuthorities();
   }
}

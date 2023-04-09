package com.example.HomeJwt.dto;

import java.util.Collections;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.HomeJwt.domain.Users;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserSignUpRequestDto {
   // private Long userId;
	private String email;
	private String password;
	@Setter
	private String name;
	private String nickname;
	private String provider;
	
	
	@Builder
	public UserSignUpRequestDto(String email, String password,String name, String nickname, String provider) {
		this.email = email;
		this.password = password;
		this.name  =name; 
		this.nickname = nickname;
		this.provider = provider;
	}
	
	/*public Users ToEntity(222) {
			 return Users.builder()
						//.userId(userId)
						.email(email)
						//.password(passwordEncoder.encode(password))
						.nickname(nickname)
						.name(name)
						.roles(Collections.singletonList("ROLE_ADMIN"))
						.build();	
		 */
	  
	public Users ToEntity(PasswordEncoder passwordEncoder) {
	if(name.equals("admin")) {
		return Users.builder()
				//.userId(userId)
				.email(email)
				.password(passwordEncoder.encode(password))
				.nickname(nickname)
				.name(name)
				.roles(Collections.singletonList("ROLE_ADMIN"))
				.build();
	}else {
		return Users.builder()
				//.userId(userId)
				.email(email)
				.password(passwordEncoder.encode(password))
				.nickname(nickname)
				.name(name)
				.roles(Collections.singletonList("ROLE_USER"))
				.build();
	   }
		
	}
}

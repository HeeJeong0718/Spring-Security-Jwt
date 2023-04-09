package com.example.HomeJwt.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Builder
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Users implements UserDetails {
     
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;
	private String password;
	private String email;
	@Setter
	private String name;
	private String nickname;
	private String provider;
	
	//유저 수정
	public void updateNickname(String nickname) {
		this.nickname = nickname;
	}
	
	
	 @ElementCollection(fetch = FetchType.EAGER)
	    @Builder.Default
	    private List<String> roles = new ArrayList<>();
	 
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		 return this.roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
	}
	   @Override
	    public String getPassword() {
	        return this.password;
	    }
	//security에서 사용하는 회원구분 id
	@Override
	public String getUsername() {
		  return String.valueOf(this.userId);
	}
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return false;
	}
	
}

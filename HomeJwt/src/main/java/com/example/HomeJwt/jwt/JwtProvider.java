package com.example.HomeJwt.jwt;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import com.example.HomeJwt.domain.Users;
import com.example.HomeJwt.dto.TokenDto;
import com.example.HomeJwt.exception.CRefreshTokenException;
import com.example.HomeJwt.repository.UserRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Component
@Slf4j
public class JwtProvider {
    /*
     * JwtToken을 생성,인증,권한부여,유효성검사,pk 추출등의 다양한 기능을 제공하는 클래스
     */
	
	
	private String secretKey = "webfirewood";
	private String ROLES = "roles";
	//private final Long accessTokenValid = 3600000L;
	private final Long accessTokenValid =  60000L;
	//private final Long refreshTokenValid = 86400000L;
	private final Long refreshTokenValid = 120000L;
	private final UserDetailsService userDetailService;
	private final UserRepository UserRepository;
	
	@PostConstruct
	protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes(StandardCharsets.UTF_8));
	}
	
	//jwt생성
	  public TokenDto createToken(Long userPk, List<String>roles) {
		  Claims claims = Jwts.claims().setSubject(String.valueOf(userPk));
		  claims.put(ROLES, roles);
		  Optional<Users>users = UserRepository.findById(userPk);
		  //생성날짜 , 만료날짜를 위한 Date
		  Date now = new Date();  
		  String accessToken = Jwts.builder()
				  .setClaims(claims)
				  .setIssuedAt(now)
				  .setExpiration(new Date(now.getTime() + accessTokenValid))
				  .signWith(SignatureAlgorithm.HS256, secretKey) //암호화 알고리즘 ,secret값
				  .compact();
		  
		  //refreshToken 생성
		  String refreshToken = Jwts.builder()
				 .setExpiration(new Date(now.getTime() + refreshTokenValid))
				 .signWith(SignatureAlgorithm.HS256, secretKey)
				 .compact();
		 
		  return TokenDto.builder()
				  .grantType("Bearer")
				  .accessToken(accessToken)
				  .refreshToken(refreshToken)
				  .accessTokenExpireDate(accessTokenValid)
				  .refreshTokenExpireDate(refreshTokenValid)
				  .id(userPk)
				  .username(users.get().getName())
	               .build();
	  }
	//Jwt로 인증정보 조회 ->인증 성공시 SecurityContextHolder에 저장할 Authentication객체 생성
	  public Authentication getAuthentication(String token) {
		  Claims claims = parseClaims(token);
		  
		  UserDetails userDetails = userDetailService.loadUserByUsername(claims.getSubject());
		  return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
	  }
	  //jwt 토큰 복호화해서 가져오기  
	  private Claims parseClaims(String token) {
		  try {
			  return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
		  }catch(ExpiredJwtException e) {
			  return e.getClaims();
		  }
	  }
	  //Header에서 Token parsing 
	  public String resolveToken(HttpServletRequest request) {
		  return request.getHeader("X-AUTH-TOKEN");
		  // String headerAuth = request.getHeader("Authorization");
	  }
	  public boolean isExpiredToken(String token) {
		  try {
			  System.out.println("!!!!!");
			  Jws<Claims> claims = Jwts.parser()
					  .setSigningKey(secretKey)
					  .parseClaimsJws(token);
			  return claims.getBody().getExpiration().after(new Date());
		  }catch(Exception e) {
			  return false;
		  }
	  }
	  
	  //JWT 유효성검사 
	  public boolean validationType(String token) {
		  try {
			  Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
			  return true;
	        } catch (IllegalArgumentException e) {
	            log.error("잘못된 토큰입니다.");
	        } catch (CRefreshTokenException e) {
	        	log.error("CRefreshTokenException empty: {}", e.getMessage());
	        }
	        return false;
	  }
	  
	
}

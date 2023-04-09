package com.example.HomeJwt.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;
import io.jsonwebtoken.Jwt;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {
    //jwt가 유요한 토큰인지 인증하기 위한 필터
	private final JwtProvider jwtProvider;
	
	//request로 들어오는 JWT의 유효성검증 -JwtProvider.validationToken()을 필터로서 filterChain에 추가
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
	//request에서 token을 취한다
		String token = jwtProvider.resolveToken((HttpServletRequest)request);
		//검증
		log.info("[Verifying token]");
		log.info(((HttpServletRequest) request).getRequestURI().toString());
		
		if(token != null && jwtProvider.validationType(token)) { //토근검증
			Authentication authentication = jwtProvider.getAuthentication(token); //인증객체생성
			SecurityContextHolder.getContext().setAuthentication(authentication); //SecurityContextHolder에 인증객체저장
		}
		chain.doFilter(request, response);
	}
     
	
}

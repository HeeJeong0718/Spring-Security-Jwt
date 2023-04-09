package com.example.HomeJwt.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.HomeJwt.jwt.JwtAuthenticationFilter;
import com.example.HomeJwt.jwt.JwtProvider;


import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	
	private final JwtProvider jwtProvider;
	  private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
	    private final CustomAccessDeniedHandler customAccessDeniedHandler;
	
	//passwordencoder bean 등록
	 @Bean
	    PasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	    }
	 // authenticationManager를 Bean 등록합니다.
	    @Bean
	    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
	        return authenticationConfiguration.getAuthenticationManager();
	    }

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
			    http.cors().and()
		        .httpBasic().disable()
		        .csrf().disable()
		
		        .sessionManagement()
		        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		
		        .and()
		        .authorizeRequests()
		        .antMatchers(HttpMethod.POST, "/v1/signup", "/v1/login","/v1/signup3",  		
		                "/v1/reissue", "/v1/social/**").permitAll()
		        .antMatchers(HttpMethod.GET, "/oauth/kakao/**").permitAll()
		        .antMatchers(HttpMethod.GET, "/exception/**").permitAll()
		        .anyRequest().hasRole("ADMIN") //admin만 가능하게 설정함  ROLE_ADMIN만 들어갈수있는  URL따로 설정필요
		  
		        .and()
		        .exceptionHandling()
		        .authenticationEntryPoint(customAuthenticationEntryPoint)
		        .accessDeniedHandler(customAccessDeniedHandler)
		      
		        .and()
		        //1.JwtAuthenticationFilter를 SpringSecurity의 UsernamePasswordAuthenticationFilter 이전에 등록하는 설정
		        //2.Filter를 등록하고 jwt Token을 생성하고 인증 및 권한 부여등의 기능을 제공할 provider를 만듬
		        .addFilterBefore(new JwtAuthenticationFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class);
		        return http.build();
		}


}

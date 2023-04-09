package com.example.HomeJwt.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.HomeJwt.domain.RefreshToken;
import com.example.HomeJwt.domain.Users;
import com.example.HomeJwt.dto.TokenDto;
import com.example.HomeJwt.dto.TokenRequestDto;
import com.example.HomeJwt.dto.UserLoginRequestDto;
import com.example.HomeJwt.dto.UserSignUpRequestDto;
import com.example.HomeJwt.exception.CEmailLoginFailedException;
import com.example.HomeJwt.exception.CEmailSignupFailedException;
import com.example.HomeJwt.exception.CRefreshTokenException;
import com.example.HomeJwt.exception.CUserNotFoundException;
import com.example.HomeJwt.exception.ErrorCode;
import com.example.HomeJwt.exception.InvalidException;
import com.example.HomeJwt.jwt.JwtProvider;
import com.example.HomeJwt.repository.RefreshTokenRepository;
import com.example.HomeJwt.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SignService {
	 private final PasswordEncoder passwordEncoder;
	private final UserRepository userRepository;
	private final PasswordEncoder PasswordEncoder;
	private final JwtProvider jwtProvider;
	private final RefreshTokenRepository refreshTokenRepository;
	
	
	@Transactional
	public Long signup(UserSignUpRequestDto userSignUpRequestDto) {
		//이메일이 없을경우 예외처리
		if(userRepository.findByEmail(userSignUpRequestDto.getEmail()).isPresent())
			throw new CEmailSignupFailedException();
		//입력자료형이 Long이니까 Long타입인 getUserId를 가져온다
		//return userRepository.save(userSignUpRequestDto.ToEntity()).getUserId();
		return null;
	  }
	
	
	@Transactional
	public UserSignUpRequestDto signup3(UserSignUpRequestDto userSignUpRequestDto) {
		//엔티티 모든것을 가져오고싶을때 Long 이 아니라 UserSignUpRequestDto로 받아온다
		 userRepository.save(userSignUpRequestDto.ToEntity(PasswordEncoder));
          return userSignUpRequestDto;
	  }
	/*@Transactional
	public UserSignUpRequestDto signup2(UserSignUpRequestDto userSignUpRequestDto) {
		return userRepository.save(userSignUpRequestDto);
	}*/
	
	//로그인 
	@Transactional
	public TokenDto login(UserLoginRequestDto userLoginRequestDto) {
		//1.회원정보 존재 확인 - email로 조회한다
		 Users user = userRepository.findByEmail(userLoginRequestDto.getEmail())
				.orElseThrow(CEmailLoginFailedException::new);
       
		 //회원패스워드 불일치 확인   //.matches(비교할대상, 비교할대상)
		 if(!passwordEncoder.matches(userLoginRequestDto.getPassword(), user.getPassword()))
		 throw new CEmailLoginFailedException();
		 //accessToken 발급 ->refreshToken 추가
		 TokenDto tokenDto = jwtProvider.createToken(user.getUserId(), user.getRoles());
		 
		 //refreshToken 저장
		 RefreshToken refreshToken = RefreshToken.builder()
				 .userkey(user.getUserId())
				 .token(tokenDto.getRefreshToken())
				 .build();
		 
		 refreshTokenRepository.save(refreshToken);
		 return tokenDto;
	}
   
	
	//재발급
	@Transactional
	public TokenDto reissue(TokenRequestDto tokenRequestDto) {
		//만료된 refreshToken 에러 jwtProvider에서 유효성검사하기때문에 jwt was expired 에러가 나옴
		//if(!jwtProvider.validationType(tokenRequestDto.getRefreshToken())) {
		//throw new CRefreshTokenException("refreshToken was expired");
	//	}
		   // 리프레시 토큰 만료 기한 체크
		 String refreshToken2 = tokenRequestDto.getRefreshToken();
        if (!jwtProvider.isExpiredToken(refreshToken2)) {
        	System.out.println("TOKEN만료됨!!");
        	throw new CRefreshTokenException("refreshToken was expired please sign again");
        }
		
		//String token = tokenRequestDto.getRefreshToken();
		//System.out.println("token:" + token);
		 // 리프레시 토큰 만료 기한 체크
       // if (jwtProvider.isExpiredToken(tokenRequestDto.getRefreshToken())) {
       // 	System.out.println("requestToken" + tokenRequestDto.getRefreshToken());
       //refreshToken 삭제해줘야함 
    		//Optional<RefreshToken> ref = refreshTokenRepository.findById(tokenRequestDto.getId());
    	   
    		//refreshTokenRepository.deleteById(ref.get().getId());
      // 	throw new CRefreshTokenException("refreshToken was expired 다시 로그인하쇼");
      //  }
		
		//AccessToken 에서 Username (pk) 가져오기
		String accessToken = tokenRequestDto.getAccessToken();
		Authentication authentication = jwtProvider.getAuthentication(accessToken);
		
		//userpk로 유저검색
		Users user = userRepository.findById(Long.parseLong(authentication.getName()))
				.orElseThrow(CUserNotFoundException::new);
		  RefreshToken refreshToken = refreshTokenRepository.findByuserkey(user.getUserId())
	                .orElseThrow(CRefreshTokenException::new);
		
		  //리프레시 토큰 불일치 에러
		  if(!refreshToken.getToken().equals(tokenRequestDto.getRefreshToken()))
		   throw new CRefreshTokenException("Refresh token was expired. Please make a new signin request");
		  //accessToken, RefreshToken 토큰재발급, 리프레시토큰다시저장
		  TokenDto newCreatedToken = jwtProvider.createToken(user.getUserId(), user.getRoles());
		  RefreshToken upRefreshToken = refreshToken.updateToken(newCreatedToken.getRefreshToken());
		  
		  refreshTokenRepository.save(upRefreshToken);
		  
		  return newCreatedToken;
	}
	
}

package com.example.HomeJwt.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.HomeJwt.dto.TokenDto;
import com.example.HomeJwt.dto.TokenRequestDto;
import com.example.HomeJwt.dto.UserLoginRequestDto;
import com.example.HomeJwt.dto.UserSignUpRequestDto;
import com.example.HomeJwt.jwt.JwtProvider;
import com.example.HomeJwt.repository.UserRepository;
import com.example.HomeJwt.response.ResponseService;
import com.example.HomeJwt.response.SingleResult;
import com.example.HomeJwt.service.SignService;

import lombok.RequiredArgsConstructor;
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class SignController {
    
	private final UserRepository userRepository;
	private final SignService signService;
	private final ResponseService responseService;
	private final JwtProvider JwtProvider;
	
	
	
	//회원가입
	/*@Param UserSignUpRequestDto
	 * 회원가입할때 필요한 dto들
	 */
	 @CrossOrigin(origins="*")
	@PostMapping("/signup") //입력자료형이 Long 이니까 service에서 return getUserId를 가쟈온다
	public SingleResult<Long> singup(@RequestBody UserSignUpRequestDto userSignUpRequestDto){
		  Long signupId = signService.signup(userSignUpRequestDto);
		  return responseService.getSingleResult(signupId); 
		  //api결과 방식 return data : 2
	 }
	
	/*{   결과dto를 다 보여주고싶을때
    "success": true,
    "code": 0,
    "msg": "성공하였습니다",
    "data": {
        "email": "eee11",
        "password": "eee11",
        "name": "eee11",
        "nickname": "eee11",
        "provider": null
    }
   }
	 * 
	 */ 
	 @CrossOrigin(origins="*")
	@PostMapping("/signup3") //입력자료형이  UserSignUprequestDto 이니까 service에서 return UserSignUprequestDto 를 가쟈온다
	public SingleResult<UserSignUpRequestDto> singup3(@RequestBody UserSignUpRequestDto userSignUpRequestDto){
		  //Long signupId = signService.signup(userSignUpRequestDto);
		 UserSignUpRequestDto result = signService.signup3(userSignUpRequestDto);
		 return responseService.getSingleResult(result);
		  //api결과 방식 return data : 2
	 }
	//회원가입
	  /* @PostMapping("/signup2")
		public Long singup2(@RequestBody UserSignUpRequestDto userSignUpRequestDto){
			  Long signupId = signService.signup(userSignUpRequestDto);
			  return signupId; //그냥 userId만 리턴
		 }*/
	 //로그인  
	   
	//----------------------------로그인--------------------------//
	 @CrossOrigin(origins="*")
	@PostMapping("/login")
	public SingleResult<TokenDto> login(@RequestBody UserLoginRequestDto userLoginRequestDto ){
		  TokenDto tokenDto = signService.login(userLoginRequestDto);
		  return responseService.getSingleResult(tokenDto);
	}
	
	//refreshToken을 보내서 새로운 accessToken을 발급하는 reissue
	 @CrossOrigin(origins="*")
	@PostMapping("/reissue")
	public SingleResult<TokenDto> reissue(@RequestBody TokenRequestDto tokenRequestDto){
		return responseService.getSingleResult(signService.reissue(tokenRequestDto));
	}
	
	
}

package com.example.HomeJwt.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.HomeJwt.dto.UserRequestDto;
import com.example.HomeJwt.dto.UserResponseDto;
import com.example.HomeJwt.response.CommonResult;
import com.example.HomeJwt.response.ListResult;
import com.example.HomeJwt.response.ResponseService;
import com.example.HomeJwt.response.SingleResult;
import com.example.HomeJwt.service.UserService;

import lombok.RequiredArgsConstructor;
@CrossOrigin(origins = "*", maxAge = 3600)
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1")
public class UserController {
    
	private final UserService userService;
	private final ResponseService responseService;
	
	//회원조회하기
	 @CrossOrigin(origins="*")
	 //@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/users")
	public ListResult<UserResponseDto> findAllUsers(){
		return responseService.getListResult(userService.findAllUsers());
	}
	//한개찾기
	 @CrossOrigin(origins="*")
	@GetMapping("/user/id/{userId}")
	public SingleResult<UserResponseDto> findUser(@PathVariable Long userId){
		return responseService.getSingleResult(userService.findById(userId));
	}
	//유저 저장하기 회원가입할때 이미 유저 저장함
	//@PostMapping("/user/save")
	
	//수정
	 @CrossOrigin(origins="*", allowedHeaders = "*")
	 @PutMapping("/user/id/{userId}")
	 public SingleResult<Long> update(@PathVariable Long userId,
			 @RequestBody UserRequestDto userRequestDto){
		return responseService.getSingleResult(userService.update(userId,userRequestDto));
	 }
	 //삭제
	 @PreAuthorize("hasRole('ADMIN')")
	 @CrossOrigin(origins="*")
	 @DeleteMapping("/user/id/{userId}")
	 public CommonResult delete(@PathVariable Long userId) {
		 userService.delete(userId);
		 return responseService.getSuccessResult();
	 }
}

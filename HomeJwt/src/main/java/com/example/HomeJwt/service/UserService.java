package com.example.HomeJwt.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.example.HomeJwt.domain.Users;
import com.example.HomeJwt.dto.UserRequestDto;
import com.example.HomeJwt.dto.UserResponseDto;
import com.example.HomeJwt.exception.CUserNotFoundException;
import com.example.HomeJwt.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
  
	private final UserRepository userRepository;
	
	//리스트
	@Transactional
	public List<UserResponseDto> findAllUsers(){
		return userRepository.findAll()
		       .stream()
		       .map(UserResponseDto::new)
		       .collect(Collectors.toList());
	}
	//한개찾기
	@Transactional
	public UserResponseDto findById(Long id) {
		Users user = userRepository.findById(id)
				.orElseThrow(CUserNotFoundException::new);
		return new UserResponseDto(user);
	}
	//이메일로찾기 
	@Transactional
	public UserResponseDto findByEmail(String email) {
		Users user = userRepository.findByEmail(email)
				.orElseThrow(CUserNotFoundException::new);
		return new UserResponseDto(user);
	}
	//유저 저장하기
	@Transactional
	public Long save(UserRequestDto userRequestDto) {
		Users savedUser = userRepository.save(userRequestDto.toEntity());
		return savedUser.getUserId();
	}
	//유저 수정하기
	@Transactional
	public Long update(Long id, UserRequestDto userRequestDto) {
		Users modifyUser = userRepository.findById(id).orElseThrow(CUserNotFoundException::new);
		modifyUser.updateNickname(userRequestDto.getNickname());
		return id;
	}
	@Transactional
	public void delete(Long id) {
		userRepository.deleteById(id);
	}
	
}

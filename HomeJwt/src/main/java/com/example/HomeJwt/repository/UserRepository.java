package com.example.HomeJwt.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.HomeJwt.domain.Users;
import com.example.HomeJwt.dto.UserResponseDto;
import com.example.HomeJwt.dto.UserSignUpRequestDto;

@Repository
public interface UserRepository  extends JpaRepository<Users, Long>{
	
	List<Users> findByName(String name);
	
	Optional<Users> findByEmail(String email);
	
	Optional<Users> findByEmailAndProvider(String email, String provider);

	Long save(UserSignUpRequestDto userSignUpRequestDto);
}

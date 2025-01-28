package com.global.ProjectManagement.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.global.UserManagement.Dto.JWTResponseDto;
import com.global.UserManagement.Dto.LoginRequestDto;
import com.global.UserManagement.Dto.UserDto;
import com.global.UserManagement.Entity.AppUser;
import com.global.UserManagement.Mapper.UserMapper;
import com.global.UserManagement.Security.Services.AuthService;
import com.global.UserManagement.Services.UserServices;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;
	private final UserServices userServices;
	private final UserMapper map;


	@PostMapping("/login")
	public ResponseEntity<JWTResponseDto> signIn(@RequestBody LoginRequestDto loginRequest) {
		JWTResponseDto jwtResponseDto = authService.login(loginRequest.getUsername(), loginRequest.getPassword());

		return ResponseEntity.ok(jwtResponseDto);
	}

	
	@PostMapping("/signup")
	public ResponseEntity<UserDto> signUp(@RequestBody AppUser entity) {
		AppUser userapp = userServices.insert(entity);
		UserDto userdto = map.map(userapp);
		return ResponseEntity.ok(userdto);
	}
}

package com.global.UserManagement.Dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginRequestDto {
//	@NotEmpty
	private String username;

//	@NotEmpty
	private String password;
}

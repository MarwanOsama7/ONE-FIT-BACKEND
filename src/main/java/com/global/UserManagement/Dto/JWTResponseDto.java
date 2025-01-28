package com.global.UserManagement.Dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class JWTResponseDto {

	private String email;
	private String accessToken;
	private String refreshToken;
}

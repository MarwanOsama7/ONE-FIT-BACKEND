package com.global.UserManagement.Dto;

import com.global.ProjectManagement.Base.Dto.BaseDto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserDto extends BaseDto<Long> {
	private String Email;
	private String UserName;
	private String Password;
	private String Address;
}

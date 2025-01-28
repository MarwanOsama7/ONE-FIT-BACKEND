package com.global.UserManagement.Dto;

import com.global.ProjectManagement.Base.Dto.BaseDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleDto extends BaseDto<Long> {

	private String name;
}

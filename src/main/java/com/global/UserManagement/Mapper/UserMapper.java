package com.global.UserManagement.Mapper;

import org.mapstruct.Mapper;

import com.global.ProjectManagement.Base.Mapper.BaseMap;
import com.global.UserManagement.Dto.UserDto;
import com.global.UserManagement.Entity.AppUser;


@Mapper(componentModel = "spring")
public interface UserMapper extends BaseMap<AppUser, UserDto> {

}

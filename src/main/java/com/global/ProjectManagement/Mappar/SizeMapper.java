package com.global.ProjectManagement.Mappar;

import org.mapstruct.Mapper;

import com.global.ProjectManagement.Base.Mapper.BaseMap;
import com.global.ProjectManagement.Dto.SizeReturnDto;
import com.global.ProjectManagement.Entity.Size;

@Mapper(componentModel = "spring")
public interface SizeMapper extends BaseMap<Size, SizeReturnDto>{
}

package com.global.ProjectManagement.Mappar;

import org.mapstruct.Mapper;

import com.global.ProjectManagement.Base.Mapper.BaseMap;
import com.global.ProjectManagement.Dto.ColorDto;
import com.global.ProjectManagement.Entity.Color;

@Mapper(componentModel = "spring")
public interface ColorMapper extends BaseMap<Color, ColorDto>{

}

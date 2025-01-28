package com.global.ProjectManagement.Mappar;

import org.mapstruct.Mapper;

import com.global.ProjectManagement.Base.Mapper.BaseMap;
import com.global.ProjectManagement.Dto.CategoryTypeDto;
import com.global.ProjectManagement.Entity.CategoryType;

@Mapper(componentModel = "spring")
public interface CategoryTypeMapper extends BaseMap<CategoryType, CategoryTypeDto>{

}

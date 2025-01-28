package com.global.ProjectManagement.Mappar;

import org.mapstruct.Mapper;

import com.global.ProjectManagement.Base.Mapper.BaseMap;
import com.global.ProjectManagement.Dto.CategoryDto;
import com.global.ProjectManagement.Entity.Category;

@Mapper(componentModel = "spring")
public interface CategoryMapper extends BaseMap<Category, CategoryDto>{
}

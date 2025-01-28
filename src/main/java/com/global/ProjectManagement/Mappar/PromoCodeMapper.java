package com.global.ProjectManagement.Mappar;

import org.mapstruct.Mapper;

import com.global.ProjectManagement.Base.Mapper.BaseMap;
import com.global.ProjectManagement.DTOs.PromoCodeDTO;
import com.global.ProjectManagement.Entity.PromoCode;

@Mapper(componentModel = "spring")
public interface PromoCodeMapper extends BaseMap<PromoCode, PromoCodeDTO>{
}

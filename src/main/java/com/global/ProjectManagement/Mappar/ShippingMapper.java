package com.global.ProjectManagement.Mappar;

import org.mapstruct.Mapper;

import com.global.ProjectManagement.Base.Mapper.BaseMap;
import com.global.ProjectManagement.Dto.ShippingDto;
import com.global.ProjectManagement.Entity.Shipping;

@Mapper(componentModel = "spring")
public interface ShippingMapper extends BaseMap<Shipping, ShippingDto>{
}

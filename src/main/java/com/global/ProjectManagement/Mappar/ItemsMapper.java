package com.global.ProjectManagement.Mappar;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.global.ProjectManagement.Base.Mapper.BaseMap;
import com.global.ProjectManagement.Dto.GetItemDto;
import com.global.ProjectManagement.Entity.OrderItems;

@Mapper(componentModel = "spring")
public interface ItemsMapper extends BaseMap<OrderItems, GetItemDto>{
	
	ItemsMapper INSTANCE = Mappers.getMapper(ItemsMapper.class);

}

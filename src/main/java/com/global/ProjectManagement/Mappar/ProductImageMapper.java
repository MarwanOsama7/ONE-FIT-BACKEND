package com.global.ProjectManagement.Mappar;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.global.ProjectManagement.Base.Mapper.BaseMap;
import com.global.ProjectManagement.Dto.ImageDataDto;
import com.global.ProjectManagement.Entity.ProductImage;

@Mapper(componentModel = "spring")
public interface ProductImageMapper extends BaseMap<ProductImage, ImageDataDto> {

	ProductImageMapper INSTANCE = Mappers.getMapper(ProductImageMapper.class);
	
    @Mapping(source = "color.id", target = "colorId")
    ImageDataDto map(ProductImage imageData);

    @Mapping(source = "colorId", target = "color.id")
    ProductImage unmap(ImageDataDto imageDataDto);
}

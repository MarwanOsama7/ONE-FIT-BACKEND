package com.global.ProjectManagement.Mappar;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.global.ProjectManagement.Base.Mapper.BaseMap;
import com.global.ProjectManagement.Dto.ProductSizeDto;
import com.global.ProjectManagement.Entity.ProductSize;

@Mapper(componentModel = "spring")
public interface ProductSizeMapper extends BaseMap<ProductSize, ProductSizeDto> {

	ProductSizeMapper INSTANCE = Mappers.getMapper(ProductSizeMapper.class);

//	@Mapping(source = "color.id", target = "colorId")
//	@Mapping(source = "color.attibutevalue", target = "colorValue") // Add this mapping
//	ProductSizeDto map(ProductSize productSize);
//
////	@Mapping(source = "colorId", target = "color.id")
////	@Mapping(source = "colorValue", target = "color.attibutevalue") // Add this mapping
//	ProductSize unmap(ProductSizeDto productSizeDto);
//
//	// Custom mapping for `sizes` when converting to DTO
////	@AfterMapping
////	default void mapSizesmap(ProductSize productSize, @MappingTarget ProductSizeDto productSizeDto) {
////		if (productSize.getSize() != null) {
////			SizeDto sizeDto = new SizeDto();
////			sizeDto.setSizeId(productSize.getSize().getId());
////			sizeDto.setStockQuantity(productSize.getStockQuantity());
////			productSizeDto.setSizes(Collections.singletonList(sizeDto));
////		}
////	}
////
////	// Custom mapping for `sizes` when converting to Entity
////	@AfterMapping
////	default void mapSizesunmap(ProductSizeDto productSizeDto, @MappingTarget ProductSize productSize) {
////		if (productSizeDto.getSizes() != null && !productSizeDto.getSizes().isEmpty()) {
////			SizeDto sizeDto = productSizeDto.getSizes().get(0);
////			Size size = new Size();
////			size.setId(sizeDto.getSizeId());
////			productSize.setSize(size);
////			productSize.setStockQuantity(sizeDto.getStockQuantity());
////		}
////	}
}

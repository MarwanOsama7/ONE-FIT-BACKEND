package com.global.ProjectManagement.Mappar;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import com.global.ProjectManagement.Base.Mapper.BaseMap;
import com.global.ProjectManagement.Dto.ProductDto;
import com.global.ProjectManagement.Dto.ProductSizeDto;
import com.global.ProjectManagement.Dto.SizeDto;
import com.global.ProjectManagement.Entity.Product;
import com.global.ProjectManagement.Entity.ProductSize;

@Mapper(componentModel = "spring", uses = { ProductSizeMapper.class, ProductImageMapper.class })
public interface ProductMapper extends BaseMap<Product, ProductDto> {

	ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

//	@Mapping(source = "category.id", target = "categoryId")
//	@Mapping(source = "categoryType.id", target = "categoryTypeId")
//	@Mapping(source = "category.", target = "categoryId")
	@Mapping(source = "categoryType.name", target = "categoryTypeName")
	@Mapping(source = "productSizes", target = "productSizes", qualifiedByName = "mergeProductSizes")
	@Mapping(source = "images", target = "images")
	ProductDto map(Product product);

//	@Mapping(source = "categoryId", target = "category.id")
//	@Mapping(source = "categoryTypeId", target = "categoryType.id")
	@Mapping(source = "categoryTypeName", target = "categoryType.name")
	@Mapping(source = "productSizes", target = "productSizes")
	@Mapping(source = "images", target = "images")
	Product unmap(ProductDto productDto);

	@Named("mergeProductSizes")
	default List<ProductSizeDto> mergeProductSizes(Set<ProductSize> productSizes) {
		Map<Long, List<SizeDto>> mergedMap = productSizes.stream()
				.collect(Collectors.groupingBy(ps -> ps.getColor().getId(), Collectors.mapping(
						ps -> new SizeDto(ps.getSize().getId(), ps.getSize().getAttibutevalue(), ps.getStockQuantity(),ps.getVariantId()),
						Collectors.toList())));

		return mergedMap.entrySet().stream().map(entry -> {
			ProductSizeDto dto = new ProductSizeDto();
			dto.setColorId(entry.getKey());
			dto.setId(entry.getKey());
			// Set the colorValue by finding it from the first element with the same colorId
			dto.setColorValue(productSizes.stream().filter(ps -> ps.getColor().getId().equals(entry.getKey()))
					.findFirst().map(ps -> ps.getColor().getAttibutevalue()) // Get color's attribute value
					.orElse(null));
			dto.setColorName(productSizes.stream().filter(ps -> ps.getColor().getId().equals(entry.getKey()))
					.findFirst().map(ps -> ps.getColor().getAttibutename()) // Get color's attribute name
					.orElse(null));
			dto.setSizes(entry.getValue());
			return dto;
		}).collect(Collectors.toList());
	}

}

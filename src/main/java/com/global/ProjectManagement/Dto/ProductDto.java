package com.global.ProjectManagement.Dto;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String name;
	private String description;
	private double price;
	private double discount;
	private String gender;
	private double priceAfterdiscount;
//	private Long categoryId;
//	private Long categoryTypeId;
	private String categoryTypeName;
	private String metaTitle;
	private String metaDescription;
	private String slug;
	private List<ProductSizeDto> productSizes;
	private List<ImageDataDto> images;
	private Set<ImageDataDto> image;

	public ProductDto(Long id, String name, double price, double discount, double priceAfterdiscount,
			String categoryTypeName, String metaTitle, String metaDescription, String slug, Set<ImageDataDto> image) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.discount = discount;
		this.priceAfterdiscount = priceAfterdiscount;
		this.categoryTypeName = categoryTypeName;
		this.metaTitle = metaTitle;
		this.metaDescription = metaDescription;
		this.slug = slug;
		this.image = image;
	}

	public ProductDto(Long id, String name, String gender, double price, double discount, double priceAfterdiscount,
			String metaTitle, String metaDescription, String slug) {
		this.id = id;
		this.name = name;
		this.gender = gender;
		this.price = price;
		this.discount = discount;
		this.priceAfterdiscount = priceAfterdiscount;
		this.metaTitle = metaTitle;
		this.metaDescription = metaDescription;
		this.slug = slug;
	}

	public ProductDto(String name, String gender, double price, double discount, double priceAfterdiscount,
			String metaTitle, String metaDescription, String slug) {
		this.name = name;
		this.gender = gender;
		this.price = price;
		this.discount = discount;
		this.priceAfterdiscount = priceAfterdiscount;
		this.metaTitle = metaTitle;
		this.metaDescription = metaDescription;
		this.slug = slug;
	}

}

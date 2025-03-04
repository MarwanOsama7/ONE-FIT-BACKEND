package com.global.ProjectManagement.DTOs;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductSizeDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String sizeValue;
	private int stockQuantity;
	private String variantId; // Unique identifier for each variant
 
//	private String colorName;
//	private Long colorId;
	
	
	public ProductSizeDTO(String size, int stockQuantity,String variantId ) {
		this.sizeValue = size;
		this.stockQuantity = stockQuantity;
		this.variantId=variantId;
//		this.colorName = color;
//		this.colorId = colorId;
	}
}

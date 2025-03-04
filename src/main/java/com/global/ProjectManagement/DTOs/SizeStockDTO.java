package com.global.ProjectManagement.DTOs;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SizeStockDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long sizeId;
    private String sizeValue;
    private Integer stockQuantity;
    private Long productId;
    private Long colorId;
    private String colorValue; // New field for color value (e.g., color name)
}

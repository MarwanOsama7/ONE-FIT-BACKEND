package com.global.ProjectManagement.DTOs;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ColorGroupedSizesDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long colorId;
	private String colorName;
	private List<ProductSizeDTO> sizes;

	public ColorGroupedSizesDTO(Long colorId, String colorName, List<ProductSizeDTO> sizes) {
		this.colorId = colorId;
		this.colorName = colorName;
		this.sizes = sizes;
	}
}

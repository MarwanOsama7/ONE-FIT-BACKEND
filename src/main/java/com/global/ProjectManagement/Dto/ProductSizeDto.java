package com.global.ProjectManagement.Dto;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductSizeDto implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
    private Long colorId;
    private String colorValue;
    private String colorName;
    private List<SizeDto> sizes;
}

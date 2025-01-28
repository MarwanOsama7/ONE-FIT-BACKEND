package com.global.ProjectManagement.Dto;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class CategoryDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String name;

	private List<CategoryTypeDto> categorytype;

	public CategoryDto(Long id, String name, List<CategoryTypeDto> categorytype) {
		this.id = id;
		this.name = name;
		this.categorytype = categorytype;
	}

}

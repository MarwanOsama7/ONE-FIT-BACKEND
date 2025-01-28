package com.global.ProjectManagement.DTOs;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryWithCategoryTypeDTO implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long categoryId;
    private String categoryName;
    private List<CategoryTypeDTO> categoryTypes;
}

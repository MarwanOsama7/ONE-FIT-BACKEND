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
public class CategoryTypeDTO implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long categoryTypeId;
    private String categoryTypeName;
}

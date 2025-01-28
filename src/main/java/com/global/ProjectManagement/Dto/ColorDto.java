package com.global.ProjectManagement.Dto;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ColorDto implements Serializable {
	/**
		 * 
		 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String attibutename;
	private String attibutevalue;
	private List<ImageDataDto> images;

	public ColorDto(Long id, String attibutename, String attibutevalue) {
		this.id = id;
		this.attibutename = attibutename;
		this.attibutevalue = attibutevalue;
	}
}

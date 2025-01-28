package com.global.ProjectManagement.Dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ImageDataDto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String imageUrl;
	private String colorName;
	private Long colorId;

	public ImageDataDto(Long id, String imageUrl, String colorName) {
		this.id = id;
		this.imageUrl = imageUrl;
		this.colorName = colorName;
	}
	public ImageDataDto(Long id, String imageUrl, Long colorId) {
		this.id = id;
		this.imageUrl = imageUrl;
		this.colorId = colorId;
	}
}

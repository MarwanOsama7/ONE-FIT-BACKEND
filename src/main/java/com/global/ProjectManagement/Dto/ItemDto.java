package com.global.ProjectManagement.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ItemDto {
	private String name;
	private String colorName;
	private String img;
	private String size;
	private double price;
	private int quantity;
}

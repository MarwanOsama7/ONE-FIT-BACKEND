package com.global.ProjectManagement.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
	private String code;
	private String cityOfOrder;
	private String note;
	private int totalPrice;
	private int totalQuantity;
	private String promoCode;

}

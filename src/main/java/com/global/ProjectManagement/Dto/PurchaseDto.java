package com.global.ProjectManagement.Dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseDto {
	private CustomerDto  client;
	private OrderDto requestOrder;
	private List<ItemDto> items;
}

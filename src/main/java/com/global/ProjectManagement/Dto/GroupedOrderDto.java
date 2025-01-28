package com.global.ProjectManagement.Dto;

import java.util.List;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GroupedOrderDto {
	private Long id;
	private String requestOrderCode;
	private int requestOrderTotalPrice;
	private int requestOrderTotalQuantity;
	private int status;
    private String createdDate;  
    private String createdTime;  
	private String clientUsername;
	private String clientPhone;
	private String clientAddress;
	private String clientcity;
	private List<GetItemDto> items;
}

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
public class ShippingDto implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String name;
    private String numberOfDay;
    private double price;
    private boolean freeShipping;
}

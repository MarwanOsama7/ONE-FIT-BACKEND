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
public class PromoCodeDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id ;
	private String promoCode;
	private double promoDiscount;
	private boolean isActive;
	private int usageLimit; // Limit number of uses
    private int usedCount;
}

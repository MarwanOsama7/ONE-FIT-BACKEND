package com.global.ProjectManagement.DTOs;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PromoCodeDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String promoCode;
	private double promoDiscount;
	private boolean isActive;
	private int usageLimit; // Limit number of uses
    private int usedCount;
}

package com.global.ProjectManagement.Entity;

import java.io.Serializable;

import com.global.ProjectManagement.Base.Entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "promo-code")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PromoCode extends BaseEntity<Long> implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(nullable = false, unique = true)
	private String promoCode;
	
    @Column(nullable = false)
	private double promoDiscount;
    
    private boolean isActive;
    private int usageLimit; //Limit number of uses
    
    @Column(nullable = false)
    private int usedCount = 0;
}

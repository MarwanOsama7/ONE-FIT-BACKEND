package com.global.ProjectManagement.Entity;

import java.io.Serializable;
import java.security.SecureRandom;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.global.ProjectManagement.Base.Entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "product-size")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductSize extends BaseEntity<Long> implements Serializable{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer stockQuantity;
	
	@Column(unique = true, nullable = false, updatable = false)
	private String variantId; // Unique identifier for each variant

    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonBackReference(value = "productSize-product")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "size_id")
    @JsonBackReference(value = "size-productSize")
    private Size size;

    @ManyToOne
    @JoinColumn(name = "color_id")
    @JsonBackReference(value = "color-productSize")
    private Color color;
    
    
	@PrePersist
	public void generateVariantId() {
	    if (this.variantId == null) {
	        this.variantId = String.valueOf(generateNumericId());
	    }
	}

	private long generateNumericId() {
	    SecureRandom random = new SecureRandom();
	    return 100_000_000_000L + random.nextLong(900_000_000_000L); // 12-digit random number
	}
}

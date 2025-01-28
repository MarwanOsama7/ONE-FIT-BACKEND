package com.global.ProjectManagement.Entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.global.ProjectManagement.Base.Entity.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

}

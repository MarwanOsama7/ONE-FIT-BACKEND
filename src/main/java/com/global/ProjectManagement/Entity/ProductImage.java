package com.global.ProjectManagement.Entity;


import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.global.ProjectManagement.Base.Entity.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "image_data")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductImage extends BaseEntity<Long> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String imageUrl;

	@ManyToOne
	@JoinColumn(name = "color_id")
	@JsonBackReference(value = "images-json1")
	private Color color;

	@ManyToOne
	@JoinColumn(name = "product_id")
	@JsonBackReference(value = "product-ImageData")
	private Product product;

	@Transient
	public Long getColorId() {
		return (color != null) ? color.getId() : null;
	}
}

package com.global.ProjectManagement.Entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.BatchSize;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.global.ProjectManagement.Base.Entity.BaseEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PostLoad;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "product")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product extends BaseEntity<Long> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private double price;
	private double discount;
	private String gender;

	@Column(columnDefinition = "LONGTEXT")
	private String description;

	@Transient
	private double priceAfterdiscount;
	
	@Column(unique = true)
	private String slug; // ✅ Slug field for friendly URLs

	@Column(name = "meta_title")
	private String metaTitle;

	@Column(name = "meta_description")
	private String metaDescription;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "category_id")
	@JsonBackReference(value = "category-product")
	private Category category;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "categorytype_id")
	@JsonBackReference(value = "categorytype-product")
	private CategoryType categoryType;

	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@BatchSize(size = 10)
	@JsonManagedReference(value = "productSize-product")
	private Set<ProductSize> productSizes = new HashSet<>();

	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@BatchSize(size = 10)
	@JsonManagedReference(value = "product-ImageData")
	private Set<ProductImage> images = new HashSet<>();


	@PostLoad
	private void calcDiscount() {
		double discountAmount = (price * discount) / 100;
		double discountedPrice = price - discountAmount;
		this.setPriceAfterdiscount(discountedPrice);
	}
	
	// ✅ Auto-generate slug from product name
	public void generateSlug() {
		this.slug = toSlug(this.name);
	}

	// ✅ Converts a string to a slug format
	private String toSlug(String input) {
		String slug = StringUtils.stripAccents(input) // Remove accents
				.replaceAll("[^\\w\\s-]", "") // Remove non-word characters
				.trim().replaceAll("\\s+", "-") // Replace spaces with hyphens
				.toLowerCase(Locale.ENGLISH);
		return slug;
	}
}

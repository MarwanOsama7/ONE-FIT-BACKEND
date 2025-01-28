package com.global.ProjectManagement.Entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.global.ProjectManagement.Base.Entity.BaseEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "size")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Size extends BaseEntity<Long> implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String attibutevalue;
	
    @OneToMany(mappedBy = "size", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "size-productSize")
    private List<ProductSize> productSizes = new ArrayList<>();
}

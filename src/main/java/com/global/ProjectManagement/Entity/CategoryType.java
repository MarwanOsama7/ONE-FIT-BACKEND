package com.global.ProjectManagement.Entity;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.BatchSize;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.global.ProjectManagement.Base.Entity.BaseEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@BatchSize(size = 10)
@Entity
@Table(name = "categorytype")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryType extends BaseEntity<Long> implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String name;

    @ManyToOne
    @JoinColumn(name = "category_id")
    @JsonBackReference(value = "categorytype-category")
    private Category category;

    @OneToMany(mappedBy = "categoryType", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference(value = "categorytype-product")
    private List<Product> products = new ArrayList<>();
}


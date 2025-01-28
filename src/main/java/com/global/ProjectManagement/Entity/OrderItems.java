package com.global.ProjectManagement.Entity;

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
@Table(name = "order_items")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItems extends BaseEntity<Long>{
	
	private String name;
	
	private String colorName;
	
	private String img;
	
	private String size;
	
	private double price;
	
	private int quantity;
	
    @ManyToOne
    @JoinColumn(name = "orders_id")
    @JsonBackReference(value = "items-order")
    private Orders orders;
}
package com.global.ProjectManagement.Entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.global.ProjectManagement.Base.Entity.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "orders")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Orders extends BaseEntity<Long>{
	private String code;
	private String note;
	private String cityOfOrder;
	private int status;
	private int totalPrice;
	private int totalQuantity;
	private String promoCode;
	
	@OneToMany(mappedBy = "orders")
	@JsonManagedReference(value = "items-order")
	private List<OrderItems> items;

	@ManyToOne
	@JoinColumn(name = "customers_id")
	@JsonBackReference(value = "customers-order")
	private Customers customers;
}

package com.global.ProjectManagement.Entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.global.ProjectManagement.Base.Entity.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "customers")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Customers extends BaseEntity<Long>{
	private String username;
	
	private String email;
	
	private String phoneNumber;
	
	private String phoneNumberOptional;
	
	private String address;
	
	private String city;
	

    @OneToMany(mappedBy = "customers")
    @JsonManagedReference(value = "customers-order")
    private List<Orders> orders;
}

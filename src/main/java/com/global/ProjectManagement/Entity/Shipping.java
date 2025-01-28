package com.global.ProjectManagement.Entity;

import java.io.Serializable;

import com.global.ProjectManagement.Base.Entity.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//@BatchSize(size = 10)
@Entity
@Table(name = "shipping")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Shipping extends BaseEntity<Long> implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
    private String numberOfDay;
    private double price;
}

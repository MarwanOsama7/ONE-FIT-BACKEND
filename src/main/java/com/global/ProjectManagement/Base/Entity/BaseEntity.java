package com.global.ProjectManagement.Base.Entity;

import java.time.LocalDateTime;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
@EntityListeners({AuditingEntityListener.class})
public class BaseEntity<ID extends Number> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected ID id;
	
	@CreatedBy
	private String createBy;
	
	@CreatedDate
	private LocalDateTime createdDate;
	
	@LastModifiedBy
	private String modifiedBy;
	
	@LastModifiedDate
	private LocalDateTime modifiedDate;
	
    public BaseEntity(ID id) {
        this.id = id;
    }

    public BaseEntity() {
        // No-argument constructor
    }
}
package com.global.ProjectManagement.Base.Dto;

import java.time.LocalDateTime;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public class BaseDto<ID extends Number> {

	private ID id;
	private String CreateBy;
	private LocalDateTime CreatedDate;
	private String modifiedBy;
	private LocalDateTime modifiedDate;
}

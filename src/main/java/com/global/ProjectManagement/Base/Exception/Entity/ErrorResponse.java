package com.global.ProjectManagement.Base.Exception.Entity;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse {
	
	private Boolean success;
	private String message;
	private LocalDateTime datetime;
	private List<String> details;

	public ErrorResponse(String message, List<String> details) {
		super();
		this.message = message;
		this.details = details;
		this.success = Boolean.FALSE;
		this.datetime = LocalDateTime.now();
	}

	public ErrorResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
}

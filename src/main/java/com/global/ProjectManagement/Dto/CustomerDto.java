package com.global.ProjectManagement.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {
	private String username;
	private String email;
	private String phoneNumber;
	private String phoneNumberOptional;
	private String address;
	private String city;
}

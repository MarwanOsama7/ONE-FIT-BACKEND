package com.global.UserManagement.Services;

import org.springframework.stereotype.Service;

import com.global.ProjectManagement.Base.Services.BaseServices;
import com.global.UserManagement.Entity.Role;
import com.global.UserManagement.Repository.RoleRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoleServices extends BaseServices<Role, Long> {
	private final RoleRepository repo;

	public Role findByName(String name) {
		return repo.findByName(name);
	}
}

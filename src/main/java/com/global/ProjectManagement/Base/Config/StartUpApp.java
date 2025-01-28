package com.global.ProjectManagement.Base.Config;

import java.util.HashSet;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.global.UserManagement.Entity.AppUser;
import com.global.UserManagement.Entity.Role;
import com.global.UserManagement.Services.RoleServices;
import com.global.UserManagement.Services.UserServices;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class StartUpApp implements CommandLineRunner {

	private final RoleServices roleService;
	private final UserServices userService;

	@Override
	public void run(String... args) throws Exception {

		if (roleService.findAll().isEmpty()) {

			roleService.insert(new Role("admin"));
			roleService.insert(new Role("user"));
		}

		if (userService.findAll().isEmpty()) {
			Set<Role> adminRole = new HashSet<>();
			adminRole.add(roleService.findByName("admin"));

			Set<Role> UserRole = new HashSet<>();
			UserRole.add(roleService.findByName("user"));

			userService.insertstartup(new AppUser("marwan@gmail.com", "marwan", "123", "matria", adminRole));
			userService.insertstartup(new AppUser("mohamed@gmail.com", "mohamed", "mo@123", "matria", adminRole));
			userService.insertstartup(new AppUser("marwanosama@brisk.com", "marwan", "Marwan@762002", "matria", adminRole));
			userService.insertstartup(new AppUser("mohamedsayed@brisk.com", "mohamed", "mo@123", "matria", adminRole));
			userService.insertstartup(new AppUser("mostafasayed@brisk.com", "mohamed", "mo@123", "matria", adminRole));
			userService.insertstartup(new AppUser("mahmoudsayed@brisk.com", "mohamed", "ma@123", "matria", adminRole));
			userService.insertstartup(new AppUser("user1@brisk.com", "mohamed", "user1@123", "matria", adminRole));
			userService.insertstartup(new AppUser("user2@brisk.com", "mohamed", "user2@123", "matria", adminRole));
			userService.insertstartup(new AppUser("user3@brisk.com", "mohamed", "user3@123", "matria", adminRole));
			userService.insertstartup(new AppUser("ahmed@gmail.com", "ahmed", "123", "gamra", UserRole));
		}

	}

}
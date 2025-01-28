package com.global.UserManagement.Services;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.global.ProjectManagement.Base.Exception.EmailException;
import com.global.ProjectManagement.Base.Exception.EmailNotFoundException;
import com.global.ProjectManagement.Base.Services.BaseServices;
import com.global.UserManagement.Entity.AppUser;
import com.global.UserManagement.Entity.AppUserDetails;
import com.global.UserManagement.Entity.Role;
import com.global.UserManagement.Repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class UserServices extends BaseServices<AppUser, Long> implements UserDetailsService {

	private final UserRepository repo;
	private final PasswordEncoder passwordEncoder;
	private final RoleServices roleService;

	@Override
	public AppUser insert(AppUser entity) {
	    // Check if an email already exists
	    if (repo.findByEmail(entity.getEmail()).isPresent()) {
	        throw new EmailException("Email already exists!");
	    }

	    // Encrypt password
	    entity.setPassword(passwordEncoder.encode(entity.getPassword()));

	    // Set default role
	    Set<Role> userRole = new HashSet<>();
	    userRole.add(roleService.findByName("user"));
	    entity.setRole(userRole);
	    
	    // Save the entity
	    return repo.save(entity);
	}

	public AppUser insertstartup(AppUser entity) {
		entity.setPassword(passwordEncoder.encode(entity.getPassword()));
		return repo.save(entity);
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<AppUser> user = repo.findByEmail(email);
		if (user.isEmpty()) {
			log.error("User not Found : " + email);
			throw new EmailNotFoundException("This user not found with selected user name: " + email);
		}
		return new AppUserDetails(user.get());
	}

}

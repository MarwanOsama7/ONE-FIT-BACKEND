package com.global.UserManagement.Repository;

import java.util.Optional;

import com.global.ProjectManagement.Base.Repository.BaseRepository;
import com.global.UserManagement.Entity.AppUser;


public interface UserRepository extends BaseRepository<AppUser, Long> {

	Optional<AppUser> findByUsername(String name);
	
//	AppUser findByEmail(String email);
	
	Optional<AppUser> findByEmail(String email);

}

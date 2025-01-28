package com.global.UserManagement.Repository;

import com.global.ProjectManagement.Base.Repository.BaseRepository;
import com.global.UserManagement.Entity.Role;

public interface RoleRepository extends BaseRepository<Role, Long> {
	Role findByName(String name);

}

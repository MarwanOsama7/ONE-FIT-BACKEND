package com.global.UserManagement.Entity;

import java.util.HashSet;
import java.util.Set;

import com.global.ProjectManagement.Base.Entity.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "sec_users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AppUser extends BaseEntity<Long> {
	private String email;
	private String username;
	private String password;
	private String address;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "sec-users-roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> role = new HashSet<>();

	public AppUser(Long id) {
		super();
		super.id = id;
	}
}

package com.global.UserManagement.Entity;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AppUserDetails implements UserDetails {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String email;
	private String username;
	private String password;
	private String address;
	private List<GrantedAuthority> authorities;

	public AppUserDetails(AppUser user) {
		this.id = user.getId();
		this.email = user.getEmail();
		this.address = user.getAddress();
		this.username = user.getUsername();
		this.password = user.getPassword();
		this.authorities = user.getRole().stream().map(role -> new SimpleGrantedAuthority(role.getName()))
				.collect(Collectors.toList());
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}

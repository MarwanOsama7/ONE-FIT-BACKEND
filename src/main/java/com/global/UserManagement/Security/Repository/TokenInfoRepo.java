package com.global.UserManagement.Security.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.global.UserManagement.Entity.TokenInfo;



public interface TokenInfoRepo extends JpaRepository<TokenInfo, Long> {

	Optional<TokenInfo> findByRefreshToken(String refreshToken);

}

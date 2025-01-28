package com.global.UserManagement.Security.Services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.global.UserManagement.Entity.TokenInfo;
import com.global.UserManagement.Security.Repository.TokenInfoRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class tokenInfoService {
	private final TokenInfoRepo tokenInfoRepo;

	public TokenInfo findById(Long id) {

		return tokenInfoRepo.findById(id).orElse(null);
	}

	public Optional<TokenInfo> findByRefreshToken(String refreshToken) {

		return tokenInfoRepo.findByRefreshToken(refreshToken);
	}

	public TokenInfo save(TokenInfo entity) {

		return tokenInfoRepo.save(entity);
	}

	public void deleteById(Long id) {

		tokenInfoRepo.deleteById(id);
	}
}

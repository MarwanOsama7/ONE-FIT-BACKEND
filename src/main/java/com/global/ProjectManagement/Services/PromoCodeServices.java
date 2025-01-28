package com.global.ProjectManagement.Services;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

import com.global.ProjectManagement.Base.Services.BaseServices;
import com.global.ProjectManagement.Entity.PromoCode;
import com.global.ProjectManagement.Repository.PromoCodeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PromoCodeServices extends BaseServices<PromoCode, Long> {
	private final PromoCodeRepository promoCodeRepository;

	@Override
	@CacheEvict(value = { "usePromoCode", "validatePromoCode" }, key = "#root.methodName", allEntries = true)
	public PromoCode insert(PromoCode entity) {
		entity.setActive(true);
		return promoCodeRepository.save(entity);
	}

	// Validate a promo code
	@CachePut(value = "validatePromoCode", key = "#code")
	public Optional<PromoCode> validatePromoCode(String code) {
		Optional<PromoCode> promoCode = promoCodeRepository.findByPromoCode(code);
		if (promoCode.isPresent()) {
			PromoCode foundCode = promoCode.get();
			if (foundCode.isActive() && foundCode.getCreatedDate().isBefore(LocalDateTime.now())
					&& (foundCode.getUsageLimit() == 0 || foundCode.getUsedCount() < foundCode.getUsageLimit())) {
				return Optional.of(foundCode);
			}
		}
		return Optional.empty();
	}

	// Mark a promo code as used
	@CachePut(value = "usePromoCode", key = "#code")
	public void usePromoCode(String code) {
		Optional<PromoCode> promoCode = promoCodeRepository.findByPromoCode(code);
		promoCode.ifPresent(p -> {
			p.setUsedCount(p.getUsedCount() + 1);
			if (p.getUsedCount() >= p.getUsageLimit()) {
				p.setActive(false);
			}
			promoCodeRepository.save(p);
		});
	}

}

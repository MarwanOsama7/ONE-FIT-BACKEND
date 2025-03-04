package com.global.ProjectManagement.Services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.global.ProjectManagement.Base.Services.BaseServices;
import com.global.ProjectManagement.DTOs.PromoCodeDTO;
import com.global.ProjectManagement.Entity.PromoCode;
import com.global.ProjectManagement.Repository.PromoCodeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PromoCodeServices extends BaseServices<PromoCode, Long> {
	private final PromoCodeRepository promoCodeRepository;

	@Cacheable(value = "getPaginatedfindAllPromoCode", key = "#page + '_' + #size")
	public Page<PromoCodeDTO> getPaginatedfindAll(int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		Page<PromoCode> promocodePage = promoCodeRepository.findAll(pageable);

		// Map each Category entity to CategoryDto
		List<PromoCodeDTO> promocodeDtoList = promocodePage.getContent().stream().map(promocode -> {

			return new PromoCodeDTO(promocode.getId(), promocode.getPromoCode(), promocode.getPromoDiscount(),
					promocode.isActive(), promocode.getUsageLimit(), promocode.getUsedCount());
		}).collect(Collectors.toList());

		return new PageImpl<>(promocodeDtoList, pageable, promocodePage.getTotalElements());
	}

	@Override
	@CacheEvict(value = { "usePromoCode", "validatePromoCode",
			"getPaginatedfindAllPromoCode" }, key = "#root.methodName", allEntries = true)
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

	@Override
	@CacheEvict(value = { "usePromoCode", "validatePromoCode",
			"getPaginatedfindAllPromoCode" }, key = "#root.methodName", allEntries = true)
	public void deleteById(Long id) {
		promoCodeRepository.deleteById(id);
	}
}

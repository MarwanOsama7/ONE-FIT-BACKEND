package com.global.ProjectManagement.Services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.global.ProjectManagement.Base.Exception.RecordNotFoundException;
import com.global.ProjectManagement.Base.Services.BaseServices;
import com.global.ProjectManagement.Dto.ShippingDto;
import com.global.ProjectManagement.Entity.Shipping;
import com.global.ProjectManagement.Mappar.ShippingMapper;
import com.global.ProjectManagement.Repository.ShippingRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ShippingServices extends BaseServices<Shipping, Long> {

	private final ShippingRepository shippingRepository;
	private final ShippingMapper shippingMapper;

	@Override
	@CacheEvict(value = { "shipingFindAll", "shipingFindById",
			"shipingGetAll" }, key = "#root.methodName", allEntries = true)
	public Shipping insert(Shipping entity) {
		return shippingRepository.save(entity);
	}

	@CacheEvict(value = { "shipingFindAll", "shipingFindById",
			"shipingGetAll" }, key = "#root.methodName", allEntries = true)
	public ShippingDto updateShipping(Long id, ShippingDto dto) {
		// Retrieve the existing shipping by ID
		Shipping existingShipping = findById(id);
		if (existingShipping == null) {
			return null; // Handle not found scenario
		}

		// Map the incoming DTO to the existing shipping entity
		shippingMapper.unmap(existingShipping, dto);

		// Save the updated shipping entity
		Shipping updatedShipping = update(existingShipping);

		// Convert the updated entity back to a DTO
		return shippingMapper.map(updatedShipping);
	}

	@Override
	@CacheEvict(value = { "shipingFindAll", "shipingFindById",
			"shipingGetAll" }, key = "#root.methodName", allEntries = true)
	public void deleteById(Long id) {
		shippingRepository.deleteById(id);
	}

	@Override
	@Cacheable(value = "shipingFindAll", key = "#root.methodName")
	public List<Shipping> findAll() {
		return shippingRepository.findAll();
	}

	@Override
	@Cacheable(value = "shipingFindById", key = "#root.methodName")
	public Shipping findById(Long id) {
		Optional<Shipping> entity = shippingRepository.findById(id);
		if (entity.isPresent()) {
			return entity.orElse(null);
		} else {
			throw new RecordNotFoundException("this record with id : " + id + "  Not Found");
		}
	}

	@Cacheable(value = "shipingGetAll", key = "#root.methodName")
	public Page<ShippingDto> getPaginatedfindAll(int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		Page<Shipping> ColorPage = shippingRepository.findAll(pageable);

		// Map each Category entity to CategoryDto
		List<ShippingDto> ColorDtoList = ColorPage.getContent().stream().map(shippingMapper::map)
				.collect(Collectors.toList());

		return new PageImpl<>(ColorDtoList, pageable, ColorPage.getTotalElements());
	}
}

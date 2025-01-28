package com.global.ProjectManagement.Services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.global.ProjectManagement.Base.Services.BaseServices;
import com.global.ProjectManagement.Dto.SizeReturnDto;
import com.global.ProjectManagement.Entity.Size;
import com.global.ProjectManagement.Mappar.SizeMapper;
import com.global.ProjectManagement.Repository.SizeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SizeServices extends BaseServices<Size, Long> {

	private final SizeRepository sizeRepository;
	private final SizeMapper mapper;

	@Override
	@CacheEvict(value = { "getPaginatedSizesfindAll",
			"findSizeIdsAndNames" }, key = "#root.methodName", allEntries = true)
	public Size insert(Size color) {
		return sizeRepository.save(color);
	}

	@Cacheable(value = "getPaginatedSizesfindAll", key = "#size + '_' + #page + '_' + #size")
	public Page<SizeReturnDto> getPaginatedfindAll(int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		Page<Size> sizePage = sizeRepository.findAll(pageable);

		// Map each Category entity to CategoryDto
		List<SizeReturnDto> sizeDtoList = sizePage.getContent().stream().map(mapper::map).collect(Collectors.toList());

		return new PageImpl<>(sizeDtoList, pageable, sizePage.getTotalElements());
	}

	@Cacheable(value = "findSizeIdsAndNames", key = "#root.methodName")
	public List<Object> findSizeIdsAndNames() {
		return sizeRepository.findSizeIdsAndNames();
	}
}

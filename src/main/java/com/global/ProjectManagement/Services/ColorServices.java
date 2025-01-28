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
import com.global.ProjectManagement.Dto.ColorDto;
import com.global.ProjectManagement.Entity.Color;
import com.global.ProjectManagement.Repository.ColorRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ColorServices extends BaseServices<Color, Long> {
	private final ColorRepository colorRepository;
//	private final ColorMapper mapper;

	@Override
	@CacheEvict(value = { "getPaginatedColorsfindAll",
			"findColorIdsAndNames" }, key = "#root.methodName", allEntries = true)
	public Color insert(Color color) {
		return colorRepository.save(color);
	}

	@Cacheable(value = "getPaginatedColorsfindAll", key = "#color + '_' + #page + '_' + #size")
	public Page<ColorDto> getPaginatedfindAll(int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		Page<Color> ColorPage = colorRepository.findAll(pageable);

		// Map each Category entity to CategoryDto
		List<ColorDto> ColorDtoList = ColorPage.getContent().stream().map(color -> {
			return new ColorDto(color.getId(), color.getAttibutename(), color.getAttibutevalue());
		}).collect(Collectors.toList());

		return new PageImpl<>(ColorDtoList, pageable, ColorPage.getTotalElements());
	}

	@Cacheable(value = "findColorIdsAndNames", key = "#root.methodName")
	public List<Object> findColorIdsAndNames() {
		return colorRepository.findColorIdsAndNames();
	}
}

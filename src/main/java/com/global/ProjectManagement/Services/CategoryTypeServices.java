package com.global.ProjectManagement.Services;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.global.ProjectManagement.Base.Services.BaseServices;
import com.global.ProjectManagement.Entity.CategoryType;
import com.global.ProjectManagement.Repository.CategoryTypeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryTypeServices extends BaseServices<CategoryType, Long> {
	private final CategoryTypeRepository categoryTypeRepository;


	@Cacheable(value = "findCategoryTypeIdsAndNames", key = "#root.methodName")
	public List<Object> findCategoryTypeIdsAndNames() {
		return categoryTypeRepository.findCategoryTypeIdsAndNames();
	}
}

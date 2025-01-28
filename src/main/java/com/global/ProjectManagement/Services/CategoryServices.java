package com.global.ProjectManagement.Services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.global.ProjectManagement.Base.Exception.RecordNotFoundException;
import com.global.ProjectManagement.DTOs.CategoryTypeDTO;
import com.global.ProjectManagement.DTOs.CategoryWithCategoryTypeDTO;
import com.global.ProjectManagement.Dto.CategoryDto;
import com.global.ProjectManagement.Dto.CategoryTypeDto;
import com.global.ProjectManagement.Entity.Category;
import com.global.ProjectManagement.Entity.CategoryType;
import com.global.ProjectManagement.Repository.CategoryRepository;
import com.global.ProjectManagement.Repository.CategoryTypeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryServices {

	private final CategoryRepository categoryRepository;
	private final CategoryTypeRepository categoryTypeRepository;
//	private final CategoryMapper mapper;

	@Cacheable(value = "getCategoryTypeNamesByCategoryName", key = "#categoryName")
	public List<String> getCategoryTypeNamesByCategoryName(String categoryName) {
		return categoryTypeRepository.findCategoryTypeNamesByCategoryName(categoryName);
	}

	@Cacheable(value = "findAllCategory", key = "#root.methodName")
	public List<Category> findAll() {
		List<Category> categories = categoryRepository.findAll();
		return categories;
	}

	@Cacheable(value = "findByIdCategory", key = "#id")
	public Category findById(Long id) {
		Category category = categoryRepository.findById(id)
				.orElseThrow(() -> new RecordNotFoundException("Category not found with id: " + id));

		// Force loading the associations if needed
		category.getCategorytype().size(); // Ensure the categorytype list is initialized

		return category;
	}

	@CacheEvict(value = { "findByIdCategory", "findAllCategory", "getCategoryTypeNamesByCategoryName",
			"getPaginatedfindAllCategory","getCategoryWithCategoryTypes" }, key = "#root.methodName", allEntries = true)
	public Category insert(Category category) {
		if (category.getCategorytype() != null) {
			for (CategoryType type : category.getCategorytype()) {
				type.setCategory(category);
			}
		}
		return categoryRepository.save(category);
	}

	@CacheEvict(value = { "findByIdCategory", "findAllCategory", "getCategoryTypeNamesByCategoryName",
			"getPaginatedfindAllCategory","getCategoryWithCategoryTypes" }, key = "#root.methodName", allEntries = true)
	public Category update(Category updatedCategory, Long id) {
		// Retrieve the existing category by ID
		Category existingCategory = findById(id);

		existingCategory.setName(updatedCategory.getName());

		existingCategory.getCategorytype().clear();
		if (updatedCategory.getCategorytype() != null) {
			for (CategoryType type : updatedCategory.getCategorytype()) {
				type.setCategory(existingCategory);
			}
			existingCategory.getCategorytype().addAll(updatedCategory.getCategorytype());
		}

		return categoryRepository.save(existingCategory);
	}

	@Cacheable(value = "getPaginatedfindAllCategory", key = "#root.methodName")
	public Page<CategoryDto> getPaginatedfindAll(int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		Page<Category> categoryPage = categoryRepository.findAll(pageable);

		List<CategoryDto> categoryDtoList = categoryPage.getContent().stream().map(category -> {
			List<CategoryTypeDto> categorytypedtos = category.getCategorytype().stream()
					.map(categorytypedto -> new CategoryTypeDto(categorytypedto.getName()))
					.collect(Collectors.toList());

			return new CategoryDto(category.getId(), category.getName(), categorytypedtos);
		}).collect(Collectors.toList());

		return new PageImpl<>(categoryDtoList, pageable, categoryPage.getTotalElements());
	}

	@Cacheable(value = "getCategoryWithCategoryTypes", key = "#root.methodName")
    public List<CategoryWithCategoryTypeDTO> getCategoryWithCategoryTypes() {
        List<Object[]> results = categoryRepository.findCategoryIdsAndNamesWithCategoryType();

        // Using streaming to process and structure the data
        Map<Long, CategoryWithCategoryTypeDTO> categoryMap = new HashMap<>();

        results.stream().forEach(row -> {
            Long categoryId = (Long) row[0];
            String categoryName = (String) row[1];
            Long categoryTypeId = (Long) row[2];
            String categoryTypeName = (String) row[3];

            CategoryWithCategoryTypeDTO categoryDto = categoryMap.get(categoryId);

            if (categoryDto == null) {
                categoryDto = new CategoryWithCategoryTypeDTO(categoryId, categoryName, new ArrayList<>());
                categoryMap.put(categoryId, categoryDto);
            }

            categoryDto.getCategoryTypes().add(new CategoryTypeDTO(categoryTypeId, categoryTypeName));
        });

        // Return as a list of CategoryWithCategoryTypeDTO
        return new ArrayList<>(categoryMap.values());
    }

	@CacheEvict(value = { "findByIdCategory", "findAllCategory", "getCategoryTypeNamesByCategoryName",
			"getPaginatedfindAllCategory","getCategoryWithCategoryTypes" }, key = "#root.methodName", allEntries = true)
	public void deleteById(Long id) {
		categoryRepository.deleteById(id);
	}
}

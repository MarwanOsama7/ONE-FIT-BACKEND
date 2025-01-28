package com.global.ProjectManagement.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.global.ProjectManagement.Dto.CategoryDto;
import com.global.ProjectManagement.Dto.CategoryTypeDto;
import com.global.ProjectManagement.Entity.Category;
import com.global.ProjectManagement.Entity.CategoryType;
import com.global.ProjectManagement.Mappar.CategoryMapper;
import com.global.ProjectManagement.Mappar.CategoryTypeMapper;
import com.global.ProjectManagement.Services.CategoryServices;
import com.global.ProjectManagement.Services.CategoryTypeServices;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v2")
@RequiredArgsConstructor
public class CategoryController {

	private final CategoryServices service;
	private final CategoryMapper mapper;
	private final CategoryTypeMapper categoryTypeMapper;
	private final CategoryTypeServices categoryTypeServices;

    @GetMapping("/names/byCategoryName")
    public ResponseEntity<List<String>> getCategoryTypeNamesByCategoryName(@RequestParam String categoryName) {
        List<String> categoryTypeNames = service.getCategoryTypeNamesByCategoryName(categoryName);
        return ResponseEntity.ok(categoryTypeNames);
    }
	
	@GetMapping("/admin/categorypaginate/findall")
	public ResponseEntity<?> getEntities(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {
		return ResponseEntity.ok(service.getPaginatedfindAll(page, size));
	}

	@GetMapping("/category/findall")
	public ResponseEntity<?> findall() {
		List<Category> category = service.findAll();
		List<CategoryDto> returnDto = mapper.map(category);
		return ResponseEntity.ok(returnDto);
	}

	@GetMapping("/admin/categorytype/findall")
	public ResponseEntity<?> findAllCategoryType() {
		List<CategoryType> category = categoryTypeServices.findAll();
		List<CategoryTypeDto> returnDto = categoryTypeMapper.map(category);
		return ResponseEntity.ok(returnDto);
	}

	@GetMapping("/admin/category/findbyid/{id}")
	public ResponseEntity<?> findById(@PathVariable Long id) {
		Category category = service.findById(id);
		CategoryDto returnDto = mapper.map(category);
		return ResponseEntity.ok(returnDto);
	}

	@PostMapping("/admin/categoryinsert/insert")
	public ResponseEntity<?> insert(@RequestBody CategoryDto dto) {
		Category category = mapper.unmap(dto);
		Category savedCategory = service.insert(category);
		CategoryDto returnDto = mapper.map(savedCategory);
		return ResponseEntity.ok(returnDto);
	}

	@PutMapping("/admin/category/update/{id}")
	public ResponseEntity<CategoryDto> update(@PathVariable Long id, @RequestBody CategoryDto dto) {
	    // Map the incoming DTO to a Category entity
	    Category updatedCategory = mapper.unmap(dto);

	    // Call the service to update the category
	    Category updatedEntity = service.update(updatedCategory, id);

	    // Map the updated entity back to a DTO
	    CategoryDto returnDto = mapper.map(updatedEntity);
	    return ResponseEntity.ok(returnDto);
	}

	@GetMapping("/admin/getCategoryWithCategoryTypes")
	public ResponseEntity<?> getCategoryWithCategoryTypes() {
		return ResponseEntity.ok(service.getCategoryWithCategoryTypes());
	}

	@GetMapping("/admin/findcategorytypeidandname")
	public ResponseEntity<?> findCategoryTypeIdsAndNames() {
		return ResponseEntity.ok(categoryTypeServices.findCategoryTypeIdsAndNames());
	}

	@DeleteMapping("/admin/category/deletebyid/{id}")
	public ResponseEntity<String> deleteById(@PathVariable Long id) {
		service.deleteById(id);
		return ResponseEntity.ok("Record deleted successfully");
	}

}

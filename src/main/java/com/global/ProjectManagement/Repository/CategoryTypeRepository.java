package com.global.ProjectManagement.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.global.ProjectManagement.Base.Repository.BaseRepository;
import com.global.ProjectManagement.Entity.CategoryType;

public interface CategoryTypeRepository extends BaseRepository<CategoryType, Long> {

	@Query("SELECT ct.name FROM CategoryType ct WHERE ct.category.name = :categoryName")
	List<String> findCategoryTypeNamesByCategoryName(@Param("categoryName") String categoryName);

	@Query("SELECT ca.id,ca.name,ca.category.id FROM CategoryType ca")
	List<Object> findCategoryTypeIdsAndNames();
}

package com.global.ProjectManagement.Repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import com.global.ProjectManagement.Base.Repository.BaseRepository;
import com.global.ProjectManagement.Entity.Category;

public interface CategoryRepository extends BaseRepository<Category, Long> {

	Page<Category> findAll(Pageable pageable);

//	@EntityGraph(attributePaths = "categorytype")
//	Optional<Category> findById(Category id);

//	@EntityGraph(attributePaths = { "categorytype", "products" })
//	List<Category> findAll();

    @Query("SELECT ca.id, ca.name, ct.id, ct.name FROM Category ca JOIN ca.categorytype ct")
    List<Object[]> findCategoryIdsAndNamesWithCategoryType();

}

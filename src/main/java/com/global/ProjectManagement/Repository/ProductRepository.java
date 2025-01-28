package com.global.ProjectManagement.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.global.ProjectManagement.Base.Repository.BaseRepository;
import com.global.ProjectManagement.Entity.Product;

public interface ProductRepository extends BaseRepository<Product, Long> {

	@Query("SELECT p FROM Product p WHERE p.name = :name")
	Optional<Product> findByNameAndCategory(@Param("name") String name);

	Page<Product> findAll(Pageable pageable);

	Page<Product> findByDiscountGreaterThanAndDiscountLessThanEqual(double minDiscount, double maxDiscount,
			Pageable pageable);

	Page<Product> findAllByDiscountIsNotNull(Pageable pageable);

	
    List<Product> findByNameContainingIgnoreCase(String name);

    
	Optional<Product> findByName(String name);

    
	
	
	@Query("SELECT p FROM Product p LEFT JOIN FETCH p.images LEFT JOIN FETCH p.productSizes WHERE p.name = :name")
	Product findByNameWithImagesAndSizes(@Param("name") String name);
	
	
	
//	Set<Product> findAllByOrderByCreatedDateDesc();

	Set<Product> findAllByCategoryNameOrderByCreatedDateDesc(String categoryName);

	Page<Product> findByCategoryId(Long categoryId, Pageable pageable);

	Page<Product> findByCategoryName(String categoryName, Pageable pageable);

	@Query("SELECT p FROM Product p WHERE p.gender = :gender AND p.category.id = :categoryId")
	Page<Product> findByGenderAndCategoryId(@Param("gender") String gender, @Param("categoryId") Long categoryId,
			Pageable pageable);

	Page<Product> findByCategoryTypeId(Long categoryTypeId, Pageable pageable);

	Page<Product> findByCategoryTypeName(String categoryTypeName, Pageable pageable);

	List<Product> findByNameContaining(String name);
}

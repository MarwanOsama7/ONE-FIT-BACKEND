package com.global.ProjectManagement.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.global.ProjectManagement.Base.Repository.BaseRepository;
import com.global.ProjectManagement.Entity.Product;
import com.global.ProjectManagement.Entity.ProductImage;

import jakarta.transaction.Transactional;


public interface ProductImageRepository extends BaseRepository<ProductImage, Long> {
	
    Optional<ProductImage> findByName(String fileName);
    
    List<ProductImage> findByOrderById();

    List<ProductImage> findByProduct(Product product);
    
    List<ProductImage> findByProductId(Long productId);
    
    @Query("SELECT i FROM ProductImage i WHERE i.product.id IN :productIds")
    List<ProductImage> findByProductIds(@Param("productIds") List<Long> productIds);
    
    @Transactional
    @Modifying
    @Query("DELETE FROM ProductImage i WHERE i.product.id = :productId")
    void deleteByProductId(Long productId);
}

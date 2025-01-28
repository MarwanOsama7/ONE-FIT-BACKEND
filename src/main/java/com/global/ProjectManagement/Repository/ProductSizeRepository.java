package com.global.ProjectManagement.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.global.ProjectManagement.Base.Repository.BaseRepository;
import com.global.ProjectManagement.Entity.ProductSize;

import jakarta.persistence.LockModeType;

public interface ProductSizeRepository extends BaseRepository<ProductSize, Long> {

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	ProductSize findByProductIdAndSizeIdAndColorId(Long productId, Long sizeId, Long colorId);

    List<ProductSize> findByProductIdAndSizeIdInAndColorIdIn(Long productId, List<Long> sizeIds, List<Long> colorIds);

	@Query("SELECT ps FROM ProductSize ps " + "JOIN FETCH ps.size s " + "JOIN FETCH ps.product p "
			+ "JOIN FETCH ps.color c " + "WHERE ps.product.id = :productId")
	List<ProductSize> findByProductIdWithFetch(@Param("productId") Long productId);
}

package com.global.ProjectManagement.Repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.global.ProjectManagement.Base.Repository.BaseRepository;
import com.global.ProjectManagement.Entity.OrderItems;
import com.global.ProjectManagement.Entity.Orders;

public interface OrderItemsRepository extends BaseRepository<OrderItems, Long> {

	@Query("SELECT i FROM OrderItems i JOIN FETCH i.orders ro JOIN FETCH ro.customers ORDER BY ro.id")
	Page<OrderItems> findAllByOrderByRequestOrderId(Pageable pageable);

	@Query("SELECT ro FROM Orders ro JOIN FETCH ro.customers c WHERE ro.code LIKE %:searchTerm% OR c.phoneNumber LIKE %:searchTerm%")
	List<Orders> findByCodeOrPhoneNumber(@Param("searchTerm") String searchTerm);
}

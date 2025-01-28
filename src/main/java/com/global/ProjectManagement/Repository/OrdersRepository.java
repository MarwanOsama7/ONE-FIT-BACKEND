package com.global.ProjectManagement.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.global.ProjectManagement.Base.Repository.BaseRepository;
import com.global.ProjectManagement.Entity.Orders;

import jakarta.transaction.Transactional;

public interface OrdersRepository extends BaseRepository<Orders, Long> {

	@Modifying
	@Transactional
	@Query("UPDATE Orders ro SET ro.status = :status WHERE ro.id = :id")
	int updateStatusById(Long id, int status);

	@Query("SELECT ro FROM Orders ro WHERE ro.createBy = :createBy AND ro.status <> 1")
	List<Orders> findByCreateByExcludingStatusOne(@Param("createBy") String createBy);

	@Query("SELECT COUNT(*) FROM Orders ro WHERE ro.status = -1")
	long CountNewOrders();

	@Query("SELECT COUNT(*) FROM Orders")
	long countAllOrders();

//    @Query("SELECT ro FROM RequestOrder ro JOIN ro.client c WHERE ro.code LIKE %:searchTerm% OR c.phoneNumber LIKE %:searchTerm%")
//    List<RequestOrder> findByCodeOrPhoneNumber(@Param("searchTerm") String searchTerm);
}

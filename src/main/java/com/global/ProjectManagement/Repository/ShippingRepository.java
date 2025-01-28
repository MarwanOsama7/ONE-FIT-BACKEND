package com.global.ProjectManagement.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.global.ProjectManagement.Base.Repository.BaseRepository;
import com.global.ProjectManagement.Entity.Shipping;

public interface ShippingRepository extends BaseRepository<Shipping, Long> {
	Page<Shipping> findAll(Pageable pageable);
}

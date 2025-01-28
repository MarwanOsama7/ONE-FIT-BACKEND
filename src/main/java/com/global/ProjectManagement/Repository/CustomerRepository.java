package com.global.ProjectManagement.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Lock;

import com.global.ProjectManagement.Base.Repository.BaseRepository;
import com.global.ProjectManagement.Entity.Customers;

import jakarta.persistence.LockModeType;

public interface CustomerRepository extends BaseRepository<Customers, Long> {

	@Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Customers> findByEmail(String email);
}

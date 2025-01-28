package com.global.ProjectManagement.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.global.ProjectManagement.Entity.Log;

public interface LogRepository extends JpaRepository<Log, Long> {

}

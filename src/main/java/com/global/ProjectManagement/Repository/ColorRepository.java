package com.global.ProjectManagement.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import com.global.ProjectManagement.Base.Repository.BaseRepository;
import com.global.ProjectManagement.Entity.Color;

public interface ColorRepository extends BaseRepository<Color, Long> {
	
	Page<Color> findAll(Pageable pageable);
	
	@Query("SELECT c.id,c.attibutename FROM Color c")
	List<Object> findColorIdsAndNames();
	
    Optional<Color> findByAttibutename(String colorName);

}

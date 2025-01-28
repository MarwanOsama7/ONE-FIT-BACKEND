package com.global.ProjectManagement.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import com.global.ProjectManagement.Base.Repository.BaseRepository;
import com.global.ProjectManagement.Entity.Size;

public interface SizeRepository extends BaseRepository<Size, Long> {
	Page<Size> findAll(Pageable pageable);
	
	
	@Query("SELECT c.id,c.attibutevalue FROM Size c")
	List<Object> findSizeIdsAndNames();
	
    Optional<Size> findByAttibutevalue(String sizeValue);

}

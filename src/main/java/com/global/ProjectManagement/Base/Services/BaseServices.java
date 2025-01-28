package com.global.ProjectManagement.Base.Services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import com.global.ProjectManagement.Base.Exception.RecordNotFoundException;
import com.global.ProjectManagement.Base.Repository.BaseRepository;
import jakarta.persistence.MappedSuperclass;
import jakarta.transaction.Transactional;

@MappedSuperclass
public class BaseServices<T, ID extends Number> {

	@Autowired
	private BaseRepository<T, ID> baseRepository;

	@Transactional
	public List<T> findAll() {
		return baseRepository.findAll();
	}

	public T findById(ID id) {
		Optional<T> entity = baseRepository.findById(id);
		if (entity.isPresent()) {
			return entity.orElse(null);
		} else {
			throw new RecordNotFoundException("this record with id : " + id + "  Not Found");
		}
	}

	public Optional<T> getById(ID id) {
		return baseRepository.findById(id);
	}

	public T insert(T entity) {
		return baseRepository.save(entity);
	}

	public T persist(T entity) {
		return baseRepository.saveAndFlush(entity);
	}

	public T update(T entity) {
		return baseRepository.save(entity);
	}

	public void deleteById(ID id) {
		baseRepository.deleteById(id);
	}

	public void deleteAll(List<ID> ids) {

		ids.forEach(id -> {
			baseRepository.deleteById(id);
		});
	}

}

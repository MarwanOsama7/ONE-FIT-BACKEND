package com.global.ProjectManagement.Base.Mapper;

import java.util.List;
import java.util.Set;

import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

public interface BaseMap<T, Dto> {

	Dto map(T t);

	T unmap(Dto d);


    @Mapping(target = "id", ignore = true)
	T unmap(@MappingTarget T t, Dto d);

	List<Dto> map(List<T> t);

	Set<Dto> map(Set<T> t);

	List<T> unmap(List<Dto> d);

	T unmaplist(T d);
}

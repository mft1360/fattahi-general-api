package com.fattahi.general.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import com.fattahi.general.model.BaseEntity;

public interface IGenericService<T extends BaseEntity<PK>, PK extends Serializable> {

	T findOne(PK id);

	T save(T entity);

	void save(List<T> entities);

	void delete(T entity);

	void delete(List<T> entites);

	void delete(PK id);

	List<T> findAll();

	List<T> findAll(List<PK> ids);

	List<T> findAll(Sort sort);

	<S extends T> List<T> findAll(S example);

	<S extends T> List<T> findAll(S example, Sort sort);

	<S extends T> Page<T> findAll(int page, int size);

	Page<T> findAll(int page, int size, Sort sort);

	Page<T> findAll(PageRequest pageRequest);

	<S extends T> Page<T> findAll(PageRequest pageRequest, S example);

	Page<T> findAll(int page, int size, Direction direction, String... properties);

	<S extends T> Page<T> findAll(int page, int size, S example);

	<S extends T> Page<T> findAll(int page, int size, S example, Direction direction, String... properties);

	<S extends T> Page<T> findAll(int page, int size, S example, Sort sort);

}

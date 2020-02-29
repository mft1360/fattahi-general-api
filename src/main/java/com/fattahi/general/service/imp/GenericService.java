package com.fattahi.general.service.imp;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fattahi.general.model.BaseEntity;
import com.fattahi.general.service.IGenericService;

@Service
@Transactional(readOnly = false)
public abstract class GenericService<T extends BaseEntity<PK>, PK extends Serializable> implements IGenericService<T, PK> {

	@Value("${access.denied}")
	protected String accessDenied;

	@Value("${error}")
	protected String error;

	@Value("${no.edit}")
	protected String noEdit;

	@Value("${no.delete}")
	protected String noDelete;

	@Value("${dublicate.record}")
	protected String dublicateRecord;

	@PersistenceContext
	protected EntityManager entityManager;

	protected abstract JpaRepository<T, PK> getGenericRepo();

	protected Example<T> getExample(T example) {
		ExampleMatcher exampleMatcher = ExampleMatcher.matching().withStringMatcher(StringMatcher.CONTAINING)
				.withIgnoreCase();
		Example<T> exam = Example.of(example, exampleMatcher);
		return exam;
	}

	@Override
	public T findOne(PK id) {
		return getGenericRepo().findOne(id);
	}

	@Override
	public T save(T entity) {
		return getGenericRepo().save(entity);
	}

	@Override
	public void save(List<T> entities) {
		for (T t : entities) {
			this.save(t);
		}
	}

	@Override
	public void delete(T entity) {
		getGenericRepo().delete(entity);
	}

	@Override
	public void delete(List<T> entites) {
		getGenericRepo().delete(entites);
	}

	@Override
	public void delete(PK id) {
		getGenericRepo().delete(id);
	}

	@Override
	public List<T> findAll() {
		return getGenericRepo().findAll();
	}

	@Override
	public List<T> findAll(List<PK> ids) {
		return getGenericRepo().findAll(ids);
	}

	public List<T> findAll(Sort sort) {
		return getGenericRepo().findAll(sort);
	}

	public <S extends T> List<T> findAll(S example) {
		return getGenericRepo().findAll(getExample(example));
	}

	public <S extends T> List<T> findAll(S example, Sort sort) {
		return getGenericRepo().findAll(getExample(example), sort);
	}

	public <S extends T> Page<T> findAll(int page, int size) {
		return getGenericRepo().findAll(new PageRequest(page, size));
	}

	public Page<T> findAll(int page, int size, Sort sort) {
		return getGenericRepo().findAll(new PageRequest(page, size, sort));
	}

	public Page<T> findAll(PageRequest pageRequest) {
		return getGenericRepo().findAll(pageRequest);
	}

	public <S extends T> Page<T> findAll(PageRequest pageRequest, S example) {
		return getGenericRepo().findAll(getExample(example), pageRequest);
	}

	public Page<T> findAll(int page, int size, Direction direction, String... properties) {
		return getGenericRepo().findAll(new PageRequest(page, size, direction, properties));
	}

	public <S extends T> Page<T> findAll(int page, int size, S example) {
		return getGenericRepo().findAll(getExample(example), new PageRequest(page, size));
	}

	public <S extends T> Page<T> findAll(int page, int size, S example, Direction direction, String... properties) {
		return getGenericRepo().findAll(getExample(example), new PageRequest(page, size, direction, properties));
	}

	public <S extends T> Page<T> findAll(int page, int size, S example, Sort sort) {
		return getGenericRepo().findAll(getExample(example), new PageRequest(page, size));
	}

}

package com.fattahi.general.service.imp;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fattahi.general.model.BaseEntity;

@Service
public abstract class GenericServiceGet<T extends BaseEntity<PK>, PK extends Serializable>
		extends GenericService<T, PK> {

	@Override
	public void delete(List<T> entites) {
		throw new UnsupportedOperationException("Not implemented");
	}

	@Override
	public void delete(T entity) {
		throw new UnsupportedOperationException("Not implemented");
	}

	@Override
	public void delete(PK id) {
		throw new UnsupportedOperationException("Not implemented");
	}

	@Override
	public void save(List<T> entities) {
		throw new UnsupportedOperationException("Not implemented");
	}

	@Override
	public T save(T entity) {
		throw new UnsupportedOperationException("Not implemented");
	}

}

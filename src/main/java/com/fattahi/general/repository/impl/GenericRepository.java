package com.fattahi.general.repository.impl;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.fattahi.general.model.BaseEntity;
import com.fattahi.general.repository.IGenericRepository;

public abstract class GenericRepository<T extends BaseEntity<PK>, PK extends Serializable>
		implements IGenericRepository<T, PK> {

	protected Class<T> domainClass = getDomainClass();

	protected String domainClassName = getDomainClass().getName();

	protected abstract Class<T> getDomainClass();

	@PersistenceContext
	private EntityManager entityManager;

	protected EntityManager getEntityManager() {
		return entityManager;
	}
}
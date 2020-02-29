package com.fattahi.general.repository;

import java.io.Serializable;

import com.fattahi.general.model.BaseEntity;

public interface IGenericRepository<T extends BaseEntity<PK>, PK extends Serializable> {

}
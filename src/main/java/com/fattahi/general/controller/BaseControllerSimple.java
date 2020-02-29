package com.fattahi.general.controller;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.fattahi.general.exception.ApplicationExceptions;
import com.fattahi.general.model.BaseEntity;
import com.fattahi.general.page.SimplePageRequest;
import com.fattahi.general.page.SimplePageResponse;
import com.fattahi.general.service.ICustomMapper;
import com.fattahi.general.service.IGenericService;
import com.fattahi.general.view.BaseEntityView;

import io.swagger.annotations.ApiParam;

/**
 * Created by m.fattahi on 3/6/2016.
 */
public abstract class BaseControllerSimple<T extends BaseEntity<PK>, PK extends Serializable, R extends SimplePageRequest, D extends BaseEntityView<PK>> {

	@Autowired
	protected ICustomMapper mapper;

	protected abstract IGenericService<T, PK> getGenericSrv();

	@SuppressWarnings("unchecked")
	protected Class<T> getDomainClass() {
		return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

	@SuppressWarnings("unchecked")
	protected Class<D> getEntityViewEdit() {
		return (Class<D>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[3];
	}

	protected D findOne(@PathVariable PK id) {
		return mapper.onlyMap(getGenericSrv().findOne(id), getEntityViewEdit());
	}

	protected SimplePageResponse<D> findAllPagination(
			@RequestBody @Validated @ApiParam(required = false) R simplePageRequest, BindingResult bindingResult) {
		T sample = mapper.onlyMap(simplePageRequest, getDomainClass());
		Page<T> data = getGenericSrv().findAll(makePageRequest(simplePageRequest), sample);
		SimplePageResponse<D> retData = new SimplePageResponse<D>(
				mapper.mapList(data.getContent(), getEntityViewEdit()), data.getTotalElements());
		return retData;
	}

	protected void delete(@PathVariable PK id) {
		getGenericSrv().delete(id);
	}

	protected D insert(@RequestBody @Validated D view, BindingResult bindingResult) {
		if (view.getId() != null) {
			throw new ApplicationExceptions("Id may be null");
		}
		return mapper.onlyMap(getGenericSrv().save(mapper.onlyMap(view, getDomainClass())), getEntityViewEdit());
	}

	protected D update(@RequestBody @Validated D view, BindingResult bindingResult) {
		if (view.getId() == null) {
			throw new ApplicationExceptions("Id may not be null");
		}
		return mapper.onlyMap(getGenericSrv().save(mapper.onlyMap(view, getDomainClass())), getEntityViewEdit());
	}

	protected PageRequest makePageRequest(SimplePageRequest simplePageRequest) {
		int page = simplePageRequest.getPage();
		if (page >= 1) {
			page--;
		}
		int size = simplePageRequest.getSize();
		String sort = simplePageRequest.getSort().contains(" ") ? simplePageRequest.getSort().split(" ")[0]
				: simplePageRequest.getSort();
		Direction direction = simplePageRequest.getSort().contains(" ")
				? simplePageRequest.getSort().split(" ")[1].equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC
				: Sort.Direction.ASC;
		return new PageRequest(page, size, direction, sort);
	}

}

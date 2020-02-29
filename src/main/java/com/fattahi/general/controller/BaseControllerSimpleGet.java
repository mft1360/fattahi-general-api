package com.fattahi.general.controller;

import java.io.Serializable;

import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fattahi.general.aspect.CheckBindingResult;
import com.fattahi.general.aspect.CheckSecurity;
import com.fattahi.general.model.BaseEntity;
import com.fattahi.general.page.SimplePageRequest;
import com.fattahi.general.page.SimplePageResponse;
import com.fattahi.general.view.BaseEntityView;

import io.swagger.annotations.ApiParam;

/**
 * Created by m.fattahi on 3/6/2016.
 */
public abstract class BaseControllerSimpleGet<T extends BaseEntity<PK>, PK extends Serializable, R extends SimplePageRequest, D extends BaseEntityView<PK>>
		extends BaseControllerSimple<T, PK, R, D> {

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@CheckSecurity
	public D findOne(@PathVariable PK id) {
		return super.findOne(id);
	}

	@RequestMapping(value = "/findAllPagination", method = RequestMethod.POST)
	@CheckBindingResult
	@CheckSecurity
	public SimplePageResponse<D> findAllPagination(@RequestBody @Validated @ApiParam(required = false) R request,
			BindingResult bindingResult) {
		return super.findAllPagination(request, bindingResult);
	}

}

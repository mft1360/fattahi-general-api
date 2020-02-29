package com.fattahi.general.page;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SimplePageRequest {

	@Min(0)
	@JsonProperty("pageNumber")
	private int page;
	@Min(1)
	@Max(40)
	@JsonProperty("pageSize")
	private int size = 30;

	private String sort = "id";

	private Long id;

	public SimplePageRequest() {
	}

	public SimplePageRequest(int page, int size, String sort) {
		this.page = page;
		this.size = size;
		this.sort = sort;
	}

	public SimplePageRequest(int page, int size) {
		this.page = page;
		this.size = size;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}

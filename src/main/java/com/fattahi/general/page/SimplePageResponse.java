package com.fattahi.general.page;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SimplePageResponse<T> {

	@JsonProperty("entityList")
	private List<T> content;
	@JsonProperty("totalRecords")
	private long count;

	public List<T> getContent() {
		return content;
	}

	public SimplePageResponse() {
	}

	public SimplePageResponse(List<T> content, long count) {
		this.content = content;
		this.count = count;
	}

	public void setContent(List<T> content) {
		this.content = content;
	}

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

}

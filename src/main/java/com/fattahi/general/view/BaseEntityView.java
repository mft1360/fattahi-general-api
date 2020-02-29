package com.fattahi.general.view;

import java.io.Serializable;

public abstract class BaseEntityView<PK extends Serializable> implements Serializable {

	private static final long serialVersionUID = 4295229462159851306L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	private PK id;

	public PK getId() {
		return id;
	}

	public void setId(PK id) {
		this.id = id;
	}

	public String propIdToString(Integer id) {
		if (id != null) {
			return id.toString();
		}
		return null;
	}

}

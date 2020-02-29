package com.fattahi.general.service.imp;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fattahi.general.service.ICustomMapper;

/**
 * @author m.fatahi
 */
@Service
public class CustomMapper implements ICustomMapper {

	@Autowired(required = false)
	private Mapper mapper;

	@Override
	public <T> Collection<T> mapCollection(Collection<?> list, Class<T> viewClass) {
		Set<T> retValues = new HashSet<>();
		for (Object entity : list) {
			retValues.add(onlyMap(entity, viewClass));
		}
		return retValues;
	}

	@Override
	public <T> List<T> mapList(Collection<?> list, Class<T> viewClass) {
		List<T> retValues = new LinkedList<>();
		for (Object entity : list) {
			retValues.add(onlyMap(entity, viewClass));
		}
		return retValues;
	}

	@Override
	public <T> T onlyMap(Object source, Class<T> destinationClass) {
		if (source == null) {
			return null;
		}
		return mapper.map(source, destinationClass);
	}

}

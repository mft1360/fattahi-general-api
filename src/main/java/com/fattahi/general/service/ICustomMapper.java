package com.fattahi.general.service;

import java.util.Collection;
import java.util.List;

public interface ICustomMapper {

	<T> T onlyMap(Object source, Class<T> destinationClass);

	<T> Collection<T> mapCollection(Collection<?> list, Class<T> viewClass);

	<T> List<T> mapList(Collection<?> list, Class<T> viewClass);

}

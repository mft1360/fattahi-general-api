package com.fattahi.general.cache;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fattahi.general.model.FUser;
import com.fattahi.general.service.ICurrentUser;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

@Component("CacheService1")
@SuppressWarnings("unchecked")
public class CacheService implements ICacheService {

	private static CacheManager manager = null;

	@Autowired
	private ICurrentUser currentUser;

	@Override
	public void addCache(String cacheName, String key, Object data) {
		Cache cache = getCacheManager().getCache(cacheName);
		cache.remove(key);
		Element element = new Element(key, data);
		cache.put(element);
	}

	@Override
	public void addCache(String cacheName, Object data) {
		String key = ((FUser) currentUser.currentUser()).getUserName();
		Cache cache = getCacheManager().getCache(cacheName);
		cache.remove(key);
		Element element = new Element(key, data);
		cache.put(element);
	}

	@Override
	public <U> List<U> getAllCache(String cacheName, Class<U> transformerClass) {
		Cache cache = getCacheManager().getCache(cacheName);
		Map<Object, Element> elements = cache.getAll(cache.getKeys());
		if (elements == null) {
			return null;
		}
		List<U> rets = new ArrayList<U>();
		for (Object key : elements.keySet()) {
			rets.add((U) elements.get(key).getObjectValue());
		}
		return rets;
	}

	@Override
	public <U> U getCache(String cacheName, String key, Class<U> transformerClass) {
		Cache cache = getCacheManager().getCache(cacheName);
		Element element = cache.get(key);
		if (element == null) {
			return null;
		}
		Object data = element.getObjectValue();
		return (U) data;
	}

	@Override
	public <U> U getCache(String cacheName, Class<U> transformerClass) {
		String key = ((FUser) currentUser.currentUser()).getUserName();
		Cache cache = getCacheManager().getCache(cacheName);
		Element element = cache.get(key);
		if (element == null) {
			return null;
		}
		Object data = element.getObjectValue();
		return (U) data;
	}

	private CacheManager getCacheManager() {
		if (manager == null) {
			manager = CacheManager.create();
		}
		return manager;
	}

	@Override
	public void removeCache(String cacheName, String key) {
		Cache cache = getCacheManager().getCache(cacheName);
		cache.remove(key);
	}

	@Override
	public void removeCache(String cacheName) {
		String key = ((FUser) currentUser.currentUser()).getUserName();
		Cache cache = getCacheManager().getCache(cacheName);
		cache.remove(key);
	}

}

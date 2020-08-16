/**
 * 
 */
package org.yelong.core.cache.simple;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.yelong.core.cache.CacheEntity;
import org.yelong.core.cache.CacheEntitys;
import org.yelong.core.cache.CacheManager;
import org.yelong.core.cache.DefaultCacheEntity;

/**
 * 
 * 简单的缓存管理器，通过 {@link Map}实现对缓存对象的存储与管理
 * 
 * @since 1.3
 */
public class SimpleCacheManager implements CacheManager {

	private final Map<String, CacheEntity<?>> caches = new HashMap<>();

	private final String name;

	public SimpleCacheManager() {
		this(null);
	}

	public SimpleCacheManager(String name) {
		this.name = name;
	}

	@Override
	public synchronized <T> CacheEntity<T> putCache(String key, CacheEntity<T> cacheEntity) {
		caches.put(key, cacheEntity);
		return cacheEntity;
	}

	@Override
	public synchronized <T> CacheEntity<T> putCache(String key, T entity) {
		CacheEntity<T> cacheEntity = new DefaultCacheEntity<T>(entity);
		return putCache(key, cacheEntity);
	}

	@SuppressWarnings("unchecked")
	@Override
	public synchronized <T> CacheEntity<T> getCache(String key) {
		CacheEntity<T> cacheEntity = (CacheEntity<T>) caches.get(key);
		return cacheEntity != null ? cacheEntity : CacheEntitys.emptyCacheEntity();
	}

	@Override
	public synchronized Map<String, CacheEntity<?>> getCacheAll() {
		return Collections.unmodifiableMap(caches);
	}

	@Override
	public synchronized boolean containsKey(String key) {
		return caches.containsKey(key);
	}

	@SuppressWarnings("unchecked")
	@Override
	public synchronized <T> CacheEntity<T> remove(String key) {
		return (CacheEntity<T>) caches.remove(key);
	}

	@Override
	public void removeQuietly(String key) {
		caches.remove(key);
	}

	@Override
	public synchronized Set<String> getKeys() {
		return caches.keySet();
	}

	@Override
	public synchronized void clear() {
		caches.clear();
	}

	@Override
	public String getName() {
		return this.name;
	}

}

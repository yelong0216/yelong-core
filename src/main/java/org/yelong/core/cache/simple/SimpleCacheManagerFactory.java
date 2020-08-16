/**
 * 
 */
package org.yelong.core.cache.simple;

import java.util.ArrayList;
import java.util.List;

import org.yelong.core.cache.CacheManager;
import org.yelong.core.cache.CacheManagerFactory;

/**
 * 简单的缓存管理器工厂
 * 
 * @since 1.3
 */
public class SimpleCacheManagerFactory implements CacheManagerFactory {

	private final List<CacheManager> cacheManagers = new ArrayList<CacheManager>();

	private final String name;

	public SimpleCacheManagerFactory() {
		this(null);
	}

	public SimpleCacheManagerFactory(String name) {
		this.name = name;
	}

	@Override
	public CacheManager create() {
		SimpleCacheManager simpleCacheManager = new SimpleCacheManager();
		cacheManagers.add(simpleCacheManager);
		return simpleCacheManager;
	}

	@Override
	public CacheManager create(String name) {
		SimpleCacheManager simpleCacheManager = new SimpleCacheManager(name);
		cacheManagers.add(simpleCacheManager);
		return simpleCacheManager;
	}

	@Override
	public List<CacheManager> getHasCreate() {
		return cacheManagers;
	}

	@Override
	public String getName() {
		return name;
	}

}

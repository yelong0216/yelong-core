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
 * @author PengFei
 * @since 1.3.0
 */
public class SimpleCacheManagerFactory implements CacheManagerFactory {

	private final List<CacheManager> cacheManagers = new ArrayList<CacheManager>();

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

}

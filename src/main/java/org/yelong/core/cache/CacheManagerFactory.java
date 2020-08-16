/**
 * 
 */
package org.yelong.core.cache;

import java.util.List;

import org.yelong.core.annotation.Nullable;

/**
 * 缓存管理器工厂
 * 
 * @since 1.3
 */
public interface CacheManagerFactory {

	/**
	 * 创建缓存管理器
	 * 
	 * @return 缓存管理器
	 */
	CacheManager create();

	/**
	 * 创建缓存管理器并指定该管理器的名称
	 * 
	 * @param name 缓存管理器名称
	 * @return 缓存管理器
	 */
	CacheManager create(String name);

	/**
	 * 获取该工厂创建的所有缓存管理器
	 * 
	 * @return 本实例所创建的所有缓存管理器
	 */
	List<CacheManager> getHasCreate();

	/**
	 * @return 该工厂的名称
	 */
	@Nullable
	String getName();

}

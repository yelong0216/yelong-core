/**
 * 
 */
package org.yelong.core.cache;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import org.yelong.core.annotation.Nullable;

/**
 * 缓存管理器
 * 
 * @since 1.3
 */
public interface CacheManager {

	/**
	 * 添加一个缓存，如果缓存管理器中已经存在该缓存的键值，将替换原来的值
	 * 
	 * @param <T>         cache entity type
	 * @param key         key
	 * @param cacheEntity 缓存对象
	 * @return 缓存的对象
	 */
	<T> CacheEntity<T> putCache(String key, CacheEntity<T> cacheEntity);

	/**
	 * 添加一个缓存，如果缓存管理器中已经存在该缓存的键值，将替换原来的值
	 * 
	 * @param <T>    cache entity type
	 * @param key    key
	 * @param entity 缓存的实体对象
	 * @return 缓存的对象
	 */
	<T> CacheEntity<T> putCache(String key, T entity);

	/**
	 * 获取缓存的实体，如果不存在该键值的缓存则工具工厂获取值，在将值放入缓存中
	 * 
	 * @param <T>               cache entity type
	 * @param key               键值
	 * @param cacheValueFactory 缓存的值工厂
	 * @return 缓存的实体
	 * @since 2.1
	 */
	default <T> CacheEntity<T> putCacheIfAbsent(String key, Function<String, T> cacheValueFactory) {
		synchronized (this) {
			boolean containsKey = containsKey(key);
			if (containsKey) {
				return getCache(key);
			}
			T entity = cacheValueFactory.apply(key);
			CacheEntity<T> cacheEntity = CacheEntitys.defaultCacheEntity(entity);
			putCache(key, cacheEntity);
			return cacheEntity;
		}
	}

	/**
	 * 获取缓存的值，如果不存在该键值的缓存则工具工厂获取值，在将值放入缓存中
	 * 
	 * @param <T>               cache entity type
	 * @param key               键值
	 * @param cacheValueFactory 缓存的值工厂
	 * @return 缓存的值
	 * @since 2.1
	 */
	default <T> T putCacheObjIfAbsent(String key, Function<String, T> cacheValueFactory) {
		return putCacheIfAbsent(key, cacheValueFactory).get();
	}

	/**
	 * 获取缓存信息
	 * 
	 * @param <T> cache entity type
	 * @param key key
	 * @return 获取缓存中的缓存信息。如果缓存中不存在这个缓存则返回 {@link CacheEntitys#emptyCacheEntity()}
	 */
	<T> CacheEntity<T> getCache(String key);

	/**
	 * 获取缓存的实体
	 * 
	 * @param <T> cache entity type
	 * @param key key
	 * @return 缓存的对象。如果缓存中不存在这个缓存则返回 <code>null</code>
	 */
	@SuppressWarnings("unchecked")
	default <T> T getCacheObj(String key) {
		return (T) getCache(key).get();
	}

	/**
	 * @return 所有已经缓存的对象
	 */
	Map<String, CacheEntity<?>> getCacheAll();

	/**
	 * 缓存中是否存在该键值的缓存
	 * 
	 * @param key key
	 * @return <code>true</code> 缓存中存在该缓存
	 */
	boolean containsKey(String key);

	/**
	 * 移除一个缓存对象
	 * 
	 * @param <T> cache entity type
	 * @param key key
	 * @return 如果缓存中存在这个这个对象，则返回原来缓存的这个对象，否则返回
	 *         {@link CacheEntitys#emptyCacheEntity()}
	 */
	<T> CacheEntity<T> remove(String key);

	/**
	 * 直接移除缓存中该键值的对象
	 * 
	 * @param key key
	 */
	void removeQuietly(String key);

	/**
	 * @return 该缓存管理器中所有已经缓存的键值集合
	 */
	Set<String> getKeys();

	/**
	 * 清空该缓存管理器中的所有缓存对象
	 */
	void clear();

	/**
	 * @return 缓存管理器的名称
	 */
	@Nullable
	String getName();

}

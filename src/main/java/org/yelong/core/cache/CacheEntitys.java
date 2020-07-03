/**
 * 
 */
package org.yelong.core.cache;

/**
 * 缓存实体功能类<br/>
 * 
 * @author PengFei
 * @since 1.3.0
 */
public final class CacheEntitys {

	private CacheEntitys() {
	}

	private static final CacheEntity<?> EMPTY_CACHE_ENTITY = new EmptyCacheEntity<>();

	/**
	 * 空的缓存对象
	 * 
	 * @param <T> cache entity type
	 * @return 空的缓存对象
	 */
	@SuppressWarnings("unchecked")
	public static <T> CacheEntity<T> emptyCacheEntity() {
		return (CacheEntity<T>) EMPTY_CACHE_ENTITY;
	}

	/**
	 * 创建默认的缓存对象
	 * 
	 * @param <T>    cache entity type
	 * @param entity 缓存的对象
	 * @return 缓存实体
	 */
	public static <T> CacheEntity<T> defaultCacheEntity(T entity) {
		return new DefaultCacheEntity<T>(entity);
	}

	/**
	 * 空的缓存对象实体
	 * 
	 * @author PengFei
	 *
	 * @param <T> cache entity type
	 */
	private static final class EmptyCacheEntity<T> implements CacheEntity<T> {

		private static final long serialVersionUID = 8244659453211336766L;

		@Override
		public T get() {
			return null;
		}

		@Override
		public boolean isNull() {
			return true;
		}

		@Override
		public String toString() {
			return null;
		}

	}

}

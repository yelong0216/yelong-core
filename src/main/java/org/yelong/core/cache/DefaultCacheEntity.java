/**
 * 
 */
package org.yelong.core.cache;

/**
 * 默认的缓存实体
 * 
 * @param <T> cache entity type
 * @since 1.3
 */
public class DefaultCacheEntity<T> implements CacheEntity<T> {

	private static final long serialVersionUID = -576882953511119791L;

	private final T entity;

	public DefaultCacheEntity(final T entity) {
		this.entity = entity;
	}

	@Override
	public T get() {
		return this.entity;
	}

	@Override
	public boolean isNull() {
		return this.entity == null;
	}

	@Override
	public String toString() {
		if (null == entity) {
			return null;
		}
		return entity.toString();
	}

}

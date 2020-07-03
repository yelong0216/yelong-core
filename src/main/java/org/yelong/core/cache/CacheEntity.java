package org.yelong.core.cache;

import java.io.Serializable;

/**
 * 缓存的实体<br/>
 * 
 * 包装原缓存对象，并可以对缓存设置一些信息 <br/>
 * 
 * 提供一些对缓存对象操作的功能性方法
 * 
 * @author PengFei
 *
 * @param <T> cache entity type
 * @since 1.3.0
 */
public interface CacheEntity<T> extends Serializable {

	/**
	 * @return 获取进行缓存的对象
	 */
	T get();

	/**
	 * @return 缓存的对象是否是 <code>null</code>
	 */
	boolean isNull();

	/**
	 * @return 缓存的对象是否不是 <code>null</code>
	 */
	default boolean isNotNull() {
		return !isNull();
	}

}

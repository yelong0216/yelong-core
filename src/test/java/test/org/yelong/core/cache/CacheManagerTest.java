/**
 * 
 */
package test.org.yelong.core.cache;

import org.yelong.core.cache.CacheEntity;
import org.yelong.core.cache.CacheManager;
import org.yelong.core.cache.simple.SimpleCacheManager;

/**
 * @author PengFei
 *
 */
public class CacheManagerTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		CacheManager cacheManager = new SimpleCacheManager();
		cacheManager.putCache("ABC", "123456");
		cacheManager.putCache("ABC", "123");
		cacheManager.putCache("ABC", "456");
		CacheEntity<Integer> cache = cacheManager.getCache("ABC");
		Integer integer = cache.get();
		System.out.println(integer);

	}

}

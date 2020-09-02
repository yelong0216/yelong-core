/**
 * 
 */
package test.org.yelong.core.cache;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.yelong.core.cache.CacheEntity;
import org.yelong.core.cache.CacheManager;
import org.yelong.core.cache.CacheManagerFactory;
import org.yelong.core.cache.simple.SimpleCacheManagerFactory;

/**
 *
 */
public class CacheManagerFactoryTest {

	CacheManagerFactory cacheManagerFactory = new SimpleCacheManagerFactory("简单工厂");

	CacheManager cacheManager = cacheManagerFactory.create("");

	@Test
	public void test() {
		CacheManager cacheManager1 = cacheManagerFactory.create("cache1");
		cacheManager1.putCache("01", "123456");
		System.out.println((String) cacheManager1.getCacheObj("02"));
		List<CacheManager> creates = cacheManagerFactory.getHasCreate();
		System.out.println(creates);
	}

	@Test
	public void putCacheIfAbsent() {
		CacheEntity<String> cacheEntity = cacheManager.putCacheIfAbsent("abc", x -> "value");
		System.out.println(cacheEntity);
		CacheEntity<String> cacheEntity1 = cacheManager.putCacheIfAbsent("abc", x -> "valueasdf");
		System.out.println(cacheEntity1);
	}
	
}

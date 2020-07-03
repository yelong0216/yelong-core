/**
 * 
 */
package org.yelong.core.model.map;

import java.util.List;
import java.util.Set;

/**
 * 支持缓存的列获取策略
 * 
 * @author PengFei
 * @since 1.3.0
 */
public interface CacheSupportMapModelFieldAndColumnGetStrategy extends MapModelFieldAndColumnGetStrategy {

	/**
	 * 获取 map model 的所有映射列。<br/>
	 * 如果缓存中存在此model的列则从缓存中获取。<br/>
	 * 缓存中没有在获取第一次后将列信息放置缓存中，之后获取该 model 列信息时从缓存中获取。<br/>
	 * 
	 * @param mapModelAndTable 模型与表
	 * @return 模型与表映射的字段
	 */
	@Override
	List<MapModelFieldAndColumn> get(MapModelAndTable mapModelAndTable);

	/**
	 * 删该 MapModel 的缓存信息
	 * 
	 * @param mapModelAndTable map model and table
	 * @return 如果缓存中原本存在该 MapModel 的缓存则返回原有的缓存，否则返回 <code>null</code>
	 */
	List<MapModelFieldAndColumn> removeCache(MapModelAndTable mapModelAndTable);

	/**
	 * @return 获取所有缓存中的 map model and table
	 */
	Set<MapModelAndTable> getCacheMapModelAndTable();

	/**
	 * 清空缓存
	 */
	void clearCache();

}

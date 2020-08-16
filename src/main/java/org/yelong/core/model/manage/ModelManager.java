/**
 * 
 */
package org.yelong.core.model.manage;

import java.util.Map;

import org.yelong.core.annotation.Nullable;
import org.yelong.core.model.Modelable;
import org.yelong.core.model.resolve.ModelResolver;
import org.yelong.core.model.resolve.ModelResolverManager;

/**
 * 管理模型与模型表的映射。 对已经解析过的模型做一个轻量的缓存， 使该模型第二次获取模型表对象时不用再次进行解析，
 * 而是从缓存中取出该模型已经解析过的模型表对象。<br/>
 * 
 * 这些缓存是可以进行管理了，使用{@link #cleanCacheModelAndTable()},{@link #removeCacheModelAndTable(Class)}可以移除、清空已经缓存过的模型表
 * 
 * @since 2.0
 */
public interface ModelManager {

	/**
	 * 获取该模型对应的模型表，如果在缓存中已经存在该模型对应的模型表则直接从缓存中获取。 缓存中不存在时在使用
	 * 模型解析器({@link ModelResolver}) 将模型进行解析为模型表，并将该模型与模型表放入缓存中
	 * 
	 * @param modelClass model class
	 * @return 模型对应的模型表
	 */
	default ModelAndTable getModelAndTable(Class<? extends Modelable> modelClass) {
		return getModelAndTable(modelClass, false);
	}

	/**
	 * 获取该模型对应的模型表，如果不忽略缓存且缓存中存在该模型对应的模型表时，直接从缓存中获取模型表。
	 * 在忽略缓存时将重新对该模型进行解析，并将解析后的模型表重新放入缓存中
	 * 
	 * @param modelClass  model class
	 * @param ignoreCache 是否忽略缓存
	 * @return 模型对应的模型表
	 */
	ModelAndTable getModelAndTable(Class<? extends Modelable> modelClass, boolean ignoreCache);

	/**
	 * 获取模型映射的表名称。如果模型表在缓存中存在则直接从缓存中获取
	 * 
	 * @param modelClass model class
	 * @return 模型映射的表名称
	 */
	@Nullable
	default String getTableName(Class<? extends Modelable> modelClass) {
		return getModelAndTable(modelClass).getTableName();
	}

	/**
	 * 获取所有在缓存中的模型与模型表的映射
	 * 
	 * @return 所有在缓存中的模型与模型表的映射
	 */
	Map<Class<? extends Modelable>, ModelAndTable> getModelAndTableMap();

	/**
	 * 清空已经缓存的模型表映射
	 * 
	 * @see Map#clear()
	 */
	void clearCacheModelAndTable();

	/**
	 * 移除指定模型类型对应的模型表缓存
	 * 
	 * @param modelClass model class
	 * @see Map#remove(Object)
	 */
	void removeCacheModelAndTable(Class<? extends Modelable> modelClass);

	/**
	 * @return 模型解析管理器
	 */
	ModelResolverManager getModelResolverManager();

}

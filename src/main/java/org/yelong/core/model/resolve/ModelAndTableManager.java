/**
 * 
 */
package org.yelong.core.model.resolve;

import java.util.HashMap;
import java.util.Map;

import org.yelong.core.model.Modelable;

/**
 * model 管理器
 * 
 * 对{@link ModelAndTable}进行单例的管理。<br/>
 * 将model解析后进行存储，使model仅被解析一次，避免每次使用每次在对model进行解析。
 * 
 * @author PengFei
 * @see ModelResolver
 */
public class ModelAndTableManager {

	private final Map<String, ModelAndTable> modelAndTableMap = new HashMap<>();

	private final ModelResolver modelResolver;

	public ModelAndTableManager(ModelResolver modelResolver) {
		this.modelResolver = modelResolver;
	}

	/**
	 * 获取model的模型与表信息
	 * 
	 * @param <M>        model type
	 * @param modelClass 模型类
	 * @return 模型与表的映射信息
	 */
	public <M extends Modelable> ModelAndTable getModelAndTable(Class<M> modelClass) {
		ModelAndTable modelAndTable = modelAndTableMap.get(modelClass.getName());
		if (null == modelAndTable) {
			synchronized (this) {
				modelAndTable = modelAndTableMap.get(modelClass.getName());
				if (null != modelAndTable) {
					return modelAndTable;
				}
				modelAndTable = modelResolver.resolve(modelClass);
				modelAndTableMap.put(modelClass.getName(), modelAndTable);
			}
		}
		return modelAndTable;
	}

	/**
	 * 获取 model 映射的表名称
	 * 
	 * @param <M>        model type
	 * @param modelClass model class
	 * @return 表名称
	 */
	public <M extends Modelable> String getTableName(Class<M> modelClass) {
		return getModelAndTable(modelClass).getTableName();
	}

	/**
	 * 获取表的别名
	 * 
	 * @param <M>        model type
	 * @param modelClass model class
	 * @return 表别名
	 */
	public <M extends Modelable> String getTableAlias(Class<M> modelClass) {
		return getModelAndTable(modelClass).getTableAlias();
	}

	/**
	 * @return model 解析器
	 */
	public ModelResolver getModelResolver() {
		return modelResolver;
	}

}

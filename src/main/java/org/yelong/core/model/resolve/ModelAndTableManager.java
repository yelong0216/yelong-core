/**
 * 
 */
package org.yelong.core.model.resolve;

import java.util.HashMap;
import java.util.Map;

import org.yelong.core.model.Modelable;
import org.yelong.core.model.exception.ModelException;

/**
 * @author PengFei
 */
public class ModelAndTableManager {
	
	private final Map<String,ModelAndTable> modelAndTableMap = new HashMap<>();
	
	private final ModelResolver modelResolver;
	
	public ModelAndTableManager(ModelResolver modelResolver) {
		this.modelResolver = modelResolver;
	}
	
	/**
	 * 获取model的模型与表信息
	 * @param <M>
	 * @param modelClass 模型类
	 * @return 模型与表的映射信息
	 */
	public <M extends Modelable> ModelAndTable getModelAndTable(Class<M> modelClass) {
		ModelAndTable modelAndTable = modelAndTableMap.get(modelClass.getName());
		if( null == modelAndTable ) {
			synchronized (this) {
				modelAndTable = modelAndTableMap.get(modelClass.getName());
				if( null != modelAndTable ) {
					return modelAndTable;
				}
				modelAndTable = modelResolver.resolve(modelClass);
				modelAndTableMap.put(modelClass.getName(), modelAndTable);
			}
		}
		return modelAndTable;
	}

	/**
	 * @param modelClass
	 * @return 表名称
	 * @throws ModelException
	 */
	public <M extends Modelable> String getTableName(Class<M> modelClass){
		return getModelAndTable(modelClass).getTableName();
	}
	
	/**
	 * @param <M>
	 * @param modelClass
	 * @return 表别名
	 */
	public <M extends Modelable> String getTableAlias(Class<M> modelClass) {
		return getModelAndTable(modelClass).getTableAlias();
	}
	
	public ModelResolver getModelResolver() {
		return modelResolver;
	}
	
}

/**
 * 
 */
package org.yelong.core.model.collector.support.remove.single.contains;

import org.yelong.core.model.Modelable;
import org.yelong.core.model.collector.support.remove.RemoveModelCollector;

/**
 * 根据单列包含某些值进行删除的模型收集器
 * 
 * @param <M> model type
 * @since 1.3
 */
public interface RemoveBySingleColumnContainsModelCollector<M extends Modelable> extends RemoveModelCollector<M> {

	/**
	 * 删除的前置处理
	 * 
	 * @param preProcess 前置处理
	 * @return this
	 */
	RemoveBySingleColumnContainsModelCollector<M> preProcess(RemoveBySingleColumnContainsPreProcess preProcess);

	/**
	 * 删除执行器
	 * 
	 * @param removeModelExecutor 删除模型执行器
	 * @return this
	 */
	RemoveBySingleColumnContainsModelCollector<M> removeModel(
			RemoveModelBySingleColumnContainsExecutor removeModelExecutor);

	/**
	 * 删除后的后置处理
	 * 
	 * @param postProcess 后置处理
	 * @return this
	 */
	RemoveBySingleColumnContainsModelCollector<M> postProcess(RemoveBySingleColumnContainsPostProcess postProcess);

	/**
	 * @return 条件列
	 */
	String getConditionColumn();

	/**
	 * @return 条件值数组
	 */
	Object[] getConditionColumnValues();

}

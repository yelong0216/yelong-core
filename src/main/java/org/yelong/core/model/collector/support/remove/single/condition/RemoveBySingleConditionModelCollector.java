/**
 * 
 */
package org.yelong.core.model.collector.support.remove.single.condition;

import org.yelong.core.model.Modelable;
import org.yelong.core.model.collector.support.remove.RemoveModelCollector;

/**
 * 根据但条件删除的模型收集器
 * 
 * @author PengFei
 *
 * @param <M> model type
 * @since 1.3.0
 */
public interface RemoveBySingleConditionModelCollector<M extends Modelable> extends RemoveModelCollector<M> {

	/**
	 * 前置处理
	 * 
	 * @param preProcess 前置处理操作
	 * @return this
	 */
	RemoveBySingleConditionModelCollector<M> preProcess(RemoveBySingleConditionPreProcess preProcess);

	/**
	 * 删除执行器
	 * 
	 * @param removeModelExecutor 删除模型执行器
	 * @return this
	 */
	RemoveBySingleConditionModelCollector<M> removeModel(RemoveModelBySingleConditionExecutor removeModelExecutor);

	/**
	 * 后置处理
	 * 
	 * @param postProcess 后置处理操作
	 * @return this
	 */
	RemoveBySingleConditionModelCollector<M> postProcess(RemoveBySingleConditionPostProcess postProcess);

}

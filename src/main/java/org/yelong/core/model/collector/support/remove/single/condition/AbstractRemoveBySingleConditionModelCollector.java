/**
 * 
 */
package org.yelong.core.model.collector.support.remove.single.condition;

import org.yelong.core.model.Modelable;
import org.yelong.core.model.collector.support.remove.impl.AbstractRemoveModelCollector;

/**
 * 抽象的根据单条件删除模型收集器
 * 
 * @author PengFei
 *
 * @param <M> model type
 * @since 1.3.0
 */
public abstract class AbstractRemoveBySingleConditionModelCollector<M extends Modelable>
		extends AbstractRemoveModelCollector<M> implements RemoveBySingleConditionModelCollector<M> {

	protected RemoveBySingleConditionPreProcess preProcess;

	protected RemoveModelBySingleConditionExecutor removeModelExecutor;

	protected RemoveBySingleConditionPostProcess postProcess;

	public AbstractRemoveBySingleConditionModelCollector(Class<M> modelClass) {
		super(modelClass);
	}

	@Override
	public RemoveBySingleConditionModelCollector<M> preProcess(RemoveBySingleConditionPreProcess preProcess) {
		this.preProcess = preProcess;
		return this;
	}

	@Override
	public RemoveBySingleConditionModelCollector<M> removeModel(
			RemoveModelBySingleConditionExecutor removeModelExecutor) {
		this.removeModelExecutor = removeModelExecutor;
		return this;
	}

	@Override
	public RemoveBySingleConditionModelCollector<M> postProcess(RemoveBySingleConditionPostProcess postProcess) {
		this.postProcess = postProcess;
		return this;
	}

}

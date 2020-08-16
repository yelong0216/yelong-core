/**
 * 
 */
package org.yelong.core.model.collector.support.remove.single.contains;

import org.yelong.core.model.Modelable;
import org.yelong.core.model.collector.support.remove.impl.AbstractRemoveModelCollector;

/**
 * 抽象的根据单列包含某些值进行删除的模型收集器
 * 
 * @param <M> model type
 * @since 1.3
 */
public abstract class AbstractRemoveBySingleColumnContainsModelCollector<M extends Modelable>
		extends AbstractRemoveModelCollector<M> implements RemoveBySingleColumnContainsModelCollector<M> {

	protected RemoveBySingleColumnContainsPreProcess preProcess;

	protected RemoveModelBySingleColumnContainsExecutor removeModelExecutor;

	protected RemoveBySingleColumnContainsPostProcess postProcess;

	public AbstractRemoveBySingleColumnContainsModelCollector(Class<M> modelClass) {
		super(modelClass);
	}

	@Override
	public RemoveBySingleColumnContainsModelCollector<M> preProcess(RemoveBySingleColumnContainsPreProcess preProcess) {
		this.preProcess = preProcess;
		return this;
	}

	@Override
	public RemoveBySingleColumnContainsModelCollector<M> removeModel(
			RemoveModelBySingleColumnContainsExecutor removeModelExecutor) {
		this.removeModelExecutor = removeModelExecutor;
		return this;
	}

	@Override
	public RemoveBySingleColumnContainsModelCollector<M> postProcess(
			RemoveBySingleColumnContainsPostProcess postProcess) {
		this.postProcess = postProcess;
		return this;
	}

}

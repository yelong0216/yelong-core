/**
 * 
 */
package org.yelong.core.model.collector.support.remove.impl;

import org.yelong.core.model.Modelable;
import org.yelong.core.model.collector.ModelCollectorImpl;
import org.yelong.core.model.collector.support.remove.RemoveModelCollector;

/**
 * 抽象的删除模型收集器
 * 
 * @param <M> model type
 * @since 1.3
 */
public abstract class AbstractRemoveModelCollector <M extends Modelable> extends ModelCollectorImpl<M, Integer, RemoveModelCollector<M>> implements RemoveModelCollector<M>{

	public AbstractRemoveModelCollector(Class<M> modelClass) {
		super(modelClass);
	}

}

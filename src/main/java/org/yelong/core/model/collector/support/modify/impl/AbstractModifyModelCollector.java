/**
 * 
 */
package org.yelong.core.model.collector.support.modify.impl;

import org.yelong.core.model.Modelable;
import org.yelong.core.model.collector.ModelCollectorImpl;
import org.yelong.core.model.collector.support.modify.ModifyModelCollector;

/**
 * 抽象的修改模型收集器
 * 
 * @param <M> model type
 * @since 1.3
 */
public abstract class AbstractModifyModelCollector<M extends Modelable>
		extends ModelCollectorImpl<M, Integer, ModifyModelCollector<M>> implements ModifyModelCollector<M> {

	public AbstractModifyModelCollector(Class<M> modelClass) {
		super(modelClass);
	}

}

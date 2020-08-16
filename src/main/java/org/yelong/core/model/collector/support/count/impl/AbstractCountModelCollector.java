/**
 * 
 */
package org.yelong.core.model.collector.support.count.impl;

import org.yelong.core.model.Modelable;
import org.yelong.core.model.collector.ModelCollectorImpl;
import org.yelong.core.model.collector.support.count.CountModelCollector;

/**
 * 抽象的查询记录数模型收集器
 * 
 * @param <M> model type
 * @since 2.0
 */
public abstract class AbstractCountModelCollector<M extends Modelable>
		extends ModelCollectorImpl<M, Long, CountModelCollector<M>> implements CountModelCollector<M> {

	public AbstractCountModelCollector(Class<M> modelClass) {
		super(modelClass);
	}

}

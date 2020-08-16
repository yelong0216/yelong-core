/**
 * 
 */
package org.yelong.core.model.collector.support.exist.impl;

import org.yelong.core.model.Modelable;
import org.yelong.core.model.collector.ModelCollectorImpl;
import org.yelong.core.model.collector.support.exist.ExistModelCollector;

/**
 * 
 * 抽象的查询模型是否存在实现
 * 
 * @param <M> model type
 * @since 2.0
 */
public abstract class AbstractExistModelCollector<M extends Modelable> extends ModelCollectorImpl<M, Boolean, ExistModelCollector<M>> implements ExistModelCollector<M>{

	public AbstractExistModelCollector(Class<M> modelClass) {
		super(modelClass);
	}

}

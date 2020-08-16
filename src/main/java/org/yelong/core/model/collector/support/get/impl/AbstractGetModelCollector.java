/**
 * 
 */
package org.yelong.core.model.collector.support.get.impl;

import org.yelong.core.model.Modelable;
import org.yelong.core.model.collector.ModelCollectorImpl;
import org.yelong.core.model.collector.support.get.GetModelCollector;

/**
 * 抽象的获取模型收集器
 * 
 * @param <M> model type
 * @since 1.3
 */
public abstract class AbstractGetModelCollector<M extends Modelable>
		extends ModelCollectorImpl<M, M, GetModelCollector<M>> implements GetModelCollector<M> {

	public AbstractGetModelCollector(Class<M> modelClass) {
		super(modelClass);
	}

}

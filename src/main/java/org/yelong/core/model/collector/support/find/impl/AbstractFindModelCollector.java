/**
 * 
 */
package org.yelong.core.model.collector.support.find.impl;

import java.util.List;

import org.yelong.core.model.Modelable;
import org.yelong.core.model.collector.ModelCollectorImpl;
import org.yelong.core.model.collector.support.find.FindModelCollector;

/**
 * 抽象的查询模型收集器
 * 
 * @author PengFei
 *
 * @param <M> model type
 * @since 1.3.0
 */
public abstract class AbstractFindModelCollector<M extends Modelable>
		extends ModelCollectorImpl<M, List<M>, FindModelCollector<M>> implements FindModelCollector<M> {

	public AbstractFindModelCollector(Class<M> modelClass) {
		super(modelClass);
	}

}

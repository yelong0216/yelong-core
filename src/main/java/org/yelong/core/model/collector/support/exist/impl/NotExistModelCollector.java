/**
 * 
 */
package org.yelong.core.model.collector.support.exist.impl;

import org.yelong.core.model.Modelable;
import org.yelong.core.model.service.SqlModelService;

/**
 * 不存在的模型收集器<br/>
 * 
 * 该收集器总会返回一个 <code>false</code>
 * 
 * @param <M> model type
 * @since 2.0
 */
public final class NotExistModelCollector<M extends Modelable> extends AbstractExistModelCollector<M> {

	public NotExistModelCollector(Class<M> modelClass) {
		super(modelClass);
	}

	@Override
	protected Boolean doCollect(SqlModelService modelService) {
		return false;
	}

}

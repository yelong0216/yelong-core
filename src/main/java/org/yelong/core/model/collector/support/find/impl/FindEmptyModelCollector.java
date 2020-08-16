/**
 * 
 */
package org.yelong.core.model.collector.support.find.impl;

import java.util.Collections;
import java.util.List;

import org.yelong.core.model.Modelable;
import org.yelong.core.model.service.SqlModelService;

/**
 * 查询空的模型收集器
 * 
 * 返回空集合的实现
 * 
 * @param <M> model type
 * @see Collections#emptyList()
 * @since 2.0
 */
public class FindEmptyModelCollector<M extends Modelable> extends AbstractFindModelCollector<M> {

	public FindEmptyModelCollector(Class<M> modelClass) {
		super(modelClass);
	}

	@Override
	protected List<M> doCollect(SqlModelService modelService) {
		return Collections.emptyList();
	}

}

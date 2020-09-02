/**
 * 
 */
package org.yelong.core.model.collector.support.find.impl;

import java.util.Collections;
import java.util.List;

import org.yelong.core.model.Modelable;
import org.yelong.core.model.service.SqlModelService;

/**
 * 抽象的查询单列收集器
 * 
 * @since 2.1
 */
public class FindEmptySingleColumnModelCollector<M extends Modelable, T>
		extends AbstractFindSingleColumnModelCollector<M, T> {

	public FindEmptySingleColumnModelCollector(Class<M> modelClass, String selectColumn) {
		super(modelClass, selectColumn);
	}

	@Override
	protected List<T> doCollect(SqlModelService modelService) {
		return Collections.emptyList();
	}

}

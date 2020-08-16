/**
 * 
 */
package org.yelong.core.model.collector.support.count.impl;

import org.yelong.core.model.Modelable;
import org.yelong.core.model.service.SqlModelService;

/**
 * 空的查询记录数模型收集器<br/>
 * 
 * 不执行数据库查询操作，直接返回 0 
 * 
 * @param <M> model type
 * @since 2.0
 */
public final class CountEmptyModelCollector<M extends Modelable> extends AbstractCountModelCollector<M> {

	public CountEmptyModelCollector(Class<M> modelClass) {
		super(modelClass);
	}

	@Override
	protected Long doCollect(SqlModelService modelService) {
		return 0L;
	}

}

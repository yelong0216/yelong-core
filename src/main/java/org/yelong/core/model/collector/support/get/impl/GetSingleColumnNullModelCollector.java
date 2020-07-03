package org.yelong.core.model.collector.support.get.impl;

import org.yelong.core.model.Modelable;
import org.yelong.core.model.service.SqlModelService;

/**
 * 获取单列 <code>null</code> 模型收集器
 * 
 * @author PengFei
 *
 * @param <M> model type
 * @param <T> return type
 * @since 1.3.0
 */
public class GetSingleColumnNullModelCollector<M extends Modelable, T>
		extends AbstractGetSingleColumnModelCollector<M, T> {

	public GetSingleColumnNullModelCollector(Class<M> modelClass, String selectColumn) {
		super(modelClass, selectColumn);
	}

	@Override
	protected T doCollect(SqlModelService modelService) {
		return null;
	}

}

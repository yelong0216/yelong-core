/**
 * 
 */
package org.yelong.core.model.collector.support.get.impl;

import org.yelong.core.model.Modelable;
import org.yelong.core.model.service.SqlModelService;

/**
 * 获取 <code>null</code> 模型收集器
 * 
 * 主要用在需要返回 <code>null</code> 时进行的实现
 * 
 * @author PengFei
 *
 * @param <M> model type
 * @since 1.3.0
 */
public final class GetNullModelCollector<M extends Modelable> extends AbstractGetModelCollector<M> {

	public GetNullModelCollector(Class<M> modelClass) {
		super(modelClass);
	}

	@Override
	protected M doCollect(SqlModelService modelService) {
		return null;
	}

}

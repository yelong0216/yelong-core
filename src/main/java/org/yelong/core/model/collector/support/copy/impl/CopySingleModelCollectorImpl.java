/**
 * 
 */
package org.yelong.core.model.collector.support.copy.impl;

import org.yelong.core.annotation.Nullable;
import org.yelong.core.model.Modelable;
import org.yelong.core.model.service.SqlModelService;

/**
 * 单个模型复制收集器实现
 * 
 * @author PengFei
 *
 * @param <M> model type
 * @since 1.3.0
 */
public class CopySingleModelCollectorImpl<M extends Modelable> extends AbstractCopySingleModelCollector<M> {

	@Nullable
	private final M model;

	public CopySingleModelCollectorImpl(Class<M> modelClass, @Nullable M model) {
		super(modelClass);
		this.model = model;
	}

	@Override
	public M getCopySourceModel() {
		return model;
	}

	@Override
	protected M getCopySourceModel(SqlModelService modelService) {
		return model;
	}

}

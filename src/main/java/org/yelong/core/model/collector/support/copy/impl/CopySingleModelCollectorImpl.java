/**
 * 
 */
package org.yelong.core.model.collector.support.copy.impl;

import java.util.Objects;

import org.yelong.core.annotation.Nullable;
import org.yelong.core.model.Modelable;
import org.yelong.core.model.service.SqlModelService;

/**
 * 单个模型复制收集器实现
 * 
 * @param <M> model type
 * @since 1.3
 */
public class CopySingleModelCollectorImpl<M extends Modelable> extends AbstractCopySingleModelCollector<M> {

	@Nullable
	private final M model;

	@SuppressWarnings("unchecked")
	public CopySingleModelCollectorImpl(@Nullable M model) {
		super((Class<M>) Objects.requireNonNull(model).getClass());
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

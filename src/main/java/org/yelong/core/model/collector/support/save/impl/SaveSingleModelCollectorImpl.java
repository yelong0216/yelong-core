/**
 * 
 */
package org.yelong.core.model.collector.support.save.impl;

import org.yelong.core.annotation.Nullable;
import org.yelong.core.model.Modelable;
import org.yelong.core.model.service.SqlModelService;

/**
 * 保存单个模型收集器实现
 * 
 * @author PengFei
 *
 * @param <M> model type
 * @since 1.3.0
 */
public class SaveSingleModelCollectorImpl<M extends Modelable> extends AbstractSaveSingleModelCollector<M> {

	@Nullable
	private final M model;

	public SaveSingleModelCollectorImpl(Class<M> modelClass, @Nullable M model) {
		super(modelClass);
		this.model = model;
	}

	@Override
	protected M getSaveModel(SqlModelService modelService) {
		return model;
	}

	@Override
	public M getSaveModel() {
		return model;
	}

}

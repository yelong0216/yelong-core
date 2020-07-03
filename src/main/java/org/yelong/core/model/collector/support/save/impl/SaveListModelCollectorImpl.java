/**
 * 
 */
package org.yelong.core.model.collector.support.save.impl;

import java.util.List;
import java.util.Objects;

import org.yelong.core.model.Modelable;
import org.yelong.core.model.service.SqlModelService;

/**
 * 保存模型集合收集器实现
 * 
 * @author PengFei
 *
 * @param <M> model type
 * @since 1.3.0
 */
public class SaveListModelCollectorImpl<M extends Modelable> extends AbstractSaveListModelCollector<M> {

	private final List<M> models;

	public SaveListModelCollectorImpl(Class<M> modelClass, List<M> models) {
		super(modelClass);
		this.models = Objects.requireNonNull(models);
	}

	@Override
	protected List<M> getSaveModels(SqlModelService modelService) {
		return models;
	}

	@Override
	public List<M> getSaveModels() {
		return models;
	}

}

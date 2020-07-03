/**
 * 
 */
package org.yelong.core.model.collector.support.copy.impl;

import java.util.List;
import java.util.Objects;

import org.yelong.core.model.Modelable;
import org.yelong.core.model.service.SqlModelService;

/**
 * 集合模型复制收集器实现
 * 
 * @author PengFei
 *
 * @param <M> model type
 * @since 1.3.0
 */
public class CopyListModelCollectorImpl<M extends Modelable> extends AbstractCopyListModelCollector<M> {

	private final List<M> models;

	public CopyListModelCollectorImpl(Class<M> modelClass, List<M> models) {
		super(modelClass);
		this.models = Objects.requireNonNull(models);
	}

	@Override
	public List<M> getCopySourceModels() {
		return models;
	}

	@Override
	protected List<M> getCopySourceModels(SqlModelService modelService) {
		return models;
	}

}

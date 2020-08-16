/**
 * 
 */
package org.yelong.core.model.collector.support.save.impl;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.yelong.core.annotation.Nullable;
import org.yelong.core.model.Modelable;
import org.yelong.core.model.collector.support.save.SaveSingleModelCollector;
import org.yelong.core.model.service.SqlModelService;

/**
 * 抽象的保存单个模型收集器
 * 
 * @param <M> model type
 * @since 1.3
 */
public abstract class AbstractSaveSingleModelCollector<M extends Modelable> extends AbstractSaveModelCollector<M, M>
		implements SaveSingleModelCollector<M> {

	protected AbstractSaveSingleModelCollector(Class<M> modelClass) {
		super(modelClass);
	}

	@Override
	protected M doCollect(SqlModelService modelService) {
		M model = getSaveModel(modelService);
		List<M> models = new SaveListModelCollectorImpl<M>(modelClass, Arrays.asList(model)).preProcess(savePreProcess)
				.beforeSave(beforeSaveModel).saveModel(saveModelExecutor).afterSave(afterSaveModel)
				.postProcess(savePostProcess).collect(modelService);
		return CollectionUtils.isNotEmpty(models) ? models.get(0) : null;
	}

	@Nullable
	protected abstract M getSaveModel(SqlModelService modelService);

	@Override
	public M getSaveModel() {
		throw new UnsupportedOperationException();
	}

}

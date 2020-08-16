/**
 * 
 */
package org.yelong.core.model.collector.support.copy.impl;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.yelong.core.annotation.Nullable;
import org.yelong.core.model.Modelable;
import org.yelong.core.model.collector.support.copy.CopySingleModelCollector;
import org.yelong.core.model.service.SqlModelService;

/**
 * 抽象的单个模型复制收集器
 * 
 * @param <M> model type
 * @since 1.3
 */
public abstract class AbstractCopySingleModelCollector<M extends Modelable> extends AbstractCopyModelCollector<M, M>
		implements CopySingleModelCollector<M> {

	protected AbstractCopySingleModelCollector(Class<M> modelClass) {
		super(modelClass);
	}

	@Override
	protected M doCollect(SqlModelService modelService) {
		M model = getCopySourceModel(modelService);
		if (null == model) {
			return null;
		}
		List<M> models = new CopyListModelCollectorImpl<M>(modelClass, Arrays.asList(model)).preProcess(copyPreProcess)
				.beforeSave(beforeSaveModel).saveModel(saveModelExecutor).afterSave(afterSaveModel)
				.postProcess(copyPostProcess).collect(modelService);
		return CollectionUtils.isNotEmpty(models) ? models.get(0) : null;
	}

	@Nullable
	protected abstract M getCopySourceModel(SqlModelService modelService);

	@Override
	public M getCopySourceModel() {
		throw new UnsupportedOperationException();
	}

}

/**
 * 
 */
package org.yelong.core.model.collector.support.save.impl;

import java.util.List;

import org.yelong.core.model.Modelable;
import org.yelong.core.model.collector.support.ProcessConfig;
import org.yelong.core.model.collector.support.save.SaveListModelCollector;
import org.yelong.core.model.service.SqlModelService;

/**
 * 抽象的保存模型集合收集器
 * 
 * @param <M> model type
 * @since 1.3
 */
public abstract class AbstractSaveListModelCollector<M extends Modelable> extends AbstractSaveModelCollector<M, List<M>>
		implements SaveListModelCollector<M> {

	protected AbstractSaveListModelCollector(Class<M> modelClass) {
		super(modelClass);
	}

	@Override
	protected List<M> doCollect(SqlModelService modelService) {
		List<M> models = getSaveModels(modelService);
		ProcessConfig processConfig = new ProcessConfig(modelService, modelClass);
		if (null != savePreProcess)
			savePreProcess.process(processConfig, models);
		for (M model : models) {
			if (null != beforeSaveModel) {
				beforeSaveModel.process(processConfig, model);
			}
			if (null != saveModelExecutor) {
				saveModelExecutor.save(processConfig, model);
			} else {
				modelService.saveSelective(model);
			}
			if (null != afterSaveModel) {
				afterSaveModel.process(processConfig, model);
			}
		}
		if (null != savePostProcess) {
			savePostProcess.process(processConfig, models);
		}
		return models;
	}

	protected abstract List<M> getSaveModels(SqlModelService modelService);

	@Override
	public List<M> getSaveModels() {
		throw new UnsupportedOperationException();
	}

}

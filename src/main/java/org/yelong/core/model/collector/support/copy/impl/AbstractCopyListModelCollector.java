/**
 * 
 */
package org.yelong.core.model.collector.support.copy.impl;

import java.util.List;

import org.yelong.core.model.Modelable;
import org.yelong.core.model.collector.support.ProcessConfig;
import org.yelong.core.model.collector.support.copy.CopyListModelCollector;
import org.yelong.core.model.service.SqlModelService;

/**
 * 抽象默认实现的集合模型复制收集器
 * 
 * @author PengFei
 *
 * @param <M> model type
 * @since 1.3.0
 */
public abstract class AbstractCopyListModelCollector<M extends Modelable> extends AbstractCopyModelCollector<M, List<M>>
		implements CopyListModelCollector<M> {

	protected AbstractCopyListModelCollector(Class<M> modelClass) {
		super(modelClass);
	}

	@Override
	protected List<M> doCollect(SqlModelService modelService) {
		List<M> models = getCopySourceModels(modelService);
		ProcessConfig processConfig = new ProcessConfig(modelService, modelClass);
		if (null != copyPreProcess)
			copyPreProcess.process(processConfig, models);
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
		if (null != copyPostProcess) {
			copyPostProcess.process(processConfig, models);
		}
		return models;
	}

	/**
	 * 获取进行复制的模型集合
	 * 
	 * @param modelService model service
	 * @return 需要进行复制的模型集合
	 */
	protected abstract List<M> getCopySourceModels(SqlModelService modelService);

	@Override
	public List<M> getCopySourceModels() {
		throw new UnsupportedOperationException();
	}

}

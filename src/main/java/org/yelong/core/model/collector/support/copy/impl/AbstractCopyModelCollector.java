/**
 * 
 */
package org.yelong.core.model.collector.support.copy.impl;

import org.yelong.core.model.Modelable;
import org.yelong.core.model.collector.ModelCollectorImpl;
import org.yelong.core.model.collector.support.copy.CopyModelCollector;
import org.yelong.core.model.collector.support.copy.CopyPostProcess;
import org.yelong.core.model.collector.support.copy.CopyPreProcess;
import org.yelong.core.model.collector.support.save.AfterSaveModel;
import org.yelong.core.model.collector.support.save.BeforeSaveModel;
import org.yelong.core.model.collector.support.save.SaveModelExecutor;

/**
 * 抽象的复制模型收集器
 * 
 * @param <M> model type
 * @param <R> return type
 * @since 1.3
 */
public abstract class AbstractCopyModelCollector<M extends Modelable, R>
		extends ModelCollectorImpl<M, R, CopyModelCollector<M, R>> implements CopyModelCollector<M, R> {

	protected CopyPreProcess<M> copyPreProcess;

	protected BeforeSaveModel<M> beforeSaveModel;

	protected SaveModelExecutor<M> saveModelExecutor;

	protected AfterSaveModel<M> afterSaveModel;

	protected CopyPostProcess<M> copyPostProcess;

	protected AbstractCopyModelCollector(Class<M> modelClass) {
		super(modelClass);
	}

	@Override
	public CopyModelCollector<M, R> preProcess(CopyPreProcess<M> copyPreProcess) {
		this.copyPreProcess = copyPreProcess;
		return this;
	}

	@Override
	public CopyModelCollector<M, R> beforeSave(BeforeSaveModel<M> beforeSaveModel) {
		this.beforeSaveModel = beforeSaveModel;
		return this;
	}

	@Override
	public CopyModelCollector<M, R> saveModel(SaveModelExecutor<M> saveModelExecutor) {
		this.saveModelExecutor = saveModelExecutor;
		return this;
	}

	@Override
	public CopyModelCollector<M, R> afterSave(AfterSaveModel<M> afterSaveModel) {
		this.afterSaveModel = afterSaveModel;
		return this;
	}

	@Override
	public CopyModelCollector<M, R> postProcess(CopyPostProcess<M> copyPostProcess) {
		this.copyPostProcess = copyPostProcess;
		return this;
	}

}

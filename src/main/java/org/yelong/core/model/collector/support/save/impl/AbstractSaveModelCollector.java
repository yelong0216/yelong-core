/**
 * 
 */
package org.yelong.core.model.collector.support.save.impl;

import org.yelong.core.model.Modelable;
import org.yelong.core.model.collector.ModelCollectorImpl;
import org.yelong.core.model.collector.support.save.AfterSaveModel;
import org.yelong.core.model.collector.support.save.BeforeSaveModel;
import org.yelong.core.model.collector.support.save.SaveModelCollector;
import org.yelong.core.model.collector.support.save.SaveModelExecutor;
import org.yelong.core.model.collector.support.save.SavePostProcess;
import org.yelong.core.model.collector.support.save.SavePreProcess;

/**
 * 抽象的保存模型收集器
 * 
 * @author PengFei
 *
 * @param <M> model type
 * @param <R> return type
 * @since 1.3.0
 */
public abstract class AbstractSaveModelCollector<M extends Modelable, R>
		extends ModelCollectorImpl<M, R, SaveModelCollector<M, R>> implements SaveModelCollector<M, R> {

	protected SavePreProcess<M> savePreProcess;

	protected BeforeSaveModel<M> beforeSaveModel;

	protected SaveModelExecutor<M> saveModelExecutor;

	protected AfterSaveModel<M> afterSaveModel;

	protected SavePostProcess<M> savePostProcess;

	public AbstractSaveModelCollector(Class<M> modelClass) {
		super(modelClass);
	}

	@Override
	public SaveModelCollector<M, R> preProcess(SavePreProcess<M> savePreProcess) {
		this.savePreProcess = savePreProcess;
		return this;
	}

	@Override
	public SaveModelCollector<M, R> beforeSave(BeforeSaveModel<M> beforeSaveModel) {
		this.beforeSaveModel = beforeSaveModel;
		return this;
	}

	@Override
	public SaveModelCollector<M, R> saveModel(SaveModelExecutor<M> saveModelExecutor) {
		this.saveModelExecutor = saveModelExecutor;
		return this;
	}

	@Override
	public SaveModelCollector<M, R> afterSave(AfterSaveModel<M> afterSaveModel) {
		this.afterSaveModel = afterSaveModel;
		return this;
	}

	@Override
	public SaveModelCollector<M, R> postProcess(SavePostProcess<M> savePostProcess) {
		this.savePostProcess = savePostProcess;
		return this;
	}

}

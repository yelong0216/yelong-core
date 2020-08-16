/**
 * 
 */
package org.yelong.core.model.collector.support.copy;

import org.yelong.core.model.Modelable;
import org.yelong.core.model.collector.ModelCollector;
import org.yelong.core.model.collector.support.save.AfterSaveModel;
import org.yelong.core.model.collector.support.save.BeforeSaveModel;
import org.yelong.core.model.collector.support.save.SaveModelExecutor;

/**
 * 模型复制收集者
 * 
 * 可以在复制模型前后进行拦截操作
 * 
 * 执行顺序：preProcess(准备)->beforeSave(保存前)->saveModel(执行保存)->afterSave(保存后)->postProcess(后置处理)
 * 
 * @param <M> model type
 * @param <R> return type
 * @since 1.3
 */
public interface CopyModelCollector<M extends Modelable, R> extends ModelCollector<M, R, CopyModelCollector<M, R>> {

	/**
	 * 复制前的准备操作
	 * 
	 * @param copyPreProcess 前置处理
	 * @return this
	 */
	CopyModelCollector<M, R> preProcess(CopyPreProcess<M> copyPreProcess);

	/**
	 * 模型保存前的操作<br/>
	 * 每个模型在保存前都执行该操作，如果模型有多个则该操作会被执行多次
	 * 
	 * @param beforeSaveModel 保存前的操作
	 * @return this
	 */
	CopyModelCollector<M, R> beforeSave(BeforeSaveModel<M> beforeSaveModel);

	/**
	 * 执行保存模型
	 * 
	 * @param saveModelExecutor 保存模型执行器
	 * @return this
	 */
	CopyModelCollector<M, R> saveModel(SaveModelExecutor<M> saveModelExecutor);

	/**
	 * 保存模型后的操作<br/>
	 * 每个模型在保存后都执行该操作，如果模型有多个则该操作会被执行多次
	 * 
	 * @param afterSaveModel 保存后的操作
	 * @return this
	 */
	CopyModelCollector<M, R> afterSave(AfterSaveModel<M> afterSaveModel);

	/**
	 * 复制完后的后置处理
	 * 
	 * @param copyPostProcess 后置处理
	 * @return this
	 */
	CopyModelCollector<M, R> postProcess(CopyPostProcess<M> copyPostProcess);

}

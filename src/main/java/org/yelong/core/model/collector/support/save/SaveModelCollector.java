/**
 * 
 */
package org.yelong.core.model.collector.support.save;

import org.yelong.core.model.Modelable;
import org.yelong.core.model.collector.ModelCollector;

/**
 * 模型保存收集器
 * 
 * 执行顺序：preProcess(准备)->beforeSave(保存前)->saveModel(执行保存)->afterSave(保存后)->postProcess(后置处理)
 * 
 * @author PengFei
 *
 * @param <M> model type
 * @param <R> return type
 * @since 1.3.0
 */
public interface SaveModelCollector<M extends Modelable, R> extends ModelCollector<M, R, SaveModelCollector<M, R>> {

	/**
	 * 保存的前置处理
	 * 
	 * @param savePreProcess 前置处理
	 * @return this
	 */
	SaveModelCollector<M, R> preProcess(SavePreProcess<M> savePreProcess);

	/**
	 * 每个保存前的操作<br/>
	 * 保存的模型存在多个时，该方法会被调用多次
	 * 
	 * @param beforeSaveModel 保存前的操作
	 * @return this
	 */
	SaveModelCollector<M, R> beforeSave(BeforeSaveModel<M> beforeSaveModel);

	/**
	 * 执行保存
	 * 
	 * @param saveModelExecutor 保存模型执行器
	 * @return this
	 */
	SaveModelCollector<M, R> saveModel(SaveModelExecutor<M> saveModelExecutor);

	/**
	 * 每个保存模型后的操作<br/>
	 * 保存的模型存在多个时，该方法会被调用多次
	 * 
	 * @param afterSaveModel 保存模型后的操作
	 * @return this
	 */
	SaveModelCollector<M, R> afterSave(AfterSaveModel<M> afterSaveModel);

	/**
	 * 所有模型保存完后的处理
	 * 
	 * @param savePostProcess 后置处理
	 * @return this
	 */
	SaveModelCollector<M, R> postProcess(SavePostProcess<M> savePostProcess);

}

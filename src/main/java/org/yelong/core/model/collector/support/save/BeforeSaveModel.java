/**
 * 
 */
package org.yelong.core.model.collector.support.save;

import org.yelong.core.model.Modelable;
import org.yelong.core.model.collector.support.ProcessConfig;

/**
 * 保存之前执行的操作
 * 
 * @param <M> model type
 * @since 1.3
 */
@FunctionalInterface
public interface BeforeSaveModel<M extends Modelable> {

	/**
	 * 在保存之前执行某些操作
	 * 
	 * @param processConfig 配置
	 * @param model         保存的模型对象
	 */
	void process(ProcessConfig processConfig, M model);

}

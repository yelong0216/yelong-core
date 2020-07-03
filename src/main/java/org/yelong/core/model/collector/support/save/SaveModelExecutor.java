/**
 * 
 */
package org.yelong.core.model.collector.support.save;

import org.yelong.core.model.Modelable;
import org.yelong.core.model.collector.support.ProcessConfig;

/**
 * 模型保存执行器
 * 
 * @author PengFei
 *
 * @param <M> model type
 * @since 1.3.0
 */
@FunctionalInterface
public interface SaveModelExecutor<M extends Modelable> {

	/**
	 * 保存模型
	 * 
	 * @param modelService model service
	 * @param model        需要保存的model
	 * @return 保存后的model
	 */
	void save(ProcessConfig processConfig, M model);

}

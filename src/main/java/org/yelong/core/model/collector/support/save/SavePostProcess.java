/**
 * 
 */
package org.yelong.core.model.collector.support.save;

import java.util.List;

import org.yelong.core.model.Modelable;
import org.yelong.core.model.collector.support.ProcessConfig;

/**
 * 保存模型的后置处理
 * 
 * @param <M> model type
 * @since 1.3
 */
@FunctionalInterface
public interface SavePostProcess<M extends Modelable> {

	/**
	 * 后置处理操作
	 * 
	 * @param processConfig 配置
	 * @param models        保存后的模型集合
	 */
	void process(ProcessConfig processConfig, List<M> models);

}

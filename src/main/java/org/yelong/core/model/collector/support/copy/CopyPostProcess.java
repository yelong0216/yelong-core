/**
 * 
 */
package org.yelong.core.model.collector.support.copy;

import java.util.List;

import org.yelong.core.model.Modelable;
import org.yelong.core.model.collector.support.ProcessConfig;

/**
 * 复制的后置处理
 * 
 * @param <M> model type
 * @since 1.3
 */
@FunctionalInterface
public interface CopyPostProcess<M extends Modelable> {

	/**
	 * 后置处理过程
	 * 
	 * @param processConfig 处理配置
	 * @param models        复制后的模型集合
	 */
	void process(ProcessConfig processConfig, List<M> models);

}

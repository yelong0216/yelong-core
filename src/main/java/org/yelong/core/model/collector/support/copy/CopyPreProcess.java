/**
 * 
 */
package org.yelong.core.model.collector.support.copy;

import java.util.List;

import org.yelong.core.model.Modelable;
import org.yelong.core.model.collector.support.ProcessConfig;

/**
 * 复制的前置处理
 * 
 * @param <M> model type
 * @since 1.3
 */
@FunctionalInterface
public interface CopyPreProcess<M extends Modelable> {

	/**
	 * 复制前的处理
	 * 
	 * @param processConfig 处理配置
	 * @param models        需要复制的模型集合
	 */
	void process(ProcessConfig processConfig, List<M> models);

}

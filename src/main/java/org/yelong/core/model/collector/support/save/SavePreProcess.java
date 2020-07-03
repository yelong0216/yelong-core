/**
 * 
 */
package org.yelong.core.model.collector.support.save;

import java.util.List;

import org.yelong.core.model.Modelable;
import org.yelong.core.model.collector.support.ProcessConfig;

/**
 * 保存模型的前置处理
 * 
 * @author PengFei
 *
 * @param <M> model type
 * @since 1.3.0
 */
@FunctionalInterface
public interface SavePreProcess<M extends Modelable> {

	/**
	 * 前置处理
	 * 
	 * @param processConfig 配置
	 * @param models        保存前的模型
	 */
	void process(ProcessConfig processConfig, List<M> models);

}

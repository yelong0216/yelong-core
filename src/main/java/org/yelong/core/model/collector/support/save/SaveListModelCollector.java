/**
 * 
 */
package org.yelong.core.model.collector.support.save;

import java.util.List;

import org.yelong.core.model.Modelable;

/**
 * 保存模型集合收集器
 * 
 * @param <M> model type
 * @since 1.3
 */
public interface SaveListModelCollector<M extends Modelable> extends SaveModelCollector<M, List<M>> {

	/**
	 * @return 所有保存的模型集合
	 */
	List<M> getSaveModels();

}

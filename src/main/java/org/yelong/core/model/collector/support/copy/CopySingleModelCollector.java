/**
 * 
 */
package org.yelong.core.model.collector.support.copy;

import org.yelong.core.model.Modelable;

/**
 * 复制单个模型收集器
 * 
 * @param <M> model type
 * @since 1.3
 */
public interface CopySingleModelCollector<M extends Modelable> extends CopyModelCollector<M, M> {

	/**
	 * @return 复制的源模型
	 */
	M getCopySourceModel();

}

/**
 * 
 */
package org.yelong.core.model.collector.support.copy;

import org.yelong.core.model.Modelable;

/**
 * 复制单个模型收集器
 * 
 * @author PengFei
 *
 * @param <M> model type
 * @since 1.3.0
 */
public interface CopySingleModelCollector<M extends Modelable> extends CopyModelCollector<M, M> {

	/**
	 * @return 复制的源模型
	 */
	M getCopySourceModel();

}

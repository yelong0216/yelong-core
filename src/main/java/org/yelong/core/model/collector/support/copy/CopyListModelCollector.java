/**
 * 
 */
package org.yelong.core.model.collector.support.copy;

import java.util.List;

import org.yelong.core.model.Modelable;

/**
 * 模型复制集合收集者
 * 
 * @author PengFei
 *
 * @param <M> model type
 * @since 1.3.0
 */
public interface CopyListModelCollector<M extends Modelable> extends CopyModelCollector<M, List<M>> {

	/**
	 * @return 所有进行复制的模型集合
	 */
	List<M> getCopySourceModels();

}

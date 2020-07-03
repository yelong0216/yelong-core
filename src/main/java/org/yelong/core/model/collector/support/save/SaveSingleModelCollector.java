package org.yelong.core.model.collector.support.save;

import org.yelong.core.model.Modelable;

/**
 * 保存单个模型的收集器
 * 
 * @author PengFei
 *
 * @param <M> model type
 * @since 1.3.0
 */
public interface SaveSingleModelCollector<M extends Modelable> extends SaveModelCollector<M, M> {

	/**
	 * @return 进行保存的模型
	 */
	M getSaveModel();

}

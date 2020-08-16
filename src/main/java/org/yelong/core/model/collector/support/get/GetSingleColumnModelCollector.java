/**
 * 
 */
package org.yelong.core.model.collector.support.get;

import org.yelong.core.model.Modelable;
import org.yelong.core.model.collector.ModelCollector;

/**
 * 获取单列值模型收集器
 * 
 * @param <M> model type
 * @param <T> return type
 * @since 1.3
 */
public interface GetSingleColumnModelCollector<M extends Modelable, T>
		extends ModelCollector<M, T, GetSingleColumnModelCollector<M, T>> {

	/**
	 * @return 查询的列名
	 */
	String getSelectColumn();

}

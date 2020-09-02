package org.yelong.core.model.collector.support.find;

import java.util.List;

import org.yelong.core.model.Modelable;
import org.yelong.core.model.collector.ModelCollector;

/**
 * 查询单列值
 *
 * @param <M> model type
 * @param <T> return list object type
 * @since 2.1
 */
public interface FindSingleColumnModelCollector<M extends Modelable, T>
		extends ModelCollector<M, List<T>, FindSingleColumnModelCollector<M, T>> {

	/**
	 * @return 查询的列名
	 */
	String getSelectColumn();

}

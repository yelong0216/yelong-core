/**
 * 
 */
package org.yelong.core.model.collector.support.get.impl;

import org.yelong.commons.lang.Strings;
import org.yelong.core.model.Modelable;
import org.yelong.core.model.collector.ModelCollectorImpl;
import org.yelong.core.model.collector.support.get.GetSingleColumnModelCollector;

/**
 * 抽象的获取单列模型收集器
 * 
 * @author PengFei
 *
 * @param <M> model type
 * @param <T> return type
 * @since 1.3.0
 */
public abstract class AbstractGetSingleColumnModelCollector<M extends Modelable, T> extends
		ModelCollectorImpl<M, T, GetSingleColumnModelCollector<M, T>> implements GetSingleColumnModelCollector<M, T> {

	protected final String selectColumn;

	public AbstractGetSingleColumnModelCollector(Class<M> modelClass, String selectColumn) {
		super(modelClass);
		this.selectColumn = Strings.requireNonBlank(selectColumn);
	}

	@Override
	public String getSelectColumn() {
		return selectColumn;
	}

}

/**
 * 
 */
package org.yelong.core.model.collector.support.find.impl;

import java.util.List;

import org.yelong.commons.lang.Strings;
import org.yelong.core.model.Modelable;
import org.yelong.core.model.collector.ModelCollectorImpl;
import org.yelong.core.model.collector.support.find.FindSingleColumnModelCollector;

/**
 * 抽象的查询单列收集器
 * 
 * @since 2.1
 */
public abstract class AbstractFindSingleColumnModelCollector<M extends Modelable, T>
		extends ModelCollectorImpl<M, List<T>, FindSingleColumnModelCollector<M, T>>
		implements FindSingleColumnModelCollector<M, T> {

	protected final String selectColumn;

	public AbstractFindSingleColumnModelCollector(Class<M> modelClass, String selectColumn) {
		super(modelClass);
		this.selectColumn = Strings.requireNonBlank(selectColumn);
	}

	@Override
	public String getSelectColumn() {
		return selectColumn;
	}

}

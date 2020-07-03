/**
 * 
 */
package org.yelong.core.jdbc.sql.sort;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.yelong.core.jdbc.dialect.Dialect;
import org.yelong.core.jdbc.sql.AbstractSqlFragment;
import org.yelong.core.jdbc.sql.exception.InvalidSortException;
import org.yelong.core.jdbc.sql.sort.support.Sort;

/**
 * 抽象的排序sql实现
 * 
 * @author PengFei
 */
public abstract class AbstractSortSqlFragment extends AbstractSqlFragment implements SortSqlFragment {

	private boolean orderBy = true;

	private final List<Sort> sorts = new ArrayList<>();

	public AbstractSortSqlFragment(Dialect dialect) {
		super(dialect);
	}

	@Override
	public void addSort(String column, String direction) {
		addSort(new Sort(column, direction));
	}

	@Override
	public void addSort(Sort sort) throws InvalidSortException {
		validSort(sort);
		sorts.add(sort);
	}

	/**
	 * 验证排序是否符合规范
	 * 
	 * @param sort 排序
	 * @throws InvalidSortException 如果这不是一个符合规范的异常
	 */
	protected abstract void validSort(Sort sort) throws InvalidSortException;

	protected abstract String generatorSortFragment(List<Sort> sorts);

	@Override
	public boolean isOrderBy() {
		return orderBy;
	}

	@Override
	public void setOrderBy(boolean orderBy) {
		this.orderBy = orderBy;
	}

	protected String orderBy(String sort) {
		if (orderBy) {
			return " order by " + sort;
		} else {
			return sort;
		}
	}

	@Override
	public Object[] getParams() {
		return ArrayUtils.EMPTY_OBJECT_ARRAY;
	}

	@Override
	public String getSqlFragment() {
		if (isEmpty()) {
			throw new UnsupportedOperationException("不存在排序!");
		}
		return orderBy(generatorSortFragment(sorts));
	}

	@Override
	public boolean isEmpty() {
		return sorts.isEmpty();
	}

	@Override
	public String toString() {
		return getSqlFragment();
	}

}

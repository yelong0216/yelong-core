/**
 * 
 */
package org.yelong.core.jdbc.sql.sort;

import org.yelong.core.jdbc.sql.SqlFragment;
import org.yelong.core.jdbc.sql.exception.InvalidSortException;
import org.yelong.core.jdbc.sql.sort.support.Sort;

/**
 * 排序sql
 * 
 * @author PengFei
 */
public interface SortSqlFragment extends SqlFragment {

	/**
	 * 添加一个排序。
	 * 
	 * @param column    字段名称
	 * @param direction 排序方向：DESC(降序)、ASC（升序）
	 * @throws InvalidSortException 如果这个排序是无效的
	 */
	void addSort(String column, String direction) throws InvalidSortException;

	/**
	 * 添加一个排序。
	 * 
	 * @param sort 排序
	 * @throws InvalidSortException 如果这个排序是无效的
	 */
	void addSort(Sort sort) throws InvalidSortException;

	/**
	 * 是否存在排序
	 * 
	 * @return <tt>true</tt> 如果不存在排序
	 */
	boolean isEmpty();

	/**
	 * 是否拼接order by 关键字
	 * 
	 * @return true 拼接 false 不拼接
	 */
	boolean isOrderBy();

	/**
	 * 设置是否拼接order by 关键字
	 * 
	 * @param orderBy true 拼接 false 不拼接
	 */
	void setOrderBy(boolean orderBy);

}

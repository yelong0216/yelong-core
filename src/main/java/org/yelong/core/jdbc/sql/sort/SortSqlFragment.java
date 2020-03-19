/**
 * 
 */
package org.yelong.core.jdbc.sql.sort;

import org.yelong.core.jdbc.sql.SqlFragment;
import org.yelong.core.jdbc.sql.exception.InvalidSortException;

/**
 * 动态排序<br/>
 * @author 彭飞
 * @date 2019年8月7日下午2:52:42
 * @version 1.0
 */
public interface SortSqlFragment extends SqlFragment{

	/**
	 * 添加一个排序条件。<br/>
	 * @author 彭飞
	 * @date 2019年7月26日下午2:03:31
	 * @param fieldName 字段名称
	 * @param direction 排序方向：DESC(降序)、ASC（升序）
	 * @throws InvalidSortException 如果这个排序是无效的
	 */
	void addSort(String fieldName,String direction) throws InvalidSortException;
	
	/**
	 * 是否存在排序
	 * @author 彭飞
	 * @date 2019年7月26日下午2:10:55
	 * @return <tt>true</tt> 如果不存在排序
	 */
	boolean isEmpty();
	
	/**
	 * 是否拼接order by 关键字
	 * @return true 拼接 false 不拼接
	 */
	boolean isOrderBy();
	
	/**
	 * 设置是否拼接order by 关键字
	 * @param orderBy true 拼接 false 不拼接
	 */
	void setOrderBy(boolean orderBy);
	
}

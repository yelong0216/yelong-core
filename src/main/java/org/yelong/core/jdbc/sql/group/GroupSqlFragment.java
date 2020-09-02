/**
 * 
 */
package org.yelong.core.jdbc.sql.group;

import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.yelong.core.jdbc.sql.SqlFragment;

/**
 * 分组片段(Group By)
 * 
 * @since 2.1
 */
public interface GroupSqlFragment extends SqlFragment {

	/**
	 * 添加分组列
	 * 
	 * @param columns 需要进行分组的列数组
	 */
	void addGroup(String... columns);

	/**
	 * 添加分组列
	 * 
	 * @param columns 需要进行分组的列集合
	 */
	default void addGroup(List<String> columns) {
		addGroup(columns.toArray(new String[columns.size()]));
	}

	/**
	 * 是否存在分组的列
	 * 
	 * @return <tt>true</tt> 如果不存在分组的列
	 */
	boolean isEmpty();

	/**
	 * 是否拼接order by 关键字
	 * 
	 * @return true 拼接 false 不拼接
	 */
	boolean isGroupBy();

	/**
	 * 设置是否拼接group by 关键字
	 * 
	 * @param groupBy true 拼接 false 不拼接
	 */
	void setGroupBy(boolean groupBy);

	@Override
	default Object[] getParams() {
		return ArrayUtils.EMPTY_OBJECT_ARRAY;
	}

}

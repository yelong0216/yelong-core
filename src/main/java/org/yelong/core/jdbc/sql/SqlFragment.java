/**
 * 
 */
package org.yelong.core.jdbc.sql;

import org.yelong.core.annotation.Nullable;

/**
 * sql 片段
 * 这个sql片段可能是一段sql( userName = ? )，也可能是一段可执行的sql。
 * @author 彭飞
 * @date 2019年10月24日下午1:58:00
 * @version 1.2
 */
public interface SqlFragment {
	
	/**
	 * 这不会保证与参数的完全对应。
	 * 需要保证统一，请使用{@link #getBoundSql()}。
	 * 这个sql片段占位符为? 。
	 * @author 彭飞
	 * @date 2019年10月24日下午1:58:23
	 * @version 1.2
	 * @return sql片段
	 */
	String getSqlFragment();
	
	/**
	 * 获取参数。
	 * 这不会保证与sql片段的完全对应。
	 * 需要保证统一，请使用{@link #getBoundSql()}
	 * @author 彭飞
	 * @date 2019年10月24日下午1:58:42
	 * @version 1.2
	 * @return sql对应的参数数组
	 */
	@Nullable
	Object [] getParams();
	
	/**
	 * @author 彭飞
	 * @date 2019年10月24日下午1:59:25
	 * @version 1.2
	 * @return 绑定的sql
	 */
	default BoundSql getBoundSql() {
		return new BoundSql(getSqlFragment(), getParams());
	}
	
}

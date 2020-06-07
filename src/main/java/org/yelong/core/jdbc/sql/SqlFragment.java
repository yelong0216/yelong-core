/**
 * 
 */
package org.yelong.core.jdbc.sql;

import org.yelong.core.annotation.Nullable;

/**
 * sql 片段
 * 这个sql片段可能是一段sql( userName = ? )，也可能是一段可执行的sql。
 * 
 * @author PengFei
 */
public interface SqlFragment {
	
	/**
	 * 这不会保证与参数的完全对应。
	 * 需要保证统一，请使用{@link #getBoundSql()}。
	 * 这个sql片段占位符为? 。
	 * 
	 * @return sql片段
	 */
	String getSqlFragment();
	
	/**
	 * 获取参数。
	 * 这不会保证与sql片段的完全对应。
	 * 需要保证统一，请使用{@link #getBoundSql()}
	 * 
	 * @return sql对应的参数数组
	 */
	@Nullable
	Object [] getParams();
	
	/**
	 * @return 绑定的sql
	 */
	default BoundSql getBoundSql() {
		return new BoundSql(getSqlFragment(), getParams());
	}
	
}

/**
 * 
 */
package org.yelong.core.jdbc.sql;

import java.util.Arrays;

import org.yelong.core.annotation.Nullable;

/**
 * 绑定的sql 执行的sql语句及sql所需要的参数
 * 
 * @author 彭飞
 * @date 2019年8月19日上午11:16:29
 * @version 1.2
 */
public class BoundSql {

	private final String sql;

	@Nullable
	private final Object[] params;

	public BoundSql(String sql, @Nullable Object[] params) {
		this.sql = sql;
		this.params = params;
	}

	public String getSql() {
		return sql;
	}

	public Object[] getParams() {
		return params;
	}

	@Override
	public String toString() {
		StringBuilder print = new StringBuilder();
		print.append("-------sql : " + sql);
		print.append("\n");
		print.append("-------param : " + Arrays.asList(params));
		return print.toString();
	}

}

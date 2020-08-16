/**
 * 
 */
package org.yelong.core.jdbc.sql;

import java.util.Arrays;
import java.util.Objects;

import org.apache.commons.lang3.ArrayUtils;
import org.yelong.core.annotation.Nullable;

/**
 * 绑定的sql 执行的sql语句及sql所需要的参数
 */
public class BoundSql {

	private final String sql;

	@Nullable
	private final Object[] params;

	public BoundSql(String sql, @Nullable Object[] params) {
		this.sql = Objects.requireNonNull(sql);
		this.params = params != null ? params : ArrayUtils.EMPTY_OBJECT_ARRAY;
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

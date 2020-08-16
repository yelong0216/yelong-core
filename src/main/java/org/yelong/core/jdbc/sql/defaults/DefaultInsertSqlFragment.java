/**
 * 
 */
package org.yelong.core.jdbc.sql.defaults;

import java.util.Objects;

import org.yelong.core.annotation.Nullable;
import org.yelong.core.jdbc.dialect.Dialect;
import org.yelong.core.jdbc.sql.BoundSql;
import org.yelong.core.jdbc.sql.attribute.AttributeSqlFragment;
import org.yelong.core.jdbc.sql.executable.AbstractSqlFragmentExecutable;
import org.yelong.core.jdbc.sql.executable.InsertSqlFragment;

/**
 * 默认的insert sql实现
 */
public class DefaultInsertSqlFragment extends AbstractSqlFragmentExecutable implements InsertSqlFragment {

	@Nullable
	private AttributeSqlFragment attributeSqlFragment;

	@Nullable
	private BoundSql attributeBoundSql;

	private String tableName;

	public DefaultInsertSqlFragment(Dialect dialect, String tableName, AttributeSqlFragment attributeSqlFragment) {
		super(dialect);
		Objects.requireNonNull(tableName, "未发现表名称！");
		Objects.requireNonNull(attributeSqlFragment, "未发现列！");
		this.tableName = tableName;
		this.attributeSqlFragment = attributeSqlFragment;
		this.attributeBoundSql = attributeSqlFragment.getBoundSql();
	}

	public DefaultInsertSqlFragment(Dialect dialect, String sql, Object... params) {
		super(dialect, sql, params);
	}

	@Override
	public String getSqlFragment() {
		if (existBaseSql()) {
			return super.getBaseSql();
		}
		return "insert into " + tableName + " " + attributeBoundSql.getSql();
	}

	@Override
	public Object[] getParams() {
		if (existBaseSql()) {
			return super.getBaseParams();
		}
		return attributeBoundSql.getParams();
	}

	@Override
	public AttributeSqlFragment getAttributeSqlFragment() {
		return this.attributeSqlFragment;
	}

}

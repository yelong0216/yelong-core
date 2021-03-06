/**
 * 
 */
package org.yelong.core.jdbc.sql.defaults;

import org.apache.commons.lang3.ArrayUtils;
import org.yelong.core.annotation.Nullable;
import org.yelong.core.jdbc.dialect.Dialect;
import org.yelong.core.jdbc.sql.BoundSql;
import org.yelong.core.jdbc.sql.condition.ConditionSqlFragment;
import org.yelong.core.jdbc.sql.executable.AbstractSqlFragmentExecutable;
import org.yelong.core.jdbc.sql.executable.CountSqlFragment;

/**
 * 默认查询记录数sql片段实现
 */
public class DefaultCountSqlFragment extends AbstractSqlFragmentExecutable implements CountSqlFragment {

	@Nullable
	private ConditionSqlFragment conditionFragment;

	@Nullable
	private BoundSql conditionBoundSql;

	public DefaultCountSqlFragment(Dialect dialect, String sql, Object... params) {
		super(dialect, sql, params);
	}

	@Override
	public String getSqlFragment() {
		if (!existConditionSqlFragment()) {
			return getBaseSql();
		}
		return getBaseSql() + " " + conditionBoundSql.getSql();
	}

	@Override
	public Object[] getParams() {
		if (!existConditionSqlFragment()) {
			return getBaseParams();
		}
		return ArrayUtils.addAll(getBaseParams(), conditionBoundSql.getParams());
	}

	@Override
	public CountSqlFragment setConditionSqlFragment(ConditionSqlFragment conditionFragment) {
		this.conditionFragment = conditionFragment;
		this.conditionBoundSql = conditionFragment.getBoundSql();
		return this;
	}

	@Override
	public ConditionSqlFragment getConditionSqlFragment() {
		return this.conditionFragment;
	}

}

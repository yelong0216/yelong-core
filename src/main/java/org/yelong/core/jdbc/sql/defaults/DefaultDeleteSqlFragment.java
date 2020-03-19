/**
 * 
 */
package org.yelong.core.jdbc.sql.defaults;

import org.apache.commons.lang3.ArrayUtils;
import org.yelong.core.annotation.Nullable;
import org.yelong.core.jdbc.sql.BoundSql;
import org.yelong.core.jdbc.sql.condition.ConditionSqlFragment;
import org.yelong.core.jdbc.sql.executable.AbstractSqlFragmentExecutable;
import org.yelong.core.jdbc.sql.executable.DeleteSqlFragment;

/**
 * 默认的delete sql片段实现
 * @author PengFei
 */
public class DefaultDeleteSqlFragment extends AbstractSqlFragmentExecutable implements DeleteSqlFragment{

	@Nullable 
	private ConditionSqlFragment conditionSqlFragment;

	@Nullable 
	private BoundSql conditionBoundSql;

	public DefaultDeleteSqlFragment(String sql, Object ... params) {
		super(sql, params);
	}

	@Override
	public String getSqlFragment() {
		if(!existConditionSqlFragment()) {
			return getBaseSql();
		}
		return getBaseSql() + " " + conditionBoundSql.getSql();
	}

	@Override
	public Object[] getParams() {
		if(!existConditionSqlFragment()) {
			return getBaseParams();
		}
		return ArrayUtils.addAll(getBaseParams(), conditionBoundSql.getParams());
	}

	@Override
	public DeleteSqlFragment setConditionSqlFragment(ConditionSqlFragment conditionSqlFragment) {
		this.conditionSqlFragment = conditionSqlFragment;
		this.conditionBoundSql = conditionSqlFragment.getBoundSql();
		return this;
	}

	@Override
	public ConditionSqlFragment getConditionSqlFragment() {
		return this.conditionSqlFragment;
	}

}

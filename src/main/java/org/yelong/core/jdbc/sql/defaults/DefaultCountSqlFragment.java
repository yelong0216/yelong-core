/**
 * 
 */
package org.yelong.core.jdbc.sql.defaults;

import org.apache.commons.lang3.ArrayUtils;
import org.yelong.core.annotation.Nullable;
import org.yelong.core.jdbc.sql.BoundSql;
import org.yelong.core.jdbc.sql.condition.ConditionSqlFragment;
import org.yelong.core.jdbc.sql.executable.AbstractSqlFragmentExecutable;
import org.yelong.core.jdbc.sql.executable.CountSqlFragment;

/**
 * jdbc 查询记录数sql片段
 * 
 * @author 彭飞
 * @date 2019年11月1日上午8:59:52
 * @version 1.2
 */
public class DefaultCountSqlFragment extends AbstractSqlFragmentExecutable implements CountSqlFragment{

	@Nullable 
	private ConditionSqlFragment conditionFragment;

	@Nullable
	private BoundSql conditionBoundSql;

	public DefaultCountSqlFragment(String sql, Object ... params) {
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

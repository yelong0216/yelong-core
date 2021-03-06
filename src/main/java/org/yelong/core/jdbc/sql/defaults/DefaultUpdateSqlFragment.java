/**
 * 
 */
package org.yelong.core.jdbc.sql.defaults;

import java.util.Objects;

import org.apache.commons.lang3.ArrayUtils;
import org.yelong.core.jdbc.dialect.Dialect;
import org.yelong.core.jdbc.sql.BoundSql;
import org.yelong.core.jdbc.sql.attribute.AttributeSqlFragment;
import org.yelong.core.jdbc.sql.condition.ConditionSqlFragment;
import org.yelong.core.jdbc.sql.executable.AbstractSqlFragmentExecutable;
import org.yelong.core.jdbc.sql.executable.UpdateSqlFragment;

/**
 * 默认的 update sql 实现
 */
public class DefaultUpdateSqlFragment extends AbstractSqlFragmentExecutable implements UpdateSqlFragment {

	private AttributeSqlFragment attributeSqlFragment;

	private ConditionSqlFragment conditionSqlFragment;

	private BoundSql conditionBoundSql;

	private String tableName;

	public DefaultUpdateSqlFragment(Dialect dialect, String tableName, AttributeSqlFragment attributeSqlFragment) {
		super(dialect);
		Objects.requireNonNull(tableName, "未发现表名称！");
		Objects.requireNonNull(attributeSqlFragment, "未发现列！");
		this.tableName = tableName;
		this.attributeSqlFragment = attributeSqlFragment;
	}

	public DefaultUpdateSqlFragment(Dialect dialect, String sql, Object... params) {
		super(dialect, sql, params);
	}

	@Override
	public String getSqlFragment() {
		String sql = null;
		if (existBaseSql()) {
			sql = getBaseSql();
		} else {
			sql = " update " + tableName + " set " + getAttributeBoundSql().getSql();
		}
		if (existConditionSqlFragment()) {
			sql = sql + " " + conditionBoundSql.getSql();
		}
		return sql;
	}

	@Override
	public Object[] getParams() {
		Object[] attrParams = null;
		if (existBaseSql()) {
			attrParams = super.getBaseParams();
		} else {
			attrParams = getAttributeBoundSql().getParams();
		}
		if (!existConditionSqlFragment()) {
			return attrParams;
		} else {
			return ArrayUtils.addAll(attrParams, conditionBoundSql.getParams());
		}
	}

	@Override
	public AttributeSqlFragment getAttributeSqlFragment() {
		return this.attributeSqlFragment;
	}
	
	public BoundSql getAttributeBoundSql() {
		return getAttributeSqlFragment().getBoundSql();
	}

	@Override
	public UpdateSqlFragment setConditionSqlFragment(ConditionSqlFragment conditionSqlFragment) {
		this.conditionSqlFragment = conditionSqlFragment;
		this.conditionBoundSql = conditionSqlFragment.getBoundSql();
		return this;
	}

	@Override
	public ConditionSqlFragment getConditionSqlFragment() {
		return this.conditionSqlFragment;
	}

}

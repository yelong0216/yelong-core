/**
 * 
 */
package org.yelong.core.jdbc.sql.factory.support;

import org.yelong.core.jdbc.dialect.Dialect;
import org.yelong.core.jdbc.sql.attribute.AttributeSqlFragment;
import org.yelong.core.jdbc.sql.condition.combination.CombinationConditionSqlFragment;
import org.yelong.core.jdbc.sql.condition.simple.SimpleConditionSqlFragment;
import org.yelong.core.jdbc.sql.condition.simple.SimpleConditionSqlFragmentFactory;
import org.yelong.core.jdbc.sql.condition.single.SingleConditionSqlFragmentFactory;
import org.yelong.core.jdbc.sql.executable.CountSqlFragment;
import org.yelong.core.jdbc.sql.executable.DeleteSqlFragment;
import org.yelong.core.jdbc.sql.executable.InsertSqlFragment;
import org.yelong.core.jdbc.sql.executable.SelectSqlFragment;
import org.yelong.core.jdbc.sql.executable.UpdateSqlFragment;
import org.yelong.core.jdbc.sql.factory.SqlFragmentFactory;
import org.yelong.core.jdbc.sql.group.GroupSqlFragment;
import org.yelong.core.jdbc.sql.sort.SortSqlFragment;

public abstract class SqlFragmentFactoryWrapper implements SqlFragmentFactory {

	private final SqlFragmentFactory sqlFragmentFactory;

	public SqlFragmentFactoryWrapper(SqlFragmentFactory sqlFragmentFactory) {
		this.sqlFragmentFactory = sqlFragmentFactory;
	}

	@Override
	public AttributeSqlFragment createAttributeSqlFragment() {
		return sqlFragmentFactory.createAttributeSqlFragment();
	}

	@Override
	public InsertSqlFragment createInsertSqlFragment(String tableName, AttributeSqlFragment attributeSqlFragment) {
		return sqlFragmentFactory.createInsertSqlFragment(tableName, attributeSqlFragment);
	}

	@Override
	public InsertSqlFragment createInsertSqlFragment(String sql, Object... params) {
		return sqlFragmentFactory.createInsertSqlFragment(sql, params);
	}

	@Override
	public DeleteSqlFragment createDeleteSqlFragment(String sql, Object... params) {
		return sqlFragmentFactory.createDeleteSqlFragment(sql, params);
	}

	@Override
	public UpdateSqlFragment createUpdateSqlFragment(String sql, Object... params) {
		return sqlFragmentFactory.createUpdateSqlFragment(sql, params);
	}

	@Override
	public UpdateSqlFragment createUpdateSqlFragment(String tableName, AttributeSqlFragment attributeSqlFragment) {
		return sqlFragmentFactory.createUpdateSqlFragment(tableName, attributeSqlFragment);
	}

	@Override
	public SelectSqlFragment createSelectSqlFragment(String sql, Object... params) {
		return sqlFragmentFactory.createSelectSqlFragment(sql, params);
	}

	@Override
	public CountSqlFragment createCountSqlFragment(String sql, Object... params) {
		return sqlFragmentFactory.createCountSqlFragment(sql, params);
	}

	@Override
	public SimpleConditionSqlFragment createConditionSqlFragment(String conditionSqlFragment, Object... params) {
		return sqlFragmentFactory.createConditionSqlFragment(conditionSqlFragment, params);
	}

	@Override
	public CombinationConditionSqlFragment createCombinationConditionSqlFragment() {
		return sqlFragmentFactory.createCombinationConditionSqlFragment();
	}

	@Override
	public SortSqlFragment createSortSqlFragment() {
		return sqlFragmentFactory.createSortSqlFragment();
	}

	@Override
	public SingleConditionSqlFragmentFactory getSingleConditionSqlFragmentFactory() {
		return sqlFragmentFactory.getSingleConditionSqlFragmentFactory();
	}

	@Override
	public SimpleConditionSqlFragmentFactory getSimpleConditionSqlFragmentFactory() {
		return sqlFragmentFactory.getSimpleConditionSqlFragmentFactory();
	}

	@Override
	public GroupSqlFragment createGroupSqlFragment() {
		return sqlFragmentFactory.createGroupSqlFragment();
	}

	@Override
	public Dialect getDialect() {
		return sqlFragmentFactory.getDialect();
	}

}

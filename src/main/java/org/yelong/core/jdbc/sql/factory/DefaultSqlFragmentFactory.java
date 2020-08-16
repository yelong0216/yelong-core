/**
 * 
 */
package org.yelong.core.jdbc.sql.factory;

import org.yelong.core.jdbc.dialect.Dialect;
import org.yelong.core.jdbc.sql.attribute.AttributeSqlFragment;
import org.yelong.core.jdbc.sql.condition.combination.CombinationConditionSqlFragment;
import org.yelong.core.jdbc.sql.condition.simple.SimpleConditionSqlFragment;
import org.yelong.core.jdbc.sql.condition.simple.SimpleConditionSqlFragmentFactory;
import org.yelong.core.jdbc.sql.condition.single.SingleConditionSqlFragmentFactory;
import org.yelong.core.jdbc.sql.defaults.DefaultAttributeSqlFragment;
import org.yelong.core.jdbc.sql.defaults.DefaultCombinationConditionSqlFragment;
import org.yelong.core.jdbc.sql.defaults.DefaultCountSqlFragment;
import org.yelong.core.jdbc.sql.defaults.DefaultDeleteSqlFragment;
import org.yelong.core.jdbc.sql.defaults.DefaultInsertSqlFragment;
import org.yelong.core.jdbc.sql.defaults.DefaultSelectSqlFragment;
import org.yelong.core.jdbc.sql.defaults.DefaultSimpleConditionSqlFragment;
import org.yelong.core.jdbc.sql.defaults.DefaultSortSqlFragment;
import org.yelong.core.jdbc.sql.defaults.DefaultUpdateSqlFragment;
import org.yelong.core.jdbc.sql.executable.CountSqlFragment;
import org.yelong.core.jdbc.sql.executable.DeleteSqlFragment;
import org.yelong.core.jdbc.sql.executable.InsertSqlFragment;
import org.yelong.core.jdbc.sql.executable.SelectSqlFragment;
import org.yelong.core.jdbc.sql.executable.UpdateSqlFragment;
import org.yelong.core.jdbc.sql.sort.SortSqlFragment;

/**
 * 默认的sql片段工厂实现
 */
public class DefaultSqlFragmentFactory implements SqlFragmentFactory {

	private final Dialect dialect;

	private final SingleConditionSqlFragmentFactory singleConditionSqlFragmentFactory;

	private final SimpleConditionSqlFragmentFactory simpleConditionSqlFragmentFactory;

	public DefaultSqlFragmentFactory(Dialect dialect,
			SingleConditionSqlFragmentFactory singleConditionSqlFragmentFactory,
			SimpleConditionSqlFragmentFactory simpleConditionSqlFragmentFactory) {
		this.dialect = dialect;
		this.singleConditionSqlFragmentFactory = singleConditionSqlFragmentFactory;
		this.simpleConditionSqlFragmentFactory = simpleConditionSqlFragmentFactory;
	}

	@Override
	public AttributeSqlFragment createAttributeSqlFragment() {
		return new DefaultAttributeSqlFragment(dialect);
	}

	@Override
	public SimpleConditionSqlFragment createConditionSqlFragment(String conditionFragment, Object... params) {
		return new DefaultSimpleConditionSqlFragment(dialect, conditionFragment, params);
	}

	@Override
	public CombinationConditionSqlFragment createCombinationConditionSqlFragment() {
		return new DefaultCombinationConditionSqlFragment(singleConditionSqlFragmentFactory);
	}

	@Override
	public SortSqlFragment createSortSqlFragment() {
		return new DefaultSortSqlFragment(dialect);
	}

	@Override
	public InsertSqlFragment createInsertSqlFragment(String tableName, AttributeSqlFragment attributeSqlFragment) {
		return new DefaultInsertSqlFragment(dialect, tableName, attributeSqlFragment);
	}

	@Override
	public InsertSqlFragment createInsertSqlFragment(String sql, Object... params) {
		return new DefaultInsertSqlFragment(dialect, sql, params);
	}

	@Override
	public DeleteSqlFragment createDeleteSqlFragment(String sql, Object... params) {
		return new DefaultDeleteSqlFragment(dialect, sql, params);
	}

	@Override
	public UpdateSqlFragment createUpdateSqlFragment(String sql, Object... params) {
		return new DefaultUpdateSqlFragment(dialect, sql, params);
	}

	@Override
	public UpdateSqlFragment createUpdateSqlFragment(String tableName, AttributeSqlFragment attributeSqlFragment) {
		return new DefaultUpdateSqlFragment(dialect, tableName, attributeSqlFragment);
	}

	@Override
	public SelectSqlFragment createSelectSqlFragment(String sql, Object... params) {
		return new DefaultSelectSqlFragment(dialect, sql, params);
	}

	@Override
	public CountSqlFragment createCountSqlFragment(String sql, Object... params) {
		return new DefaultCountSqlFragment(dialect, sql, params);
	}

	@Override
	public SingleConditionSqlFragmentFactory getSingleConditionSqlFragmentFactory() {
		return singleConditionSqlFragmentFactory;
	}

	@Override
	public SimpleConditionSqlFragmentFactory getSimpleConditionSqlFragmentFactory() {
		return simpleConditionSqlFragmentFactory;
	}

	public Dialect getDialect() {
		return dialect;
	}

}
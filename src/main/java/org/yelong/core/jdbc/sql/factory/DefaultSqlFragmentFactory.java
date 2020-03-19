/**
 * 
 */
package org.yelong.core.jdbc.sql.factory;

import org.yelong.core.jdbc.dialect.Dialect;
import org.yelong.core.jdbc.sql.attribute.AttributeSqlFragment;
import org.yelong.core.jdbc.sql.condition.combination.CombinationConditionSqlFragment;
import org.yelong.core.jdbc.sql.condition.simple.SimpleConditionSqlFragment;
import org.yelong.core.jdbc.sql.condition.single.SingleConditionSqlFragmentFactory;
import org.yelong.core.jdbc.sql.defaults.DefaultAttributeSqlFragment;
import org.yelong.core.jdbc.sql.defaults.DefaultCombinationConditionSqlFragment;
import org.yelong.core.jdbc.sql.defaults.DefaultCountSqlFragment;
import org.yelong.core.jdbc.sql.defaults.DefaultDeleteSqlFragment;
import org.yelong.core.jdbc.sql.defaults.DefaultInsertSqlFragment;
import org.yelong.core.jdbc.sql.defaults.DefaultSelectSqlFragment;
import org.yelong.core.jdbc.sql.defaults.DefaultSimpleConditionSqlFragment;
import org.yelong.core.jdbc.sql.defaults.DefaultSingleConditionSqlFragmentFactory;
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
 * @author PengFei
 */
public class DefaultSqlFragmentFactory implements SqlFragmentFactory{

	private Dialect dialect;

	private SingleConditionSqlFragmentFactory singleConditionSqlFragmentFactory;
	
	public DefaultSqlFragmentFactory(Dialect dialect) {
		this.dialect = dialect;
	}

	@Override
	public AttributeSqlFragment createAttributeSqlFragment() {
		return new DefaultAttributeSqlFragment().setDialect(dialect);
	}

	@Override
	public SimpleConditionSqlFragment createConditionSqlFragment(String conditionFragment, Object... params) {
		return new DefaultSimpleConditionSqlFragment(conditionFragment, params);
	}

	@Override
	public SingleConditionSqlFragmentFactory getSingleConditionSqlFragmentFactory() {
		if( null == singleConditionSqlFragmentFactory ) {
			synchronized (this) {
				singleConditionSqlFragmentFactory = new DefaultSingleConditionSqlFragmentFactory();
			}
		}
		return singleConditionSqlFragmentFactory;
	}
	
	@Override
	public void setSingleConditionSqlFragmentFactory(
			SingleConditionSqlFragmentFactory singleConditionSqlFragmentFactory) {
		this.singleConditionSqlFragmentFactory = singleConditionSqlFragmentFactory;
	}
	
	@Override
	public CombinationConditionSqlFragment createCombinationConditionSqlFragment() {
		return new DefaultCombinationConditionSqlFragment();
	}

	@Override
	public SortSqlFragment createSortSqlFragment() {
		return new DefaultSortSqlFragment();
	}

	@Override
	public InsertSqlFragment createInsertSqlFragment(String tableName, AttributeSqlFragment attributeSqlFragment) {
		return new DefaultInsertSqlFragment(tableName, attributeSqlFragment);
	}

	@Override
	public InsertSqlFragment createInsertSqlFragment(String sql, Object... params) {
		return new DefaultInsertSqlFragment(sql, params);
	}
	
	@Override
	public DeleteSqlFragment createDeleteSqlFragment(String sql, Object... params) {
		return new DefaultDeleteSqlFragment(sql, params);
	}

	@Override
	public UpdateSqlFragment createUpdateSqlFragment(String sql, Object... params) {
		return new DefaultUpdateSqlFragment(sql, params);
	}

	@Override
	public UpdateSqlFragment createUpdateSqlFragment(String tableName, AttributeSqlFragment attributeSqlFragment) {
		return new DefaultUpdateSqlFragment(tableName, attributeSqlFragment);
	}
	
	@Override
	public SelectSqlFragment createSelectSqlFragment(String sql, Object... params) {
		return new DefaultSelectSqlFragment(sql, params);
	}

	@Override
	public CountSqlFragment createCountSqlFragment(String sql, Object... params) {
		return new DefaultCountSqlFragment(sql, params);
	}
	
	public Dialect getDialect() {
		return dialect;
	}

	@Override
	public void setDialect(Dialect dialect) {
		this.dialect = dialect;
	}
	
}

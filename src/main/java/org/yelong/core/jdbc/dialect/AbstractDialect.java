/**
 * 
 */
package org.yelong.core.jdbc.dialect;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.yelong.commons.lang.Strings;
import org.yelong.core.jdbc.sql.SpliceSqlUtils;
import org.yelong.core.jdbc.sql.condition.simple.SimpleConditionSqlFragmentFactory;
import org.yelong.core.jdbc.sql.condition.single.SingleConditionSqlFragmentFactory;
import org.yelong.core.jdbc.sql.defaults.DefaultSimpleConditionSqlFragment;
import org.yelong.core.jdbc.sql.defaults.DefaultSingleConditionSqlFragmentFactory;
import org.yelong.core.jdbc.sql.factory.DefaultSqlFragmentFactory;
import org.yelong.core.jdbc.sql.factory.SqlFragmentFactory;

/**
 * 抽象的方言实现
 */
public abstract class AbstractDialect implements Dialect {

	protected SqlFragmentFactory sqlFragmentFactory;

	@Override
	public String getBaseCountSql(String tableName, String tableAlias) {
		Strings.requireNonBlank(tableName);
		Strings.requireNonBlank(tableAlias);
		return " select count(0) from " + tableName + " " + tableAlias;
	}

	@Override
	public String getBaseSelectSql(String tableName, String tableAlias) {
		Strings.requireNonBlank(tableName);
		Strings.requireNonBlank(tableAlias);
		return " select " + tableAlias + ".* from " + tableName + " " + tableAlias;
	}

	@Override
	public String getBaseSelectSql(String tableName, String tableAlias, List<String> columns) {
		Strings.requireNonBlank(tableName);
		Strings.requireNonBlank(tableAlias);
		if (CollectionUtils.isEmpty(columns)) {
			throw new UnsupportedOperationException("缺失查询的列！");
		}
		List<String> sqlFragment = new ArrayList<String>();
		sqlFragment.add("select");
		sqlFragment.add(columns.stream().map(x -> tableAlias + "." + x).collect(Collectors.joining(" , ")));
		sqlFragment.add("from");
		sqlFragment.add(tableName);
		sqlFragment.add(tableAlias);
		return SpliceSqlUtils.spliceSqlFragment(sqlFragment);
	}

	@Override
	public SqlFragmentFactory getSqlFragmentFactory() {
		if (null == sqlFragmentFactory) {
			sqlFragmentFactory = new DefaultSqlFragmentFactory(this, getSingleConditionSqlFragmentFactory(),
					getSimpleConditionSqlFragmentFactory());
		}
		return sqlFragmentFactory;
	}

	protected SingleConditionSqlFragmentFactory getSingleConditionSqlFragmentFactory() {
		return new DefaultSingleConditionSqlFragmentFactory(this);
	}

	protected SimpleConditionSqlFragmentFactory getSimpleConditionSqlFragmentFactory() {
		return (x, y) -> {
			return new DefaultSimpleConditionSqlFragment(this, x, y);
		};
	}

}

/**
 * 
 */
package org.yelong.core.jdbc.sql.factory;

import org.yelong.core.jdbc.dialect.Dialect;
import org.yelong.core.jdbc.sql.attribute.AttributeSqlFragment;
import org.yelong.core.jdbc.sql.condition.ConditionSqlFragment;
import org.yelong.core.jdbc.sql.condition.combination.CombinationConditionSqlFragment;
import org.yelong.core.jdbc.sql.condition.single.SingleConditionSqlFragmentFactory;
import org.yelong.core.jdbc.sql.executable.CountSqlFragment;
import org.yelong.core.jdbc.sql.executable.DeleteSqlFragment;
import org.yelong.core.jdbc.sql.executable.InsertSqlFragment;
import org.yelong.core.jdbc.sql.executable.SelectSqlFragment;
import org.yelong.core.jdbc.sql.executable.UpdateSqlFragment;
import org.yelong.core.jdbc.sql.sort.SortSqlFragment;

/**
 * @author PengFei
 * @date 2020年1月20日上午10:55:47
 */
public interface SqlFragmentFactory {
	
	AttributeSqlFragment createAttributeSqlFragment();
	
	ConditionSqlFragment createConditionSqlFragment(String conditionFragment , Object ... params);
	
	CombinationConditionSqlFragment createCombinationConditionSqlFragment();
	
	SingleConditionSqlFragmentFactory getSingleConditionSqlFragmentFactory();
	
	SortSqlFragment createSortSqlFragment();
	
	InsertSqlFragment createInsertSqlFragment(String tableName , AttributeSqlFragment attributeSqlFragment);
	
	InsertSqlFragment createInsertSqlFragment(String sql , Object ... params);
	
	DeleteSqlFragment createDeleteSqlFragment(String sql , Object ... params);
	
	UpdateSqlFragment createUpdateSqlFragment(String sql , Object ... params);
	
	UpdateSqlFragment createUpdateSqlFragment(String tableName , AttributeSqlFragment attributeSqlFragment);
	
	SelectSqlFragment createSelectSqlFragment(String sql , Object ... params);
	
	CountSqlFragment createCountSqlFragment(String sql , Object ... params);

	Dialect getDialect();
	
}

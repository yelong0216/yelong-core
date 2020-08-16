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
import org.yelong.core.jdbc.sql.executable.CountSqlFragment;
import org.yelong.core.jdbc.sql.executable.DeleteSqlFragment;
import org.yelong.core.jdbc.sql.executable.InsertSqlFragment;
import org.yelong.core.jdbc.sql.executable.SelectSqlFragment;
import org.yelong.core.jdbc.sql.executable.UpdateSqlFragment;
import org.yelong.core.jdbc.sql.sort.SortSqlFragment;

/**
 * sql片段工厂
 */
public interface SqlFragmentFactory {

	// ==================================================attribute==================================================

	/**
	 * @return 属性sql
	 */
	AttributeSqlFragment createAttributeSqlFragment();

	// ==================================================insert==================================================

	/**
	 * 创建insert sql
	 * 
	 * @param tableName            表名
	 * @param attributeSqlFragment 属性sql
	 * @return insert sql
	 */
	InsertSqlFragment createInsertSqlFragment(String tableName, AttributeSqlFragment attributeSqlFragment);

	/**
	 * 创建insert sql
	 * 
	 * @param sql    sql
	 * @param params 参数
	 * @return insert sql
	 */
	InsertSqlFragment createInsertSqlFragment(String insertSql, Object... params);

	// ==================================================delete==================================================

	/**
	 * 创建delete sql
	 * 
	 * @param sql    sql
	 * @param params 参数
	 * @return delete sql
	 */
	DeleteSqlFragment createDeleteSqlFragment(String deleteSql, Object... params);

	// ==================================================update==================================================

	/**
	 * 创建update sql
	 * 
	 * @param sql    sql
	 * @param params 参数
	 * @return update sql
	 */
	UpdateSqlFragment createUpdateSqlFragment(String updateSql, Object... params);

	/**
	 * 创建update sql
	 * 
	 * @param tableName            表明
	 * @param attributeSqlFragment 属性sql
	 * @return update sql
	 */
	UpdateSqlFragment createUpdateSqlFragment(String tableName, AttributeSqlFragment attributeSqlFragment);

	// ==================================================select==================================================

	/**
	 * 创建select sql
	 * 
	 * @param sql    sql
	 * @param params 参数
	 * @return select sql
	 */
	SelectSqlFragment createSelectSqlFragment(String selectSql, Object... params);

	/**
	 * 创建count sql
	 * 
	 * @param sql    sql
	 * @param params 参数
	 * @return count sql
	 */
	CountSqlFragment createCountSqlFragment(String countSql, Object... params);

	// ==================================================condition==================================================

	/**
	 * 创建简单的条件
	 * 
	 * @param conditionSqlFragment 条件sql
	 * @param params               参数
	 * @return 简单的条件
	 */
	SimpleConditionSqlFragment createConditionSqlFragment(String conditionSqlFragment, Object... params);

	/**
	 * 创建组合条件
	 * 
	 * @return 组合条件
	 */
	CombinationConditionSqlFragment createCombinationConditionSqlFragment();

	// ==================================================sort==================================================

	/**
	 * 创建排序sql片段
	 * 
	 * @return 排序片段
	 */
	SortSqlFragment createSortSqlFragment();

	// ==================================================factory==================================================

	/**
	 * 获取单一条件工厂
	 * 
	 * @return 单一条件工厂
	 */
	SingleConditionSqlFragmentFactory getSingleConditionSqlFragmentFactory();

	/**
	 * 获取简单条件工厂
	 * 
	 * @return 简单条件工厂
	 */
	SimpleConditionSqlFragmentFactory getSimpleConditionSqlFragmentFactory();

	// ==================================================dialect==================================================

	/**
	 * @return 数据库方言
	 */
	Dialect getDialect();

}

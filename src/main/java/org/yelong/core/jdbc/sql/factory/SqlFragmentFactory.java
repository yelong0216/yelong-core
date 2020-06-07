/**
 * 
 */
package org.yelong.core.jdbc.sql.factory;

import org.yelong.core.jdbc.dialect.Dialect;
import org.yelong.core.jdbc.sql.attribute.AttributeSqlFragment;
import org.yelong.core.jdbc.sql.condition.combination.CombinationConditionSqlFragment;
import org.yelong.core.jdbc.sql.condition.simple.SimpleConditionSqlFragment;
import org.yelong.core.jdbc.sql.condition.single.SingleConditionSqlFragmentFactory;
import org.yelong.core.jdbc.sql.executable.CountSqlFragment;
import org.yelong.core.jdbc.sql.executable.DeleteSqlFragment;
import org.yelong.core.jdbc.sql.executable.InsertSqlFragment;
import org.yelong.core.jdbc.sql.executable.SelectSqlFragment;
import org.yelong.core.jdbc.sql.executable.UpdateSqlFragment;
import org.yelong.core.jdbc.sql.sort.SortSqlFragment;

/**
 * sql片段工厂
 * 
 * @author PengFei
 */
public interface SqlFragmentFactory {
	
	/**
	 * @return 属性sql
	 */
	AttributeSqlFragment createAttributeSqlFragment();
	
	/**
	 * 创建简单的条件
	 * 
	 * @param conditionSqlFragment 条件sql
	 * @param params 参数
	 * @return 简单的条件
	 */
	SimpleConditionSqlFragment createConditionSqlFragment(String conditionSqlFragment , Object ... params);
	
	/**
	 * 创建组合条件
	 * @return 组合条件
	 */
	CombinationConditionSqlFragment createCombinationConditionSqlFragment();
	
	/**
	 * 获取单一条件工厂
	 * 
	 * @return 单一条件工厂
	 */
	SingleConditionSqlFragmentFactory getSingleConditionSqlFragmentFactory();
	
	/**
	 * 设置单一条件工厂
	 * 
	 * @param SingleConditionSqlFragmentFactory
	 */
	@Deprecated
	void setSingleConditionSqlFragmentFactory(SingleConditionSqlFragmentFactory singleConditionSqlFragmentFactory);
	
	/**
	 * 创建排序sql片段
	 * 
	 * @return 排序片段
	 */
	SortSqlFragment createSortSqlFragment();
	
	/**
	 * 创建insert sql
	 * 
	 * @param tableName 表名
	 * @param attributeSqlFragment 属性sql
	 * @return insert sql
	 */
	InsertSqlFragment createInsertSqlFragment(String tableName , AttributeSqlFragment attributeSqlFragment);
	
	/**
	 * 创建insert sql
	 * 
	 * @param sql sql
	 * @param params 参数
	 * @return insert sql
	 */
	InsertSqlFragment createInsertSqlFragment(String sql , Object ... params);
	
	/**
	 * 创建delete sql
	 * 
	 * @param sql sql
	 * @param params 参数
	 * @return delete sql
	 */
	DeleteSqlFragment createDeleteSqlFragment(String sql , Object ... params);
	
	/**
	 * 创建update sql
	 * 
	 * @param sql sql
	 * @param params 参数
	 * @return update sql
	 */
	UpdateSqlFragment createUpdateSqlFragment(String sql , Object ... params);
	
	/**
	 * 创建update sql
	 * 
	 * @param tableName 表明
	 * @param attributeSqlFragment 属性sql
	 * @return update sql
	 */
	UpdateSqlFragment createUpdateSqlFragment(String tableName , AttributeSqlFragment attributeSqlFragment);
	
	/**
	 * 创建select sql
	 * 
	 * @param sql sql
	 * @param params 参数
	 * @return select sql
	 */
	SelectSqlFragment createSelectSqlFragment(String sql , Object ... params);
	
	/**
	 * 创建count sql
	 * 
	 * @param sql sql
	 * @param params 参数
	 * @return count sql
	 */
	CountSqlFragment createCountSqlFragment(String sql , Object ... params);

	/**
	 * @return 数据库方言
	 */
	Dialect getDialect();
	
}

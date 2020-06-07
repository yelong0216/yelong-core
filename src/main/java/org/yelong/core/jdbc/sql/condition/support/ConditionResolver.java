/**
 * 
 */
package org.yelong.core.jdbc.sql.condition.support;

import java.util.List;

import org.yelong.core.jdbc.sql.condition.combination.CombinationConditionSqlFragment;
import org.yelong.core.jdbc.sql.condition.single.SingleConditionSqlFragment;
import org.yelong.core.jdbc.sql.factory.SqlFragmentFactory;

/**
 * {@link Condition}的解析器
 * 
 * @author PengFei
 */
public interface ConditionResolver {
	
	/**
	 * 将一组{@link Condition}解析为{@link CombinationConditionSqlFragment}
	 * 
	 * @param conditions 一组条件
	 * @return {@link CombinationConditionSqlFragment}
	 * @throws ConditionResolverException 条件解析异常
	 */
	CombinationConditionSqlFragment resolve(List<Condition> conditions) throws ConditionResolverException;
	
	/**
	 * 将]{@link Condition}解析为{@link SingleConditionSqlFragment}
	 * 
	 * @param condition 条件
	 * @return {@link SingleConditionSqlFragment}
	 * @throws ConditionResolverException 条件解析异常
	 */
	SingleConditionSqlFragment resolve(Condition condition) throws ConditionResolverException;

	/**
	 * 设置sql片段工厂。
	 * 
	 * @param sqlFragmentFactory sql 片段工厂
	 * @return this
	 */
	ConditionResolver setSqlFragmentFactory(SqlFragmentFactory sqlFragmentFactory);
	
	/**
	 * @return sql 片段工厂
	 */
	SqlFragmentFactory getSqlFragmentFactory();
	
}

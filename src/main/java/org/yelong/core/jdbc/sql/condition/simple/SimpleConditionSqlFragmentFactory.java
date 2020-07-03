/**
 * 
 */
package org.yelong.core.jdbc.sql.condition.simple;

import org.yelong.core.annotation.Nullable;

/**
 * 简单的条件sql片段工厂
 * 
 * @author PengFei
 */
@FunctionalInterface
public interface SimpleConditionSqlFragmentFactory {

	/**
	 * 创建简单的条件片段
	 * 
	 * @param conditionSqlFragment 条件sql片段
	 * @param params               参数
	 * @return 简单的条件
	 */
	SimpleConditionSqlFragment create(String conditionSqlFragment, @Nullable Object[] params);

}

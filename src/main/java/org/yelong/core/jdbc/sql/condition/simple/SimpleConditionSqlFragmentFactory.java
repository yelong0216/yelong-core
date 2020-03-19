/**
 * 
 */
package org.yelong.core.jdbc.sql.condition.simple;

import org.yelong.core.annotation.Nullable;

/**
 * @author 彭飞
 * @date 2019年11月1日上午11:14:43
 * @version 1.2
 */
@FunctionalInterface
public interface SimpleConditionSqlFragmentFactory {

	/**
	 * 创建简单的条件片段
	 * @author 彭飞
	 * @date 2019年11月1日上午11:15:51
	 * @version 1.2
	 * @param conditionFragment
	 * @param params
	 * @return
	 */
	SimpleConditionSqlFragment create(String conditionFragment,@Nullable Object [] params);
	
	
}

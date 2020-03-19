/**
 * 
 */
package org.yelong.core.jdbc.sql.executable;

import org.yelong.core.jdbc.sql.condition.ConditionSqlFragment;

/**
 * count sql 片段
 * @author PengFei
 */
public interface CountSqlFragment extends SqlFragmentExecutable{

	/**
	 * 设置条件
	 * @param conditionSqlFragment 条件
	 * @return this
	 */
	CountSqlFragment setConditionSqlFragment(ConditionSqlFragment conditionSqlFragment);

	/**
	 * 获取条件
	 * @return 条件
	 */
	ConditionSqlFragment getConditionSqlFragment();

	/**
	 * 是否存在条件
	 * @return  <tt>true</tt> 存在条件
	 */
	default boolean existConditionSqlFragment() {
		return null != getConditionSqlFragment();
	}
	
}

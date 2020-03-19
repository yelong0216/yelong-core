/**
 * 
 */
package org.yelong.core.jdbc.sql.executable;

import org.yelong.core.jdbc.sql.condition.ConditionSqlFragment;

/**
 * 查询记录数sql
 * @author PengFei
 * @date 2020年3月4日下午6:25:42
 * @since 1.0
 */
public interface CountSqlFragment extends SqlFragmentExecutable{

	/**
	 * 设置条件
	 * @param conditionSqlFragment
	 * @return
	 */
	CountSqlFragment setConditionSqlFragment(ConditionSqlFragment conditionSqlFragment);

	/**
	 * 获取条件
	 * @return
	 */
	ConditionSqlFragment getConditionSqlFragment();

	/**
	 * 是否存在条件
	 * @return
	 */
	default boolean existConditionSqlFragment() {
		return null != getConditionSqlFragment();
	}
	
}

/**
 * 
 */
package org.yelong.core.jdbc.sql.executable;

import org.yelong.core.annotation.Nullable;
import org.yelong.core.jdbc.sql.condition.ConditionSqlFragment;

/**
 * delete sql
 */
public interface DeleteSqlFragment extends SqlFragmentExecutable {

	/**
	 * 设置条件
	 * 
	 * @param conditionSqlFragment条件
	 * @return this
	 */
	DeleteSqlFragment setConditionSqlFragment(ConditionSqlFragment conditionSqlFragment);

	/**
	 * 获取条件
	 * 
	 * @return 条件
	 */
	@Nullable
	ConditionSqlFragment getConditionSqlFragment();

	/**
	 * 是否存在条件
	 * 
	 * @return <tt>true</tt> 存在条件
	 */
	default boolean existConditionSqlFragment() {
		return null != getConditionSqlFragment();
	}

}

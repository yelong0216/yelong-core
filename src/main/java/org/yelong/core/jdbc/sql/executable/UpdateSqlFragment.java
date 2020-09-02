package org.yelong.core.jdbc.sql.executable;

import org.yelong.core.annotation.Nullable;
import org.yelong.core.jdbc.sql.attribute.AttributeSqlFragment;
import org.yelong.core.jdbc.sql.condition.ConditionSqlFragment;

/**
 * update sql片段
 */
public interface UpdateSqlFragment extends SqlFragmentExecutable {

	/**
	 * 设置条件
	 * 
	 * @param conditionSqlFragment 条件
	 * @return this
	 */
	UpdateSqlFragment setConditionSqlFragment(ConditionSqlFragment conditionSqlFragment);

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

	/**
	 * 获取属性sql
	 * 
	 * @return 属性sql
	 */
	AttributeSqlFragment getAttributeSqlFragment();

}
package org.yelong.core.jdbc.sql.executable;

import org.yelong.core.jdbc.sql.attribute.AttributeSqlFragment;
import org.yelong.core.jdbc.sql.condition.ConditionSqlFragment;

/**
 * 修改sql片段
 * @author PengFei
 * @date 2020年1月20日下午12:08:14
 */
public interface UpdateSqlFragment extends SqlFragmentExecutable{

	/**
	 * 设置条件
	 * @param conditionSqlFragment
	 * @return
	 */
	UpdateSqlFragment setConditionSqlFragment(ConditionSqlFragment conditionSqlFragment);

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

	/**
	 * 获取属性sql
	 * @return
	 */
	AttributeSqlFragment getAttributeSqlFragment();

}
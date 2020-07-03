/**
 * 
 */
package org.yelong.core.jdbc.sql.executable;

import org.yelong.core.jdbc.sql.condition.ConditionSqlFragment;
import org.yelong.core.jdbc.sql.sort.SortSqlFragment;

/**
 * select sql
 * 
 * @author PengFei
 */
public interface SelectSqlFragment extends SqlFragmentExecutable {

	/**
	 * 设置条件
	 * 
	 * @param conditionSqlFragment 条件
	 * @return this
	 */
	SelectSqlFragment setConditionSqlFragment(ConditionSqlFragment conditionSqlFragment);

	/**
	 * 获取条件
	 * 
	 * @return 条件
	 */
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
	 * 设置排序
	 * 
	 * @param sortSqlFragment 排序
	 * @return this
	 */
	SelectSqlFragment setSortSqlFragment(SortSqlFragment sortSqlFragment);

	/**
	 * 获取排序
	 * 
	 * @return 排序
	 */
	SortSqlFragment getSortSqlFragment();

	/**
	 * 是否存在排序
	 * 
	 * @return <tt>true</tt> 存在排序
	 */
	default boolean existSortSqlFragment() {
		return null != getSortSqlFragment();
	}

	/**
	 * 启动分页
	 * 
	 * @param pageNum  数量
	 * @param pageSize 页码
	 */
	void startPage(int pageNum, int pageSize);

	/**
	 * 取消分页
	 */
	void cancelPage();

	/**
	 * 是否分页
	 * 
	 * @return <tt>true</tt> 进行了分页
	 */
	boolean isPage();

	/**
	 * 获取数量
	 * 
	 * @return 如果 {@link #isPage()}为false 则返回null
	 */
	Integer getPageNum();

	/**
	 * 获取页码
	 * 
	 * @return 如果 {@link #isPage()}为false 则返回null
	 */
	Integer getPageSize();

}

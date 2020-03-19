/**
 * 
 */
package org.yelong.core.jdbc.sql.executable;

import org.yelong.core.jdbc.sql.condition.ConditionSqlFragment;
import org.yelong.core.jdbc.sql.sort.SortSqlFragment;

/**
 * 查询sql片段
 * @author PengFei
 * @date 2020年3月4日下午6:27:18
 * @since 1.0
 */
public interface SelectSqlFragment extends SqlFragmentExecutable{

	/**
	 * 设置条件
	 * @param conditionSqlFragment
	 * @return
	 */
	SelectSqlFragment setConditionSqlFragment(ConditionSqlFragment conditionSqlFragment);

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
	 * 设置排序
	 * @param sortSqlFragment
	 * @return
	 */
	SelectSqlFragment setSortSqlFragment(SortSqlFragment sortSqlFragment);
	
	/**
	 * 获取排序
	 * @return
	 */
	SortSqlFragment getSortSqlFragment();
	
	/**
	 * 是否存在排序
	 * @return
	 */
	default boolean existSortSqlFragment() {
		return null != getSortSqlFragment();
	}
	
	/**
	 * 启动分页
	 * @author 彭飞
	 * @date 2019年11月1日上午11:37:15
	 * @version 1.2
	 * @param pageNum 数量
	 * @param pageSize 页码
	 */
	void startPage(int pageNum , int pageSize);
	
	/**
	 * 取消分页
	 * @author 彭飞
	 * @date 2019年11月1日上午11:39:53
	 * @version 1.2
	 */
	void cancelPage();
	
	/**
	 * 是否分页
	 * @author 彭飞
	 * @date 2019年11月1日上午11:37:37
	 * @version 1.2
	 * @return
	 */
	boolean isPage();
	
	/**
	 * 获取数量
	 * @author 彭飞
	 * @date 2019年11月1日上午11:41:07
	 * @version 1.2
	 * @return 如果 {@link #isPage()}为false 则返回null
	 */
	Integer getPageNum();
	
	/**
	 * 获取页码
	 * @author 彭飞
	 * @date 2019年11月1日上午11:41:15
	 * @version 1.2
	 * @return 如果 {@link #isPage()}为false 则返回null
	 */
	Integer getPageSize();
	
}

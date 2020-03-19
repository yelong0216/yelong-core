/**
 * 
 */
package org.yelong.core.jdbc.sql.condition;

import org.yelong.core.jdbc.sql.SqlFragment;

/**
 * 条件片段<br/>
 * 表示一个sql的条件语句片段。<br/>
 * 这可能是一组条件，也可能是一个条件<br/>
 * @author 彭飞
 * @date 2019年8月21日下午4:34:49
 * @version 1.2
 */
public interface ConditionSqlFragment extends SqlFragment{
	
	/**
	 * 是否需要拼接where
	 * @return <tt>true</tt>条件sql中拼接where关键字
	 */
	boolean isWhere();
	
	/**
	 * 设置是否需要追加where
	 * @param where <tt>true</tt>条件sql中拼接where关键字 false 不拼接
	 */
	void setWhere(boolean where);
	
}

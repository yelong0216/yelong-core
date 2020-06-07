/**
 * 
 */
package org.yelong.core.jdbc.sql.condition.single;

import org.yelong.core.annotation.Nullable;
import org.yelong.core.jdbc.sql.condition.ConditionSqlFragment;

/**
 * 单个条件语句
 * 
 * @author PengFei
 */
public interface SingleConditionSqlFragment extends ConditionSqlFragment{
	
	/**
	 * @return 字段名称
	 */
	String getFieldName();
	
	/**
	 * @return 条件操作符
	 */
	String getConditionOperator();
	
	/**
	 * @return 条件的值，这可能是List
	 * 			如果该条件不需要值则返回null
	 */
	@Nullable 
	Object getValue();
	
	/**
	 * @return 获取条件第二个值。
	 * 			如果条件不需要第二个值则返回null
	 */
	@Nullable 
	Object getSecondValue();
	
	/**
	 * @return <tt>true</tt> 条件不需要值
	 */
	boolean isNoValue();
	
	/**
	 * @return <tt>true</tt> 条件需要单个值
	 */
	boolean isSingleValue();
	
	/**
	 * @return <tt>true</tt> 条件需要两个值
	 */
	boolean isBetweenValue();
	
	/**
	 * @return <tt>true</tt> 条件需要任意个值
	 */
	boolean isListValue();
	
}

/**
 * 
 */
package org.yelong.core.jdbc.sql.condition.single;

import org.yelong.core.annotation.Nullable;
import org.yelong.core.jdbc.sql.condition.ConditionSqlFragment;

/**
 * 单个条件语句
 * @author 彭飞
 * @date 2019年8月22日下午5:49:16
 * @version 1.2
 */
public interface SingleConditionSqlFragment extends ConditionSqlFragment{
	
	/**
	 * 获取该条件的字段名称
	 * @author 彭飞
	 * @date 2019年8月22日下午5:58:32
	 * @version 1.2
	 * @return 字段名称
	 */
	String getFieldName();
	
	/**
	 * 获取条件操作符
	 * @author 彭飞
	 * @date 2019年8月22日下午5:58:44
	 * @version 1.2
	 * @return 条件操作符
	 */
	String getConditionOperator();
	
	/**
	 * 条件值<br/>
	 * 获取参数值
	 * @author 彭飞
	 * @date 2019年8月22日下午5:59:09
	 * @version 1.2
	 * @return 条件的值，这可能是List
	 * 			如果该条件不需要值则返回null
	 */
	@Nullable 
	Object getValue();
	
	/**
	 * 获取第二个参数值<br/>
	 * 如果条件不是between操作符则为null
	 * @author 彭飞
	 * @date 2019年8月22日下午5:59:45
	 * @version 1.2
	 * @return 获取条件第二个值。
	 * 			如果条件不需要第二个值则返回null
	 */
	@Nullable 
	Object getSecondValue();
	
	/**
	 * @author 彭飞
	 * @date 2019年8月22日下午6:01:34
	 * @version 1.2
	 * @return <tt>true</tt> 条件不需要值
	 */
	boolean isNoValue();
	
	/**
	 * @author 彭飞
	 * @date 2019年8月22日下午6:01:55
	 * @version 1.2
	 * @return <tt>true</tt> 条件需要单个值
	 */
	boolean isSingleValue();
	
	/**
	 * @author 彭飞
	 * @date 2019年8月22日下午6:02:07
	 * @version 1.2
	 * @return <tt>true</tt> 条件需要两个值
	 */
	boolean isBetweenValue();
	
	/**
	 * @author 彭飞
	 * @date 2019年8月22日下午6:02:18
	 * @version 1.2
	 * @return <tt>true</tt> 条件需要任意个值
	 */
	boolean isListValue();
	
}

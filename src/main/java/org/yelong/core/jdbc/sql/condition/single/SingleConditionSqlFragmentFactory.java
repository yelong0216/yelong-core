/**
 * 
 */
package org.yelong.core.jdbc.sql.condition.single;

import java.util.Collection;
import java.util.List;

import org.yelong.core.jdbc.sql.exception.InvalidConditionException;

/**
 * 单一条件语句工厂<br/>
 * 创建的SingleConditionClause对象，应该是一个规范的条件
 * @author 彭飞
 * @date 2019年8月22日下午5:11:24
 * @version 1.2
 */
public interface SingleConditionSqlFragmentFactory {
	
	/**
	 * 添加一个条件。该条件是不支持参数的类型。如：is null等。
	 * @author 彭飞
	 * @date 2019年7月26日下午1:59:34
	 * @param fieldName 字段名称
	 * @param condition 条件
	 * @throws InvalidConditionException 如果这条件是一个无效的条件
	 */
	SingleConditionSqlFragment create(String fieldName,String condition) throws InvalidConditionException;
	
	/**
	 * 添加一个条件。<br/>
	 * 如果value 实现Collection 则{@link #createConditionClause(String, String, Collection)}
	 * @author 彭飞
	 * @date 2019年7月26日下午2:00:42
	 * @param fieldName 字段名称
	 * @param condition 条件
	 * @param value 值
	 * @throws InvalidConditionException 如果这条件是一个无效的条件
	 */
	SingleConditionSqlFragment create(String fieldName,String condition,Object value) throws InvalidConditionException;
	
	/**
	 * 添加一个条件。<br/>
	 * 该条件应该需要多个值
	 * @author 彭飞
	 * @date 2019年7月26日下午2:00:42
	 * @param fieldName 字段名称
	 * @param condition 条件
	 * @param value 值
	 * @throws InvalidConditionException 如果这条件是一个无效的条件
	 */
	SingleConditionSqlFragment create(String fieldName,String condition,List<Object> value) throws InvalidConditionException;
	
	/**
	 * 添加一个条件。<br/>
	 * 该条件需要两个值。例如 between
	 * @author 彭飞
	 * @date 2019年7月26日下午2:02:12
	 * @param fieldName 字段名称
	 * @param condition 条件
	 * @param value1 第一个值
	 * @param value2 第二个值
	 * @throws InvalidConditionException 如果这条件是一个无效的条件
	 */
	SingleConditionSqlFragment create(String fieldName,String condition ,Object value1 , Object value2) throws InvalidConditionException;
	
}

/**
 * 
 */
package org.yelong.core.jdbc.sql.condition.single;

import java.util.Collection;
import java.util.List;

import org.yelong.core.jdbc.dialect.Dialect;
import org.yelong.core.jdbc.sql.exception.InvalidConditionException;

/**
 * 单一条件语句工厂<br/>
 * 创建的SingleConditionClause对象，应该是一个规范的条件
 * 
 * @author PengFei
 */
public interface SingleConditionSqlFragmentFactory {

	/**
	 * 添加一个条件。该条件是不支持参数的类型。如：is null等。
	 * 
	 * @param column    列名称
	 * @param condition 条件
	 * @throws InvalidConditionException 如果这条件是一个无效的条件
	 */
	SingleConditionSqlFragment create(String column, String condition) throws InvalidConditionException;

	/**
	 * 添加一个条件。<br/>
	 * 如果value 实现Collection
	 * 则{@link #createConditionClause(String, String, Collection)}
	 * 
	 * @param column    列名称
	 * @param condition 条件
	 * @param value     值
	 * @throws InvalidConditionException 如果这条件是一个无效的条件
	 */
	SingleConditionSqlFragment create(String column, String condition, Object value) throws InvalidConditionException;

	/**
	 * 添加一个条件。<br/>
	 * 该条件应该需要多个值
	 * 
	 * @param column    列名称
	 * @param condition 条件
	 * @param value     值
	 * @throws InvalidConditionException 如果这条件是一个无效的条件
	 */
	SingleConditionSqlFragment create(String column, String condition, List<Object> value)
			throws InvalidConditionException;

	/**
	 * 添加一个条件。<br/>
	 * 该条件需要两个值。例如 between
	 * 
	 * @param column    列名称
	 * @param condition 条件
	 * @param value1    第一个值
	 * @param value2    第二个值
	 * @throws InvalidConditionException 如果这条件是一个无效的条件
	 */
	SingleConditionSqlFragment create(String column, String condition, Object value1, Object value2)
			throws InvalidConditionException;

	/**
	 * @return 数据库方言
	 */
	Dialect getDialect();

}

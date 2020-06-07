/**
 * 
 */
package org.yelong.core.jdbc.sql.condition.combination;

import java.util.List;

import org.yelong.core.jdbc.sql.condition.ConditionSqlFragment;
import org.yelong.core.jdbc.sql.exception.InvalidConditionException;

/**
 * 组合条件<br/>
 * 一组条件
 * 
 * @author PengFei
 */
public interface CombinationConditionSqlFragment extends ConditionSqlFragment{
	
	/**
	 * 添加一个条件。该条件是不支持参数的类型。如：is null等。
	 * 该条件使用and进行拼接
	 * 
	 * @param fieldName 字段名称
	 * @param conditionOperator 条件
	 * @throws InvalidConditionException 如果这条件是一个无效的条件
	 * @return this
	 */
	CombinationConditionSqlFragment and(String fieldName,String conditionOperator) throws InvalidConditionException;
	
	/**
	 * 添加一个条件。<br/>
	 * 如果value为List则{@link #and(String, String, List)}
	 * 该条件使用and进行拼接
	 * 
	 * @param fieldName 字段名称
	 * @param conditionOperator 条件
	 * @param value 值
	 * @throws InvalidConditionException 如果这条件是一个无效的条件
	 * @return this
	 */
	CombinationConditionSqlFragment and(String fieldName,String conditionOperator,Object value) throws InvalidConditionException;
	
	/**
	 * 添加一个条件。<br/>
	 * 该条件需要N个参数的类型。例如:in
	 * 该条件使用and进行拼接
	 * 
	 * @param fieldName 字段名称
	 * @param conditionOperator 条件
	 * @param value 值
	 * @throws InvalidConditionException 如果这条件是一个无效的条件
	 * @return this
	 */
	CombinationConditionSqlFragment and(String fieldName,String conditionOperator,List<Object> value) throws InvalidConditionException;
	
	/**
	 * 添加一个条件。<br/>
	 * 该条件需要两个值。例如 between
	 * 该条件使用and进行拼接
	 * 
	 * @param fieldName 字段名称
	 * @param conditionOperator 条件
	 * @param value1 第一个值
	 * @param value2 第二个值
	 * @throws InvalidConditionException 如果这条件是一个无效的条件
	 * @return this
	 */
	CombinationConditionSqlFragment and(String fieldName,String conditionOperator ,Object value1 , Object value2) throws InvalidConditionException;
	
	/**
	 * 添加一个条件
	 * 
	 * @param SimpleConditionFragment 单个条件
	 * @return this
	 */
	CombinationConditionSqlFragment and(ConditionSqlFragment conditionFragment);
	
	/**
	 * 添加一个条件。该条件是不支持参数的类型。如：is null等。
	 * 该条件使用or进行拼接
	 * 
	 * @param fieldName 字段名称
	 * @param conditionOperator 条件
	 * @throws InvalidConditionException 如果这条件是一个无效的条件
	 * @return this
	 */
	CombinationConditionSqlFragment or(String fieldName,String conditionOperator) throws InvalidConditionException;
	
	/**
	 * 添加一个条件。<br/>
	 * 如果value为List则{@link #or(String, String, List)}
	 * 该条件使用or进行拼接
	 * 
	 * @param fieldName 字段名称
	 * @param conditionOperator 条件
	 * @param value 值
	 * @throws InvalidConditionException 如果这条件是一个无效的条件
	 * @return this
	 */
	CombinationConditionSqlFragment or(String fieldName,String conditionOperator,Object value) throws InvalidConditionException;
	
	/**
	 * 添加一个条件。<br/>
	 * 该条件需要N个参数的类型。例如:in
	 * 该条件使用or进行拼接
	 * 
	 * @param fieldName 字段名称
	 * @param conditionOperator 条件
	 * @param value 值
	 * @throws InvalidConditionException 如果这条件是一个无效的条件
	 * @return this
	 */
	CombinationConditionSqlFragment or(String fieldName,String conditionOperator,List<Object> value) throws InvalidConditionException;
	
	
	/**
	 * 添加一个条件。<br/>
	 * 该条件需要两个值。例如 between
	 * 该条件使用or进行拼接
	 * 
	 * @param fieldName 字段名称
	 * @param conditionOperator 条件
	 * @param value1 第一个值
	 * @param value2 第二个值
	 * @throws InvalidConditionException 如果这条件是一个无效的条件
	 * @return this
	 */
	CombinationConditionSqlFragment or(String fieldName,String conditionOperator ,Object value1 , Object value2) throws InvalidConditionException;
	
	/**
	 * 添加一个条件
	 * 
	 * @param SimpleConditionFragment 单个条件
	 * @return this
	 */
	CombinationConditionSqlFragment or(ConditionSqlFragment conditionFragment);
	
	/**
	 * 条件是否为空
	 * s
	 * @return <tt>true</tt>如果条件为空
	 */
	boolean isEmpty();
	
}

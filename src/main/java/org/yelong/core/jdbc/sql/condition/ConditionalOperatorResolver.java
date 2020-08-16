/**
 * 
 */
package org.yelong.core.jdbc.sql.condition;

/**
 * 条件运算符解析器
 * 
 * @since 2.0
 */
public interface ConditionalOperatorResolver {

	/**
	 * 解析条件枚举为实际数据库进行运算的运算符
	 * 
	 * @param conditionalOperator 条件运算符
	 * @return 实际在数据库中进行使用的运算符
	 */
	String resolve(ConditionalOperator conditionalOperator);

}

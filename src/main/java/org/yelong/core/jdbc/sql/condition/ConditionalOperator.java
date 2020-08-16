/**
 * 
 */
package org.yelong.core.jdbc.sql.condition;

/**
 * @since 2.0
 */
public enum ConditionalOperator {

	/** like */
	LIKE,
	/** not like */
	NOT_LIKE,
	/** 等于 */
	EQUAL,
	/** 不等于 */
	NOT_EQUAL,
	/** 包含 */
	IN,
	/** 不包含 */
	NOT_IN,
	/** 区间 */
	BETWEEN,
	/** 不在指定的区间 */
	NOT_BETWEEN,
	/** 为空 */
	IS_NULL,
	/** 不为空 */
	IS_NOT_NULL,
	/** 大于 */
	GREATER_THAN,
	/** 大于等于 */
	GREATER_THAN_OR_EQUAL,
	/** 小于 */
	LESS_THAN,
	/** 小于等于 */
	LESS_THAN_OR_EQUAL;

}

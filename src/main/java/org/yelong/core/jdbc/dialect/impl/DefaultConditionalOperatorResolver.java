/**
 * 
 */
package org.yelong.core.jdbc.dialect.impl;

import java.util.Objects;

import org.yelong.core.jdbc.sql.condition.ConditionalOperator;
import org.yelong.core.jdbc.sql.condition.ConditionalOperatorResolver;
import static org.yelong.core.jdbc.SqlKeyword.*;

/**
 * 默认的条件运算符解析器
 * 
 * @since 2.0
 */
public class DefaultConditionalOperatorResolver implements ConditionalOperatorResolver {

	public static final ConditionalOperatorResolver INSTANCE = new DefaultConditionalOperatorResolver();

	@Override
	public String resolve(ConditionalOperator conditionalOperator) {
		Objects.requireNonNull(conditionalOperator);
		switch (conditionalOperator) {
		case LIKE:
			return LIKE.getKeyword();
		case NOT_LIKE:
			return NOT.getKeyword() + " " + LIKE.getKeyword();
		case EQUAL:
			return EQ.getKeyword();
		case NOT_EQUAL:
			return NOT.getKeyword() + " " + EQ.getKeyword();
		case IN:
			return IN.getKeyword();
		case NOT_IN:
			return NOT.getKeyword() + " " + IN.getKeyword();
		case BETWEEN:
			return BETWEEN.getKeyword();
		case NOT_BETWEEN:
			return NOT.getKeyword() + " " + BETWEEN.getKeyword();
		case IS_NULL:
			return IS_NULL.getKeyword();
		case IS_NOT_NULL:
			return IS_NOT_NULL.getKeyword();
		case GREATER_THAN:
			return GT.getKeyword();
		case GREATER_THAN_OR_EQUAL:
			return GE.getKeyword();
		case LESS_THAN:
			return LT.getKeyword();
		case LESS_THAN_OR_EQUAL:
			return LE.getKeyword();
		default:
			throw new UnsupportedOperationException("不支持的条件运算符：" + conditionalOperator);
		}
	}

}

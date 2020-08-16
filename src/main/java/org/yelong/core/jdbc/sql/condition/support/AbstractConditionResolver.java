/**
 * 
 */
package org.yelong.core.jdbc.sql.condition.support;

import org.yelong.core.jdbc.sql.condition.ConditionalOperatorResolver;
import org.yelong.core.jdbc.sql.factory.SqlFragmentFactory;

/**
 * 抽象的条件解析器实现
 */
public abstract class AbstractConditionResolver implements ConditionResolver {

	protected SqlFragmentFactory sqlFragmentFactory;

	protected ConditionalOperatorResolver conditionalOperatorResolver;

	public AbstractConditionResolver(SqlFragmentFactory sqlFragmentFactory) {
		this.sqlFragmentFactory = sqlFragmentFactory;
		this.conditionalOperatorResolver = sqlFragmentFactory.getDialect().getConditionalOperatorResolver();
	}

	@Override
	public SqlFragmentFactory getSqlFragmentFactory() {
		return this.sqlFragmentFactory;
	}

	@Override
	public ConditionResolver setSqlFragmentFactory(SqlFragmentFactory sqlFragmentFactory) {
		this.sqlFragmentFactory = sqlFragmentFactory;
		return this;
	}

}

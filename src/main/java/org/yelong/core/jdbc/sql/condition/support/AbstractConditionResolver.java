/**
 * 
 */
package org.yelong.core.jdbc.sql.condition.support;

import org.yelong.core.jdbc.sql.factory.SqlFragmentFactory;

/**
 * 抽象的条件解析器实现
 * 
 * @author PengFei
 */
public abstract class AbstractConditionResolver implements ConditionResolver{
	
	protected SqlFragmentFactory sqlFragmentFactory;
	
	public AbstractConditionResolver(SqlFragmentFactory sqlFragmentFactory) {
		this.sqlFragmentFactory = sqlFragmentFactory;
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

/**
 * 
 */
package org.yelong.core.jdbc.sql.condition.support;

import org.yelong.core.jdbc.sql.factory.SqlFragmentFactory;

/**
 * @author PengFei
 * @date 2020年2月25日上午11:43:42
 * @since 1.0
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

/**
 * 
 */
package org.yelong.core.test.jdbc.sql.condition.support;

import org.yelong.core.jdbc.dialect.impl.mysql.MySqlDialect;
import org.yelong.core.jdbc.sql.condition.support.ConditionResolver;
import org.yelong.core.jdbc.sql.condition.support.DefaultConditionResolver;

/**
 * @since
 */
public class ConditionResolverTest {

	public static final ConditionResolver conditionResolver = new DefaultConditionResolver(new MySqlDialect().getSqlFragmentFactory());
	
}

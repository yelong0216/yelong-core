/**
 * 
 */
package org.yelong.core.jdbc.sql.condition.support;

import java.util.List;

import org.yelong.core.jdbc.sql.condition.combination.CombinationConditionSqlFragment;
import org.yelong.core.jdbc.sql.condition.single.SingleConditionSqlFragment;
import org.yelong.core.jdbc.sql.factory.SqlFragmentFactory;

/**
 * @author PengFei
 * @date 2020年2月25日上午11:17:24
 * @since 1.0
 */
public interface ConditionResolver {
	
	CombinationConditionSqlFragment resolve(List<Condition> conditionList) throws ConditionResolverException;
	
	SingleConditionSqlFragment resolve(Condition condition) throws ConditionResolverException;

	ConditionResolver setSqlFragmentFactory(SqlFragmentFactory sqlFragmentFactory);
	
	SqlFragmentFactory getSqlFragmentFactory();
	
}

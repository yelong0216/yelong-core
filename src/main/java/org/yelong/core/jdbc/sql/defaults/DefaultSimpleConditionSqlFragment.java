/**
 * 
 */
package org.yelong.core.jdbc.sql.defaults;

import org.yelong.core.annotation.Nullable;
import org.yelong.core.jdbc.sql.condition.AbstractConditionSqlFragment;
import org.yelong.core.jdbc.sql.condition.simple.SimpleConditionSqlFragment;

/**
 * @author 彭飞
 * @date 2019年10月24日下午5:40:33
 * @version 1.2
 */
public class DefaultSimpleConditionSqlFragment extends AbstractConditionSqlFragment implements SimpleConditionSqlFragment{

	private final String conditionFragment;
	
	@Nullable 
	private final Object [] params;

	public DefaultSimpleConditionSqlFragment(String conditionFragment,@Nullable  Object [] params) {
		this.conditionFragment = conditionFragment;
		this.params = params;
	}
	
	@Override
	public String getSqlFragment() {
		return where(this.conditionFragment);
	}

	@Override
	public Object[] getParams() {
		return this.params;
	}
	
	
	
}

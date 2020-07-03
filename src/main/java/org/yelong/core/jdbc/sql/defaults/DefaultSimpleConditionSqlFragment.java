/**
 * 
 */
package org.yelong.core.jdbc.sql.defaults;

import org.yelong.core.annotation.Nullable;
import org.yelong.core.jdbc.dialect.Dialect;
import org.yelong.core.jdbc.sql.condition.AbstractConditionSqlFragment;
import org.yelong.core.jdbc.sql.condition.simple.SimpleConditionSqlFragment;

/**
 * 默认的简单条件条件实现
 * 
 * @author PengFei
 */
public class DefaultSimpleConditionSqlFragment extends AbstractConditionSqlFragment
		implements SimpleConditionSqlFragment {

	private final String conditionFragment;

	@Nullable
	private final Object[] params;

	public DefaultSimpleConditionSqlFragment(Dialect dialect, String conditionFragment, @Nullable Object[] params) {
		super(dialect);
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

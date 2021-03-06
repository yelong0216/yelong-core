/**
 * 
 */
package org.yelong.core.jdbc.sql.condition;

import org.yelong.core.jdbc.dialect.Dialect;
import org.yelong.core.jdbc.sql.AbstractSqlFragment;

/**
 * 抽象的条件片段实现
 */
public abstract class AbstractConditionSqlFragment extends AbstractSqlFragment implements ConditionSqlFragment {

	private boolean where = true;

	public AbstractConditionSqlFragment(Dialect dialect) {
		super(dialect);
	}

	public boolean isWhere() {
		return where;
	}

	public void setWhere(boolean where) {
		this.where = where;
	}

	/**
	 * 根据是否需要 where 来拼接 where 关键字
	 * 
	 * @return 拼接后的sql
	 */
	protected String where(String conditionSqlFragment) {
		if (where) {
			return " where " + conditionSqlFragment;
		} else {
			return conditionSqlFragment;
		}
	}

}

/**
 * 
 */
package org.yelong.core.jdbc.sql.condition;

import org.yelong.core.jdbc.sql.AbstractSqlFragment;

/**
 * 抽象的条件片段实现
 * 
 * @author PengFei
 */
public abstract class AbstractConditionSqlFragment extends AbstractSqlFragment implements ConditionSqlFragment{

	private boolean where = true;

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
		if(where) {
			return " where " + conditionSqlFragment;
		} else {
			return conditionSqlFragment;
		}
	}
	
}

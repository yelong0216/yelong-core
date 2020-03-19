/**
 * 
 */
package org.yelong.core.jdbc.sql.condition;

import org.yelong.core.jdbc.sql.AbstractSqlFragment;

/**
 * @author PengFei
 * @date 2020年1月20日下午4:25:42
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
	 * @return
	 */
	protected String where(String conditionSqlFragment) {
		if(where) {
			return " where " + conditionSqlFragment;
		} else {
			return conditionSqlFragment;
		}
	}
	
	
	
}

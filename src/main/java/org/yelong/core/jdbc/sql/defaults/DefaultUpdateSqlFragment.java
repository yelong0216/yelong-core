/**
 * 
 */
package org.yelong.core.jdbc.sql.defaults;

import java.util.Objects;

import org.apache.commons.lang3.ArrayUtils;
import org.yelong.core.jdbc.sql.BoundSql;
import org.yelong.core.jdbc.sql.attribute.AttributeSqlFragment;
import org.yelong.core.jdbc.sql.condition.ConditionSqlFragment;
import org.yelong.core.jdbc.sql.executable.AbstractSqlFragmentExecutable;
import org.yelong.core.jdbc.sql.executable.UpdateSqlFragment;

/**
 * @author 彭飞
 * @date 2019年10月31日上午11:22:35
 * @version 1.2
 */
public class DefaultUpdateSqlFragment extends AbstractSqlFragmentExecutable implements UpdateSqlFragment{

	private AttributeSqlFragment attributeSqlFragment;

	private BoundSql attributeBoundSql;

	private ConditionSqlFragment conditionSqlFragment;

	private BoundSql conditionBoundSql;

	private String tableName;
	
	public DefaultUpdateSqlFragment(String tableName , AttributeSqlFragment attributeSqlFragment) {
		Objects.requireNonNull(tableName, "未发现表名称！");
		Objects.requireNonNull(attributeSqlFragment, "未发现列！");
		this.tableName = tableName;
		this.attributeSqlFragment = attributeSqlFragment;
		this.attributeBoundSql = attributeSqlFragment.getBoundSql();
	}
	
	public DefaultUpdateSqlFragment(String sql , Object ... params) {
		super(sql,params);
	}
	
	@Override
	public String getSqlFragment() {
		String sql = null;
		if( existBaseSql() ) {
			sql = getBaseSql();
		} else {
			sql = " update " + tableName + " set " + attributeBoundSql.getSql();
		}
		if(existConditionSqlFragment()) {
			sql = sql + " " + conditionBoundSql.getSql();
		}
		return sql;
	}

	@Override
	public Object[] getParams() {
		Object [] attrParams = null;
		if( existBaseSql() ) {
			attrParams = super.getBaseParams();
		} else {
			attrParams = attributeBoundSql.getParams();
		}
		if(!existConditionSqlFragment()) {
			return attrParams;
		} else {
			return ArrayUtils.addAll(attrParams,conditionBoundSql.getParams());
		}
	}
	@Override
	public AttributeSqlFragment getAttributeSqlFragment() {
		return this.attributeSqlFragment;
	}

	@Override
	public UpdateSqlFragment setConditionSqlFragment(ConditionSqlFragment conditionSqlFragment) {
		this.conditionSqlFragment = conditionSqlFragment;
		this.conditionBoundSql = conditionSqlFragment.getBoundSql();
		return this;
	}

	@Override
	public ConditionSqlFragment getConditionSqlFragment() {
		return this.conditionSqlFragment;
	}

}

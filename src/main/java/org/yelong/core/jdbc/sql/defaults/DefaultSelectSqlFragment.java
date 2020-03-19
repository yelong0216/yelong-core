/**
 * 
 */
package org.yelong.core.jdbc.sql.defaults;

import org.apache.commons.lang3.ArrayUtils;
import org.yelong.core.annotation.Nullable;
import org.yelong.core.jdbc.sql.BoundSql;
import org.yelong.core.jdbc.sql.condition.ConditionSqlFragment;
import org.yelong.core.jdbc.sql.executable.AbstractSqlFragmentExecutable;
import org.yelong.core.jdbc.sql.executable.SelectSqlFragment;
import org.yelong.core.jdbc.sql.sort.SortSqlFragment;

/**
 * @author 彭飞
 * @date 2019年10月31日上午11:00:45
 * @version 1.2
 */
public class DefaultSelectSqlFragment extends AbstractSqlFragmentExecutable implements SelectSqlFragment{

	@Nullable 
	private ConditionSqlFragment conditionSqlFragment;

	@Nullable 
	private BoundSql conditionBoundSql;
	
	@Nullable 
	private SortSqlFragment sortSqlFragment;
	
	@Nullable 
	private BoundSql sortBoundSql;

	@Nullable 
	private Integer pageNum;
	
	@Nullable 
	private Integer pageSize;
	
	private boolean isPage;
	
	public DefaultSelectSqlFragment(String sql, Object ... params) {
		super(sql, params);
	}
	
	@Override
	public String getSqlFragment() {
		String sqlFragment = getBaseSql();
		if( existConditionSqlFragment() ) {
			sqlFragment = sqlFragment + " " + conditionSqlFragment.getSqlFragment();
		}
		if( existSortSqlFragment() ) {
			sqlFragment = sqlFragment + " " + sortSqlFragment.getSqlFragment();
		}
		return sqlFragment;
	}

	@Override
	public Object[] getParams() {
		if( !existConditionSqlFragment() ) {
			return getBaseParams();
		}
		return ArrayUtils.addAll(getBaseParams(), conditionBoundSql.getParams());
	}

	@Override
	public SelectSqlFragment setConditionSqlFragment(ConditionSqlFragment conditionSqlFragment) {
		this.conditionSqlFragment = conditionSqlFragment;
		this.conditionBoundSql = conditionSqlFragment.getBoundSql();
		return this;
	}

	@Override
	public ConditionSqlFragment getConditionSqlFragment() {
		return this.conditionSqlFragment;
	}

	@Override
	public SelectSqlFragment setSortSqlFragment(SortSqlFragment sortSqlFragment) {
		this.sortSqlFragment = sortSqlFragment;
		this.sortBoundSql = sortSqlFragment.getBoundSql();
		return this;
	}

	@Override
	public SortSqlFragment getSortSqlFragment() {
		return sortSqlFragment;
	}

	@Override
	public void startPage(int pageNum, int pageSize) {
		this.pageNum = pageNum;
		this.pageSize = pageSize;
		this.isPage = true;
	}
	
	@Override
	public void cancelPage() {
		this.isPage = false;
	}
	
	@Override
	public boolean isPage() {
		return this.isPage;
	}
	
	@Override
	public Integer getPageNum() {
		if( !isPage ) {
			return null;
		}
		return this.pageNum;
	}
	@Override
	public Integer getPageSize() {
		if( !isPage ) {
			return null;
		}
		return this.pageSize;
	}
	
}

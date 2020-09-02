/**
 * 
 */
package org.yelong.core.jdbc.sql.executable;

import org.apache.commons.lang3.ArrayUtils;
import org.yelong.core.annotation.Nullable;
import org.yelong.core.jdbc.dialect.Dialect;
import org.yelong.core.jdbc.sql.BoundSql;
import org.yelong.core.jdbc.sql.condition.ConditionSqlFragment;
import org.yelong.core.jdbc.sql.group.GroupSqlFragment;
import org.yelong.core.jdbc.sql.sort.SortSqlFragment;

/**
 * 抽象的查询SQL片段实现
 * 
 * @since 2.1
 */
public class AbstractSelectSqlFragment extends AbstractSqlFragmentExecutable implements SelectSqlFragment {

	@Nullable
	protected ConditionSqlFragment conditionSqlFragment;

	@Nullable
	protected BoundSql conditionBoundSql;

	@Nullable
	protected SortSqlFragment sortSqlFragment;

	@Nullable
	protected BoundSql sortBoundSql;

	@Nullable
	protected GroupSqlFragment groupSqlFragment;

	@Nullable
	protected BoundSql groupBoundSql;

	@Nullable
	protected Integer pageNum;

	@Nullable
	protected Integer pageSize;

	protected boolean isPage;

	public AbstractSelectSqlFragment(Dialect dialect, String sql, Object... params) {
		super(dialect, sql, params);
	}

	@Override
	public String getSqlFragment() {
		String sqlFragment = getBaseSql();
		if (existConditionSqlFragment()) {
			sqlFragment = sqlFragment + " " + conditionBoundSql.getSql();
		}
		if (existGroupSqlFragment()) {
			sqlFragment = sqlFragment + " " + groupBoundSql.getSql();
		}
		if (existSortSqlFragment()) {
			sqlFragment = sqlFragment + " " + sortBoundSql.getSql();
		}
		return sqlFragment;
	}

	@Override
	public Object[] getParams() {
		if (!existConditionSqlFragment()) {
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
	public SelectSqlFragment setGroupSqlFragment(GroupSqlFragment groupSqlFragment) {
		this.groupSqlFragment = groupSqlFragment;
		this.groupBoundSql = groupSqlFragment.getBoundSql();
		return this;
	}

	@Override
	public GroupSqlFragment getGroupSqlFragment() {
		return this.groupSqlFragment;
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
		if (!isPage) {
			return null;
		}
		return this.pageNum;
	}

	@Override
	public Integer getPageSize() {
		if (!isPage) {
			return null;
		}
		return this.pageSize;
	}

}

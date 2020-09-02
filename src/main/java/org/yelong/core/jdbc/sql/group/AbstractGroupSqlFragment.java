/**
 * 
 */
package org.yelong.core.jdbc.sql.group;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.yelong.core.jdbc.dialect.Dialect;
import org.yelong.core.jdbc.sql.AbstractSqlFragment;

/**
 * 抽象的分组SQL片段实现
 * 
 * @since 2.1
 */
public abstract class AbstractGroupSqlFragment extends AbstractSqlFragment implements GroupSqlFragment {

	protected boolean groupBy = true;

	protected static final String GROUP_BY_KEYWORD = "group by";

	protected final List<String> groupColumns = new ArrayList<>();

	public AbstractGroupSqlFragment(Dialect dialect) {
		super(dialect);
	}

	@Override
	public void addGroup(String... columns) {
		if (ArrayUtils.isNotEmpty(columns)) {
			for (String column : columns) {
				groupColumns.add(column);
			}
		}
	}

	@Override
	public boolean isEmpty() {
		return groupColumns.isEmpty();
	}

	@Override
	public boolean isGroupBy() {
		return groupBy;
	}

	@Override
	public void setGroupBy(boolean groupBy) {
		this.groupBy = groupBy;
	}

	protected String groupBy(String sql) {
		if (groupBy) {
			return " " + GROUP_BY_KEYWORD + " " + sql;
		}
		return sql;
	}

}

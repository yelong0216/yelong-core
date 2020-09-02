/**
 * 
 */
package org.yelong.core.jdbc.sql.defaults;

import java.util.stream.Collectors;

import org.yelong.core.jdbc.dialect.Dialect;
import org.yelong.core.jdbc.sql.SqlFragmentException;
import org.yelong.core.jdbc.sql.group.AbstractGroupSqlFragment;

/**
 * 默认的分组SQL片段实现
 * 
 * @since 2.1
 */
public class DefaultGroupSqlFragment extends AbstractGroupSqlFragment {

	public DefaultGroupSqlFragment(Dialect dialect) {
		super(dialect);
	}

	@Override
	public String getSqlFragment() {
		if (isEmpty()) {
			throw new SqlFragmentException("不存在分组SQL片段");
		}

		return groupBy(groupColumns.stream().collect(Collectors.joining(",")));
	}

}

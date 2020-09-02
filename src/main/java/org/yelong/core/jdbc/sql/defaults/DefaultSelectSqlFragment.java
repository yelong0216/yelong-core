/**
 * 
 */
package org.yelong.core.jdbc.sql.defaults;

import org.yelong.core.jdbc.dialect.Dialect;
import org.yelong.core.jdbc.sql.executable.AbstractSelectSqlFragment;

/**
 * 默认的 select SQL 实现
 * 
 * @since 2.1
 */
public class DefaultSelectSqlFragment extends AbstractSelectSqlFragment {

	public DefaultSelectSqlFragment(Dialect dialect, String sql, Object... params) {
		super(dialect, sql, params);
	}

}

package org.yelong.core.jdbc.sql.executable;

import org.yelong.core.jdbc.sql.attribute.AttributeSqlFragment;

/**
 * insert sql
 */
public interface InsertSqlFragment extends SqlFragmentExecutable {

	/**
	 * @return 获取属性sql
	 */
	AttributeSqlFragment getAttributeSqlFragment();

}
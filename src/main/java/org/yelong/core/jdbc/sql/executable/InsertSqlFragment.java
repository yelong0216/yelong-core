package org.yelong.core.jdbc.sql.executable;

import org.yelong.core.jdbc.sql.attribute.AttributeSqlFragment;

/**
 * insert sql
 * @author PengFei
 * @date 2020年1月20日上午11:52:49
 */
public interface InsertSqlFragment extends SqlFragmentExecutable{
	
	/**
	 * @return 获取属性sql
	 */
	AttributeSqlFragment getAttributeSqlFragment();
	
}
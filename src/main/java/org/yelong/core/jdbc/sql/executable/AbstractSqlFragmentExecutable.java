/**
 * 
 */
package org.yelong.core.jdbc.sql.executable;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.yelong.core.jdbc.sql.AbstractSqlFragment;

/**
 * 抽象的可执行的sql片段实现
 * @author PengFei
 */
public abstract class AbstractSqlFragmentExecutable extends AbstractSqlFragment implements SqlFragmentExecutable{

	private final String sql;
	
	private final Object [] params;
	
	public AbstractSqlFragmentExecutable() {
		this.sql = null;
		this.params = null;
	}
	
	public AbstractSqlFragmentExecutable(String sql , Object ... params) {
		if( null == sql ) {
			throw new NullPointerException("sql 不允许为空！");
		}
		this.sql = sql;
		this.params = params == null ? ArrayUtils.EMPTY_OBJECT_ARRAY : params;
	}

	public String getBaseSql() {
		return sql;
	}

	public Object[] getBaseParams() {
		return params;
	}
	
	public boolean existBaseSql() {
		return StringUtils.isNotEmpty(sql);
	}
	
}

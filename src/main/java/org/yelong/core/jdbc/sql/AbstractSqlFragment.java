/**
 * 
 */
package org.yelong.core.jdbc.sql;

import org.yelong.core.jdbc.dialect.Dialect;

/**
 * @author PengFei
 * @date 2020年1月20日上午11:12:38
 */
public abstract class AbstractSqlFragment {
	
	private Dialect dialect;
	
	public AbstractSqlFragment() {
	
	}
	
	public AbstractSqlFragment(Dialect dialect) {
		this.dialect = dialect;
	}
	
	public Dialect getDialect() {
		return dialect;
	}

	@SuppressWarnings("unchecked")
	public <S extends AbstractSqlFragment> S setDialect(Dialect dialect) {
		this.dialect = dialect;
		return (S) this;
	}

}

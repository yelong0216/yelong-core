/**
 * 
 */
package org.yelong.core.jdbc.sql;

import org.yelong.core.jdbc.dialect.Dialect;

/**
 * 抽象的sql片段
 * @author PengFei
 */
public abstract class AbstractSqlFragment implements SqlFragment{
	
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

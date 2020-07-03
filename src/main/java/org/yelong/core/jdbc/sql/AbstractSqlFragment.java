/**
 * 
 */
package org.yelong.core.jdbc.sql;

import java.util.Objects;

import org.yelong.core.jdbc.dialect.Dialect;

/**
 * 抽象的sql片段
 * 
 * @author PengFei
 */
public abstract class AbstractSqlFragment implements SqlFragment {

	private final Dialect dialect;

	public AbstractSqlFragment(final Dialect dialect) {
		this.dialect = Objects.requireNonNull(dialect);
	}

	public Dialect getDialect() {
		return dialect;
	}

	@Override
	public String toString() {
		return getSqlFragment();
	}

}

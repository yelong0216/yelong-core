/**
 * 
 */
package org.yelong.core.jdbc;

import org.yelong.core.jdbc.dialect.Dialect;
import org.yelong.core.jdbc.sql.factory.DefaultSqlFragmentFactory;
import org.yelong.core.jdbc.sql.factory.SqlFragmentFactory;

/**
 * @author PengFei
 * @date 2020年1月20日上午10:53:36
 */
public class JdbcConfiguration {

	private final Dialect dialect;
	
	private SqlFragmentFactory sqlFragmentFactory;
	
	public JdbcConfiguration(Dialect dialect) {
		this(dialect,new DefaultSqlFragmentFactory(dialect));
	}
	
	public JdbcConfiguration(Dialect dialect, SqlFragmentFactory sqlFragmentFactory) {
		super();
		this.dialect = dialect;
		this.sqlFragmentFactory = sqlFragmentFactory;
	}

	public Dialect getDialect() {
		return dialect;
	}

	public SqlFragmentFactory getSqlFragmentFactory() {
		return sqlFragmentFactory;
	}

	public void setSqlFragmentFactory(SqlFragmentFactory sqlFragmentFactory) {
		this.sqlFragmentFactory = sqlFragmentFactory;
	}
	
}

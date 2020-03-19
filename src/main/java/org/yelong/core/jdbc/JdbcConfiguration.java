/**
 * 
 */
package org.yelong.core.jdbc;

import org.yelong.core.annotation.test.NotImplemented;
import org.yelong.core.annotation.test.NotTest;
import org.yelong.core.jdbc.dialect.Dialect;
import org.yelong.core.jdbc.sql.factory.DefaultSqlFragmentFactory;
import org.yelong.core.jdbc.sql.factory.SqlFragmentFactory;

/**
 * Jdbc配置
 * @author PengFei
 */
@NotTest
@NotImplemented
public class JdbcConfiguration {

	private final Dialect dialect;
	
	private SqlFragmentFactory sqlFragmentFactory;
	
	public JdbcConfiguration(Dialect dialect) {
		this(dialect,new DefaultSqlFragmentFactory(dialect));
	}
	
	public JdbcConfiguration(Dialect dialect, SqlFragmentFactory sqlFragmentFactory) {
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

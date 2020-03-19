/**
 * 
 */
package org.yelong.core.jdbc.dialect;

import org.yelong.core.jdbc.dialect.impl.MySqlDialect;
import org.yelong.core.jdbc.dialect.impl.OracleDialect;

/**
 * 默认的数据库方言对应的实现
 * @author PengFei
 */
public enum Dialects {
	
	ORACLE(new OracleDialect()),
	MYSQL(new MySqlDialect());
	
	private final Dialect dialect;

	private Dialects(Dialect dialect) {
		this.dialect = dialect;
	}
	
	public Dialect getDialect() {
		return dialect;
	}
	
	public static Dialects valueOfByName(String name) {
		Dialects dialects = Dialects.valueOf(name.toUpperCase());
		if( null == dialects ) {
			throw new NullPointerException("不存在的方言："+name);
		}
		return dialects;
	}
	
}

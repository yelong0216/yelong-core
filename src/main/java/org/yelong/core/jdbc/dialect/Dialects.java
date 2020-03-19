/**
 * 
 */
package org.yelong.core.jdbc.dialect;

/**
 * @author PengFei
 * @date 2020年2月11日下午2:23:59
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

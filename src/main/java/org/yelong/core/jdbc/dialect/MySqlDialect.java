/**
 * 
 */
package org.yelong.core.jdbc.dialect;

/**
 * @author PengFei
 * @date 2020年2月11日下午2:15:11
 */
public class MySqlDialect extends AbstractDialect{
	
	@Override
	public String getBaseDeleteSql(String tableName, String tableAlias) {
		return "delete from " + tableName;
	}

	@Override
	public DialectType getDialectType() {
		return DialectType.MYSQL;
	}
	
}

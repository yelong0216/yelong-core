/**
 * 
 */
package org.yelong.core.jdbc.dialect;

/**
 * @author PengFei
 * @date 2020年1月10日上午9:00:10
 */
public interface Dialect {

	String getBaseDeleteSql(String tableName , String tableAlias);
	
	String getBaseSelectSql(String tableName , String tableAlias);
	
	String getBaseCountSql(String tableName , String tableAlias);
	
	DialectType getDialectType();
	
}

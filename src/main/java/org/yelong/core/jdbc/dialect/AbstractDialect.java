/**
 * 
 */
package org.yelong.core.jdbc.dialect;

/**
 * @author PengFei
 * @date 2020年1月20日下午3:03:35
 */
public abstract class AbstractDialect implements Dialect{
	
	@Override
	public String getBaseCountSql(String tableName, String tableAlias) {
		return " select count(1) from "+tableName + " " + tableAlias;
	}
	
	@Override
	public String getBaseSelectSql(String tableName, String tableAlias) {
		return " select " + tableAlias + ".* from "+tableName + " " + tableAlias;
	}
}

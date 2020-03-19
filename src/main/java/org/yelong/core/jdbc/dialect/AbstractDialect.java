/**
 * 
 */
package org.yelong.core.jdbc.dialect;

/**
 * 抽象的方言实现
 * @author PengFei
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

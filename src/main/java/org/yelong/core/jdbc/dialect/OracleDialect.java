/**
 * 
 */
package org.yelong.core.jdbc.dialect;

/**
 * @author PengFei
 * @date 2020年1月20日下午3:05:42
 */
public class OracleDialect extends AbstractDialect{
	
	@Override
	public String getBaseDeleteSql(String tableName, String tableAlias) {
		return "delete "+tableName + " " + tableAlias;
	}
	
	@Override
	public DialectType getDialectType() {
		return DialectType.ORACLE;
	}
	
}

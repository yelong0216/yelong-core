/**
 * 
 */
package org.yelong.core.jdbc.dialect.impl.oracle;

import org.yelong.core.jdbc.dialect.AbstractDialect;
import org.yelong.core.jdbc.dialect.DialectType;

/**
 * oracle 方言
 * 
 * @author PengFei
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

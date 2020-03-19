/**
 * 
 */
package org.yelong.core.jdbc.dialect.impl;

import org.yelong.core.jdbc.dialect.AbstractDialect;
import org.yelong.core.jdbc.dialect.DialectType;

/**
 * mysql 数据库方言
 * @author PengFei
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

/**
 * 
 */
package org.yelong.core.jdbc.dialect.impl;

import org.apache.commons.lang3.ArrayUtils;
import org.yelong.core.jdbc.dialect.AbstractDialect;
import org.yelong.core.jdbc.dialect.DialectType;
import org.yelong.core.jdbc.sql.BoundSql;

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
	
	@Override
	public BoundSql page(BoundSql boundSql, int pageNum, int pageSize) {
		if(pageSize < 0) {
			throw new IllegalArgumentException("页面大小不能小于0。pageSize:"+pageSize);
		}
		if( pageNum <= 0 ) {
			pageNum = 1;
		}
		String sql = boundSql.getSql();
		Object [] params = boundSql.getParams();
		String pageSql;
		Object [] pageParams;
		if( pageNum == 1 ) {
			pageSql = sql + " LIMIT ?";
			pageParams = ArrayUtils.addAll(params, pageSize);
		} else {
			pageSql = sql + " LIMIT ?,?";
			pageParams = ArrayUtils.addAll(params , (pageNum-1) * pageSize, pageSize);
		}
		return new BoundSql(pageSql, pageParams);
	}
}

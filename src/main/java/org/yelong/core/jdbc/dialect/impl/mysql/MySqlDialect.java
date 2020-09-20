/**
 * 
 */
package org.yelong.core.jdbc.dialect.impl.mysql;

import org.apache.commons.lang3.ArrayUtils;
import org.yelong.core.jdbc.BaseDataBaseOperation;
import org.yelong.core.jdbc.dialect.AbstractDialect;
import org.yelong.core.jdbc.dialect.DataType;
import org.yelong.core.jdbc.dialect.DialectType;
import org.yelong.core.jdbc.sql.BoundSql;
import org.yelong.core.jdbc.sql.ddl.DataDefinitionLanguage;
import org.yelong.core.jdbc.sql.function.DatabaseFunction;

/**
 * mysql 数据库方言
 */
public class MySqlDialect extends AbstractDialect {

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
		if (pageSize < 0) {
			throw new IllegalArgumentException("页面大小不能小于0。pageSize:" + pageSize);
		}
		if (pageNum <= 0) {
			pageNum = 1;
		}
		String sql = boundSql.getSql();
		Object[] params = boundSql.getParams();
		String pageSql;
		Object[] pageParams;
		if (pageNum == 1) {
			pageSql = sql + " LIMIT ?";
			pageParams = ArrayUtils.addAll(params, pageSize);
		} else {
			pageSql = sql + " LIMIT ?,?";
			pageParams = ArrayUtils.addAll(params, (pageNum - 1) * pageSize, pageSize);
		}
		return new BoundSql(pageSql, pageParams);
	}

	@Override
	public DataDefinitionLanguage createDataDefinitionLanguage(BaseDataBaseOperation db) {
		return new MySqlDataDefinitionLanguage(this, db);
	}

	@Override
	public DatabaseFunction createDatabaseFunction(BaseDataBaseOperation db) {
		return new MySqlDatabaseFunction(this, db);
	}

	@Override
	public DataType getDataType() {
		return MySqlDataType.INSTANCE;
	}

}

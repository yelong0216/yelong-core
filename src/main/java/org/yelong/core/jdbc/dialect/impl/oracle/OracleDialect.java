/**
 * 
 */
package org.yelong.core.jdbc.dialect.impl.oracle;

import org.yelong.core.jdbc.BaseDataBaseOperation;
import org.yelong.core.jdbc.dialect.AbstractDialect;
import org.yelong.core.jdbc.dialect.DialectType;
import org.yelong.core.jdbc.dialect.impl.mysql.MySqlDataDefinitionLanguage;
import org.yelong.core.jdbc.dialect.impl.mysql.MySqlDatabaseFunction;
import org.yelong.core.jdbc.sql.ddl.DataDefinitionLanguage;
import org.yelong.core.jdbc.sql.function.DatabaseFunction;

/**
 * oracle 方言
 */
public class OracleDialect extends AbstractDialect {

	@Override
	public String getBaseDeleteSql(String tableName, String tableAlias) {
		return "delete " + tableName + " " + tableAlias;
	}

	@Override
	public DialectType getDialectType() {
		return DialectType.ORACLE;
	}

	// 先用mysql的
	@Override
	public DataDefinitionLanguage createDataDefinitionLanguage(BaseDataBaseOperation db) {
		return new MySqlDataDefinitionLanguage(this, db);
	}

	// 先用mysql的
	@Override
	public DatabaseFunction createDatabaseFunction(BaseDataBaseOperation db) {
		return new MySqlDatabaseFunction(this, db);
	}

}

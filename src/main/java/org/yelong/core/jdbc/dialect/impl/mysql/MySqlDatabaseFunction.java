/**
 * 
 */
package org.yelong.core.jdbc.dialect.impl.mysql;

import org.yelong.core.jdbc.BaseDataBaseOperation;
import org.yelong.core.jdbc.dialect.Dialect;
import org.yelong.core.jdbc.sql.function.DatabaseFunction;

/**
 * mysql 数据库函数
 * 
 * @author PengFei
 * @since 1.1.0
 */
public class MySqlDatabaseFunction implements DatabaseFunction{

	protected final Dialect dialect;
	
	protected final BaseDataBaseOperation baseDataBaseOperation;
	
	public MySqlDatabaseFunction(Dialect dialect,BaseDataBaseOperation baseDataBaseOperation) {
		this.dialect = dialect;
		this.baseDataBaseOperation = baseDataBaseOperation;
	}
	
	@Override
	public String getCurrentDatabase() {
		return baseDataBaseOperation.selectSingleObject("select database()");
	}

	@Override
	public BaseDataBaseOperation getBaseDataBaseOperation() {
		return this.baseDataBaseOperation;
	}

	@Override
	public Dialect getDialect() {
		return this.dialect;
	}

}

/**
 * 
 */
package org.yelong.core.jdbc.dialect;

import org.yelong.core.jdbc.sql.factory.DefaultSqlFragmentFactory;
import org.yelong.core.jdbc.sql.factory.SqlFragmentFactory;

/**
 * 抽象的方言实现
 * 
 * @author PengFei
 */
public abstract class AbstractDialect implements Dialect{
	
	protected SqlFragmentFactory sqlFragmentFactory;
	
	@Override
	public String getBaseCountSql(String tableName, String tableAlias) {
		return " select count(1) from "+tableName + " " + tableAlias;
	}
	
	@Override
	public String getBaseSelectSql(String tableName, String tableAlias) {
		return " select " + tableAlias + ".* from "+tableName + " " + tableAlias;
	}
	
	@Override
	public SqlFragmentFactory getSqlFragmentFactory() {
		if( null == sqlFragmentFactory ) {
			sqlFragmentFactory = new DefaultSqlFragmentFactory(this);
		}
		return sqlFragmentFactory;
	}
	
}

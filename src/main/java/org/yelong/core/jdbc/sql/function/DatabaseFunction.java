/**
 * 
 */
package org.yelong.core.jdbc.sql.function;

import org.yelong.core.jdbc.BaseDataBaseOperation;
import org.yelong.core.jdbc.dialect.Dialect;

/**
 * 数据库方法、函数、功能的sql
 * 该类的实现类一般由{@link Dialect}来提供
 */
public interface DatabaseFunction {

	/**
	 * 获取当前的数据库名称
	 * @return 查询当前连接的数据库名称sql
	 */
	String getCurrentDatabase();
	
	/**
	 * @return 基础数据库操作
	 */
	BaseDataBaseOperation getBaseDataBaseOperation();
	
	/**
	 * @return 数据库方言
	 */
	Dialect getDialect();
	
}

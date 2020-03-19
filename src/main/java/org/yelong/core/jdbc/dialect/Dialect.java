/**
 * 
 */
package org.yelong.core.jdbc.dialect;

/**
 * 数据库方言
 * @author PengFei
 */
public interface Dialect {

	/**
	 * 获取基础的delete sql
	 * @param tableName 表名称
	 * @param tableAlias 表别名
	 * @return delete sql
	 */
	String getBaseDeleteSql(String tableName , String tableAlias);
	
	/**
	 * 获取基础的select sql
	 * @param tableName 表名称
	 * @param tableAlias 表别名
	 * @return select sql
	 */
	String getBaseSelectSql(String tableName , String tableAlias);
	
	/**
	 * 获取基础的 count sql
	 * @param tableName 表名称
	 * @param tableAlias 表别名
	 * @return count sql
	 */
	String getBaseCountSql(String tableName , String tableAlias);
	
	/**
	 * @return 方言类型
	 */
	DialectType getDialectType();
	
}

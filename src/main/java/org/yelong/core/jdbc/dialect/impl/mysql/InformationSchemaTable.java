/**
 * 
 */
package org.yelong.core.jdbc.dialect.impl.mysql;

import java.util.Map;
import java.util.Objects;

import org.yelong.core.jdbc.sql.ddl.Table;

/**
 * mysql 查询表时的列
 * 
 * @author PengFei
 * @since 1.1.0
 */
public class InformationSchemaTable {

	/**
	 * 表名称
	 */
	public static final String TABLE_NAME = "TABLE_NAME";

	/**
	 * 表所属的数据库名称
	 */
	public static final String TABLE_SCHEMA = "TABLE_SCHEMA";

	/**
	 * 创建表
	 * 
	 * @param record 查询的表的记录
	 * @return 表
	 */
	public static final Table createTable(Map<String, Object> record) {
		Objects.requireNonNull(record);
		String tableName = (String) record.get(TABLE_NAME);
		String database = (String) record.get(TABLE_SCHEMA);
		return new Table(database, tableName);
	}

}

/**
 * 
 */
package org.yelong.core.jdbc.dialect.impl.mysql;

import java.util.Map;
import java.util.Objects;

import org.yelong.core.jdbc.sql.ddl.Column;
import org.yelong.core.jdbc.sql.ddl.Table;

/**
 * mysql 的查询列时的列
 * 
 * @author PengFei
 * @since 1.0.0
 */
public class InformationSchemaColumn {

	public static final String TABLE_CATALOG = "TABLE_CATALOG";

	/**
	 * 表所属的数据库名称
	 */
	public static final String TABLE_SCHEMA = "TABLE_SCHEMA";

	/**
	 * 表名
	 */
	public static final String TABLE_NAME = "TABLE_NAME";

	/**
	 * 列名
	 */
	public static final String COLUMN_NAME = "COLUMN_NAME";

	public static final String ORDINAL_POSITION = "ORDINAL_POSITION";

	/**
	 * 列默认值
	 */
	public static final String COLUMN_DEFAULT = "COLUMN_DEFAULT";

	/**
	 * 是否允许为 null
	 */
	public static final String IS_NULLABLE = "IS_NULLABLE";

	/**
	 * 数据类型（varchar等）
	 */
	public static final String DATA_TYPE = "DATA_TYPE";

	public static final String CHARACTER_MAXIMUM_LENGTH = "CHARACTER_MAXIMUM_LENGTH";

	public static final String CHARACTER_OCTET_LENGTH = "CHARACTER_OCTET_LENGTH";

	public static final String NUMERIC_PRECISION = "NUMERIC_PRECISION";

	public static final String NUMERIC_SCALE = "NUMERIC_SCALE";

	public static final String CHARACTER_SET_NAME = "CHARACTER_SET_NAME";

	public static final String COLLATION_NAME = "COLLATION_NAME";

	/**
	 * 列类型（带有长度的。如varchar(32)）
	 */
	public static final String COLUMN_TYPE = "COLUMN_TYPE";

	/**
	 * 列的健
	 */
	public static final String COLUMN_KEY = "COLUMN_KEY";

	public static final String PRIVILEGES = "PRIVILEGES";

	/**
	 * 列描述
	 */
	public static final String COLUMN_COMMENT = "COLUMN_COMMENT";

	/**
	 * 创建
	 * 
	 * @param record 查询的列的记录
	 * @return 列
	 */
	public static final Column createColumn(Map<String, Object> record) {
		Objects.requireNonNull(record);
		String database = (String) record.get(TABLE_SCHEMA);
		String tableName = (String) record.get(TABLE_NAME);
		String columnName = (String) record.get(COLUMN_NAME);
		Column column = new Column(new Table(database, tableName), columnName);
		column.setAllowNull("YES".equals((String) record.get(IS_NULLABLE)));// 是否允许为空
		column.setComment((String) record.get(COLUMN_COMMENT));// 列描述
		column.setDefaultValue((String) record.get(COLUMN_DEFAULT));// 列默认值
		Object maxLength = record.get(CHARACTER_MAXIMUM_LENGTH);
		if (null != maxLength) {
			column.setLength(Long.valueOf(maxLength.toString()));// 长度
		}
		column.setPrimaryKey("PRI".equals((String) record.get(COLUMN_KEY)));// key
		column.setTypeName((String) record.get(DATA_TYPE));// 数据类型
		column.setAttributes(record);
		return column;
	}

}

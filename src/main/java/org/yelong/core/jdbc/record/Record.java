/**
 * 
 */
package org.yelong.core.jdbc.record;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;

/**
 * 数据库记录映射。代表数据库中一条记录
 * 
 * 所有 get... 方法遵循以下规则： 
 * 1、column与记录列名对应，则返回对应的列名值
 * 2、未找到column对应的列，将column转换至大写在尝试获取列值 
 * 3、仍为找到column对应的列，将column驼峰转换为下划线后尝试列值
 * 4、3中仍未找到column对应的列，将column驼峰转换为下划线且大写后尝试获取列值 
 * 5、均未找到则视为不存在此列
 * 
 * @author PengFei
 * @date 2019年12月22日上午12:58:05
 * @since 1.0
 */
public interface Record {

	/**
	 * @return columns map
	 */
	Map<String, Object> getColumns();

	/**
	 * @param columns value with map.
	 * @return this
	 */
	Record setColumns(Map<String, Object> columns);

	/**
	 * @param the Record object
	 * @return this
	 */
	Record setColumns(Record record);

	/**
	 * @param column the column name of the record
	 * @return this
	 */
	Record remove(String column);

	/**
	 * @param columns the column names of the record
	 * @return this
	 */
	Record remove(String... columns);

	/**
	 * 移除所有null的列 Remove columns if it is null
	 * 
	 * @return this
	 */
	Record removeNullValueColumns();

	/**
	 * 只留下 columns 列，其余列将被清除
	 * 
	 * @param columns the column names of the record
	 * @return this
	 */
	Record keep(String... columns);

	/**
	 * 只留下 column 列，其余列将被清除
	 * 
	 * @param column the column names of the record
	 * @return this
	 */
	Record keep(String column);

	/**
	 * 清空记录 Remove all columns of this record.
	 * 
	 * @return this
	 */
	Record clear();

	/**
	 * 设置列到记录中
	 * 
	 * @param column 列名
	 * @param value  这个列的值
	 * @return this
	 */
	Record set(String column, Object value);

	/**
	 * 获取 column 列对应的值。如果不存在则返回null
	 * 
	 * @param <T>
	 * @param column 列名
	 * @return 该列对应的值
	 */
	<T> T get(String column);

	/**
	 * 获取 column 列对应的值。如果不存在则返回 defaultValue
	 * 
	 * @param <T>
	 * @param column       列名
	 * @param defaultValue 不存在该列时返回的默认值
	 * @return column 列对应的值
	 */
	<T> T get(String column, T defaultValue);

	Object getObject(String column);

	/**
	 * @param column       列名
	 * @param defaultValue 不存在该列时返回的默认值
	 * @return column 列对应的值(Object 类型)
	 */
	Object getObject(String column, Object defaultValue);

	/**
	 * @param column 列名
	 * @return {@link #getObject(String)#toString()}
	 */
	String getString(String column);

	/**
	 * @param column 列名
	 * @return {@link #getNumber(String)#byteValue()}
	 */
	Byte getByte(String column);

	/**
	 * @param column 列名
	 * @return {@link #getNumber(String)#shortValue()}
	 */
	Short getShort(String column);

	/**
	 * @param column 列名
	 * @return column {@link #getNumber(String)#intValue()}
	 */
	Integer getInt(String column);

	/**
	 * @param column 列名
	 * @return {@link #getNumber(String)#longValue()}
	 */
	Long getLong(String column);

	/**
	 * @param column 列名
	 * @return {@link #getNumber(String)#floatValue()}
	 */
	Float getFloat(String column);

	/**
	 * @param column 列名
	 * @return {@link #getNumber(String)#doubleValue()}
	 */
	Double getDouble(String column);

	/**
	 * 获取String类型的值，值为1则为true，否则为false 值不存在返回null
	 * 
	 * @param column 列名
	 * @return {@link #getString(String)#equals("1")}
	 */
	Boolean getBoolean(String column);

	/**
	 * @param column 列名
	 * @return {@link (BigInteger)#getObject(String)}
	 */
	BigInteger getBigInteger(String column);

	/**
	 * @param column 列名
	 * @return {@link (BigDecimal)#getObject(String)}
	 */
	BigDecimal getBigDecimal(String column);

	/**
	 * @param column 列名
	 * @return {@link (Number)#getObject(String)}
	 */
	Number getNumber(String column);

	/**
	 * @param column 列名
	 * @return {@link (Date)#getObject(String)}
	 */
	Date getDate(String column);

	/**
	 * @param column 列名
	 * @return {@link (Time)#getObject(String)}
	 */
	Time getTime(String column);

	/**
	 * @param column 列名
	 * @return {@link (Timestamp)#getObject(String)}
	 */
	Timestamp getTimestamp(String column);

	/**
	 * 支持类型 blob
	 * 
	 * @param column 列名
	 * @return {@link (byte[])#getObject(String)}
	 */
	byte[] getBytes(String column);

	/**
	 * @return {column:value,...}
	 */
	String toString();

	/**
	 * @param o
	 * @return o == this || o.getColumns().equals(getColumns())
	 */
	boolean equals(Object o);

	/**
	 * @return getColumns().hashCode()
	 */
	int hashCode();

	/**
	 * @return 所有列名称
	 */
	String[] getColumnNames();

	/**
	 * @return 所有列值
	 */
	Object[] getColumnValues();
}

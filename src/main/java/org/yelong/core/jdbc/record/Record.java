/**
 * 
 */
package org.yelong.core.jdbc.record;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

import org.yelong.commons.util.map.MapWrapper;

/**
 * 记录，对应数据中的一条记录
 * 
 * @author PengFei
 * @since 1.1.0
 */
public class Record extends MapWrapper<String, Object> implements Serializable {

	private static final long serialVersionUID = 1474695239634500326L;

	public Record() {
		this(LinkedHashMap::new);
	}

	public Record(Supplier<Map<String, Object>> mapFactory) {
		this(mapFactory.get());
	}

	public Record(Map<String, Object> map) {
		super(map);
	}

	/**
	 * @param columns value with map.
	 * @return this
	 */
	public Record setColumns(Map<String, Object> columns) {
		super.putAll(columns);
		return this;
	}

	/**
	 * @param column the column name of the record
	 * @return this
	 */
	public Record remove(String column) {
		super.remove(column);
		return this;
	}

	/**
	 * @param columns the column names of the record
	 * @return this
	 */
	public Record remove(String... columns) {
		if (null != columns) {
			for (String column : columns) {
				remove(column);
			}
		}
		return this;
	}

	/**
	 * 移除所有null的列 Remove columns if it is null
	 * 
	 * @return this
	 */
	public Record removeNullValueColumns() {
		for (java.util.Iterator<Entry<String, Object>> it = super.entrySet().iterator(); it.hasNext();) {
			Entry<String, Object> e = it.next();
			if (e.getValue() == null) {
				it.remove();
			}
		}
		return this;
	}

	/**
	 * 只留下 columns 列，其余列将被清除
	 * 
	 * @param columns the column names of the record
	 * @return this
	 */
	public Record keep(String... columns) {
		if (columns != null && columns.length > 0) {
			Map<String, Object> newColumns = new HashMap<String, Object>(columns.length); // getConfig().containerFactory.getColumnsMap();
			for (String c : columns)
				if (super.containsKey(c)) // prevent put null value to the newColumns
					newColumns.put(c, super.get(c));

			super.clear();
			super.putAll(newColumns);
		} else
			super.clear();
		return this;
	}

	/**
	 * 只留下 column 列，其余列将被清除
	 * 
	 * @param column the column names of the record
	 * @return this
	 */
	public Record keep(String column) {
		if (super.containsKey(column)) { // prevent put null value to the newColumns
			Object keepIt = super.get(column);
			super.clear();
			super.put(column, keepIt);
		} else
			super.clear();
		return this;
	}

	/**
	 * 设置列到记录中
	 * 
	 * @param column 列名
	 * @param value  这个列的值
	 * @return this
	 */
	public Record set(String column, Object value) {
		super.put(column, value);
		return this;
	}

	/**
	 * 获取 column 列对应的值。如果不存在则返回null
	 * 
	 * @param <T>
	 * @param column 列名
	 * @return 该列对应的值
	 */
	@SuppressWarnings("unchecked")
	public <T> T get(String column) {
		return (T) super.get(column);
	}

	/**
	 * 获取 column 列对应的值。如果不存在则返回 defaultValue
	 * 
	 * @param <T>
	 * @param column       列名
	 * @param defaultValue 不存在该列时返回的默认值
	 * @return column 列对应的值
	 */
	public <T> T get(String column, T defaultValue) {
		T value = get(column);
		return value != null ? value : defaultValue;
	}

	/**
	 * @param column 列名
	 * @return column 列对应的值(Object 类型)
	 */
	public Object getObject(String column) {
		return get(column);
	}

	/**
	 * @param column       列名
	 * @param defaultValue 不存在该列时返回的默认值
	 * @return column 列对应的值(Object 类型)
	 */
	public Object getObject(String column, Object defaultValue) {
		Object value = getObject(column);
		return value != null ? value : defaultValue;
	}

	/**
	 * @param column 列名
	 * @return {@link #getObject(String)#toString()}
	 */
	public String getString(String column) {
		Object value = getObject(column);
		return value != null ? value.toString() : null;
	}

	/**
	 * @param column 列名
	 * @return {@link #getNumber(String)#intValue()}
	 */
	public Integer getInt(String column) {
		Number number = getNumber(column);
		return number != null ? number.intValue() : null;
	}

	/**
	 * @param column 列名
	 * @return {@link #getNumber(String)#longValue()}
	 */
	public Long getLong(String column) {
		Number number = getNumber(column);
		return number != null ? number.longValue() : null;
	}

	/**
	 * @param column 列名
	 * @return {@link (BigInteger)#getObject(String)}
	 */
	public BigInteger getBigInteger(String column) {
		return (BigInteger) getObject(column);
	}

	/**
	 * @param column 列名
	 * @return {@link (Date)#getObject(String)}
	 */
	public Date getDate(String column) {
		return (Date) getObject(column);
	}

	/**
	 * @param column 列名
	 * @return {@link (Time)#getObject(String)}
	 */
	public Time getTime(String column) {
		return (Time) getObject(column);
	}

	/**
	 * @param column 列名
	 * @return {@link (Timestamp)#getObject(String)}
	 */
	public Timestamp getTimestamp(String column) {
		return (Timestamp) getObject(column);
	}

	/**
	 * @param column 列名
	 * @return {@link #getNumber(String)#doubleValue()}
	 */
	public Double getDouble(String column) {
		Number number = getNumber(column);
		return number != null ? number.doubleValue() : null;
	}

	/**
	 * @param column 列名
	 * @return {@link #getNumber(String)#floatValue()}
	 */
	public Float getFloat(String column) {
		Number number = getNumber(column);
		return number != null ? number.floatValue() : null;
	}

	/**
	 * @param column 列名
	 * @return {@link #getNumber(String)#shortValue()}
	 */
	public Short getShort(String column) {
		Number number = getNumber(column);
		return number != null ? number.shortValue() : null;
	}

	/**
	 * @param column 列名
	 * @return {@link #getNumber(String)#byteValue()}
	 */
	public Byte getByte(String column) {
		Number number = getNumber(column);
		return number != null ? number.byteValue() : null;
	}

	/**
	 * 获取String类型的值，值为1则为true，否则为false 值不存在返回null
	 * 
	 * @param column 列名
	 * @return {@link #getString(String)#equals("1")}
	 */
	public Boolean getBoolean(String column) {
		String value = getString(column);
		return value != null ? value.equals("1") : null;
	}

	/**
	 * @param column 列名
	 * @return {@link (BigDecimal)#getObject(String)}
	 */
	public BigDecimal getBigDecimal(String column) {
		return (BigDecimal) getObject(column);
	}

	/**
	 * 支持类型 blob
	 * 
	 * @param column 列名
	 * @return {@link (byte[])#getObject(String)}
	 */
	public byte[] getBytes(String column) {
		return (byte[]) getObject(column);
	}

	/**
	 * @param column 列名
	 * @return {@link (Number)#getObject(String)}
	 */
	public Number getNumber(String column) {
		return (Number) getObject(column);
	}

	/**
	 * @return 所有列名称
	 */
	public String[] getColumnNames() {
		Set<String> columnNameSet = super.keySet();
		return columnNameSet.toArray(new String[columnNameSet.size()]);
	}

	/**
	 * @return 所有列值
	 */
	public Object[] getColumnValues() {
		Collection<Object> columnValueCollection = super.values();
		return columnValueCollection.toArray(new Object[columnValueCollection.size()]);
	}

	/**
	 * @param obj
	 * @return obj == this || obj.getColumns().equals(getColumns())
	 */
	public boolean equals(Object obj) {
		if (!(obj instanceof Record)) {
			return false;
		}
		return obj == this || ((Record) obj).getMap().equals(this.getMap());
	}

	@Override
	public int hashCode() {
		return getMap().hashCode();
	}

}

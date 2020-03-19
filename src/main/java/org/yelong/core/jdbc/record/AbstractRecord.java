/**
 * 
 */
package org.yelong.core.jdbc.record;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.yelong.commons.lang.StringUtils;

/**
 * @author PengFei
 * @date 2019年12月22日上午1:23:36
 * @since 1.0
 */
public abstract class AbstractRecord implements Record{

	private final Map<String, Object> columns = new LinkedHashMap<String, Object>();
	
	@Override
	public Map<String, Object> getColumns() {
		return Collections.unmodifiableMap(this.columns);
	}

	@Override
	public Record setColumns(Map<String, Object> columns) {
		this.columns.putAll(columns);
		return this;
	}

	@Override
	public Record setColumns(Record record) {
		this.columns.putAll(record.getColumns());
		return this;
	}

	@Override
	public Record remove(String column) {
		this.columns.remove(column);
		return this;
	}

	@Override
	public Record remove(String... columns) {
		if( null != columns ) {
			for (String column : columns) {
				remove(column);
			}
		}
		return this;
	}

	@Override
	public Record removeNullValueColumns() {
		for (java.util.Iterator<Entry<String, Object>> it = this.columns.entrySet().iterator(); it.hasNext();) {
			Entry<String, Object> e = it.next();
			if (e.getValue() == null) {
				it.remove();
			}
		}
		return this;
	}

	@Override
	public Record keep(String... columns) {
		if (columns != null && columns.length > 0) {
			Map<String, Object> newColumns = new HashMap<String, Object>(columns.length);	// getConfig().containerFactory.getColumnsMap();
			for (String c : columns)
				if (this.columns.containsKey(c))	// prevent put null value to the newColumns
					newColumns.put(c, this.columns.get(c));
			
			this.columns.clear();
			this.columns.putAll(newColumns);
		}
		else
			this.getColumns().clear();
		return this;
	}

	@Override
	public Record keep(String column) {
		if (this.columns.containsKey(column)) {	// prevent put null value to the newColumns
			Object keepIt = this.columns.get(column);
			this.columns.clear();
			this.columns.put(column, keepIt);
		}
		else
			this.columns.clear();
		return this;
	}

	@Override
	public Record clear() {
		this.columns.clear();
		return this;
	}

	@Override
	public Record set(String column, Object value) {
		this.columns.put(column, value);
		return this;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T get(String column) {
		Object value = this.columns.get(column);
		if( null == value ) {//大写获取
			value = this.columns.get(column.toUpperCase());
		}
		if( null == value ) {//驼峰转下划线
			value = this.columns.get(StringUtils.camelCaseToUnderscore(column));
		}
		if( null == value ) {//驼峰转下划线且大写
			value = this.columns.get(StringUtils.camelCaseToUnderscore(column).toUpperCase());
		}
		return (T) value;
	}

	@Override
	public <T> T get(String column, T defaultValue) {
		T value = get(column);
		return value != null ? value : defaultValue;
	}

	@Override
	public Object getObject(String column) {
		return get(column);
	}

	@Override
	public Object getObject(String column, Object defaultValue) {
		Object value = getObject(column);
		return value != null ? value : defaultValue;
	}

	@Override
	public String getString(String column) {
		Object value = getObject(column);
		return value != null ? value.toString() : null;
	}

	@Override
	public Integer getInt(String column) {
		Number number = getNumber(column);
		return number != null ? number.intValue() : null;
	}

	@Override
	public Long getLong(String column) {
		Number number = getNumber(column);
		return number != null ? number.longValue() : null;
	}

	@Override
	public BigInteger getBigInteger(String column) {
		return (BigInteger) getObject(column);
	}

	@Override
	public Date getDate(String column) {
		return (Date) getObject(column);
	}

	@Override
	public Time getTime(String column) {
		return (Time) getObject(column);
	}

	@Override
	public Timestamp getTimestamp(String column) {
		return (Timestamp) getObject(column);
	}

	@Override
	public Double getDouble(String column) {
		Number number = getNumber(column);
		return number != null ? number.doubleValue() : null;
	}

	@Override
	public Float getFloat(String column) {
		Number number = getNumber(column);
		return number != null ? number.floatValue() : null;
	}

	@Override
	public Short getShort(String column) {
		Number number = getNumber(column);
		return number != null ? number.shortValue() : null;
	}

	@Override
	public Byte getByte(String column) {
		Number number = getNumber(column);
		return number != null ? number.byteValue() : null;
	}

	@Override
	public Boolean getBoolean(String column) {
		String value = getString(column);
		return value != null ? value.equals("1") : null;
	}

	@Override
	public BigDecimal getBigDecimal(String column) {
		return (BigDecimal) getObject(column);
	}

	@Override
	public byte[] getBytes(String column) {
		return (byte[]) getObject(column);
	}

	@Override
	public Number getNumber(String column) {
		return (Number) getObject(column);
	}

	@Override
	public String[] getColumnNames() {
		Set<String> columnNameSet = getColumns().keySet();
		return columnNameSet.toArray(new String[columnNameSet.size()]);
	}

	@Override
	public Object[] getColumnValues() {
		Collection<Object> columnValueCollection = getColumns().values();
		return columnValueCollection.toArray(new Object[columnValueCollection.size()]);
	}

}

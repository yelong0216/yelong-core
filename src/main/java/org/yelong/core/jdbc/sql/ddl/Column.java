package org.yelong.core.jdbc.sql.ddl;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.yelong.commons.lang.Strings;

/**
 * 列
 * 
 * @author PengFei
 * @since 1.1.0
 */
public class Column {

	private Table table;

	private final String name;

	private String typeName;

	private Long length;

	private Object defaultValue;

	private String comment;

	private boolean primaryKey = false;

	private boolean allowNull = true;

	private Map<String, Object> attributes = new HashMap<>();

	/**
	 * @param tableName 表明
	 * @param name      列名
	 */
	public Column(String tableName, String name) {
		this(new Table(tableName), name);
	}

	/**
	 * @param table 表
	 * @param name  列
	 */
	public Column(Table table, String name) {
		this.table = Objects.requireNonNull(table);
		this.name = Strings.requireNonBlank(name);
	}

	public String getName() {
		return name;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public Long getLength() {
		return length;
	}

	public void setLength(Long length) {
		this.length = length;
	}

	public Object getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(Object defaultValue) {
		this.defaultValue = defaultValue;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getTableName() {
		return table.getName();
	}

	public boolean isPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(boolean primaryKey) {
		this.primaryKey = primaryKey;
	}

	public boolean isAllowNull() {
		return allowNull;
	}

	public void setAllowNull(boolean allowNull) {
		this.allowNull = allowNull;
	}

	public Table getTable() {
		return table;
	}

	public void setTable(Table table) {
		this.table = table;
	}

	public void addAttributes(Map<String, Object> attributes) {
		this.attributes.putAll(attributes);
	}

	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = Objects.requireNonNull(attributes);
	}

	public void addAttribute(String attrName, Object attrValue) {
		this.attributes.put(attrName, attrValue);
	}

	public void removeAttribute(String attrName) {
		this.attributes.remove(attrName);
	}

	public Object getAttribute(String attrName) {
		return this.attributes.get(attrName);
	}

	public Map<String, Object> getAttributes() {
		return this.attributes;
	}

	@Override
	public String toString() {
		return name;
	}

}

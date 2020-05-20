package org.yelong.core.jdbc.sql.ddl;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import org.yelong.commons.lang.Strings;

/**
 * 表
 * 
 * @author PengFei
 * @since 1.1.0
 */
public class Table {

	private Database dataBase;

	private final String name;

	private String comment;

	private String charset;

	private final Map<String,Column> columns = new LinkedHashMap<>();

	public Table(String name) {
		this((Database)null,name);
	}

	public Table(String database , String name) {
		this(new Database(database),name);
	}

	public Table(Database database , String name) {
		this.dataBase = database;
		this.name = Strings.requireNonBlank(name);
	}

	public String getName() {
		return name;
	}

	public Database getDataBase() {
		return dataBase;
	}

	public void setDataBase(Database dataBase) {
		this.dataBase = dataBase;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Collection<Column> getColumns() {
		return columns.values();
	}

	public Column getColumn(String columnName) {
		return this.columns.get(columnName);
	}

	public Table addColumn(Column column) {
		this.columns.put(column.getName(), column);
		return this;	
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public String getDataBaseName() {
		return null == dataBase ? null : dataBase.getName();
	}

	@Override
	public String toString() {
		return name;
	}

}

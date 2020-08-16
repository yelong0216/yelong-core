/**
 * 
 */
package org.yelong.core.model.map.support;

import java.util.ArrayList;
import java.util.List;

import org.yelong.core.jdbc.sql.ddl.Column;
import org.yelong.core.jdbc.sql.ddl.DataDefinitionLanguage;
import org.yelong.core.jdbc.sql.ddl.Table;
import org.yelong.core.jdbc.sql.function.DatabaseFunction;
import org.yelong.core.model.map.MapModelAndTable;
import org.yelong.core.model.map.MapModelFieldAndColumn;
import org.yelong.core.model.map.MapModelFieldAndColumnGetStrategy;

/**
 * Map模型字段列获取策略默认实现
 * 
 * @since 1.1
 */
public class DefaultMapModelFieldAndColumnGetStrategy implements MapModelFieldAndColumnGetStrategy {

	private DataDefinitionLanguage dataDefinitionLanguage;

	private DatabaseFunction dataBaseFunction;

	public DefaultMapModelFieldAndColumnGetStrategy(DataDefinitionLanguage dataDefinitionLanguage,
			DatabaseFunction dataBaseFunction) {
		this.dataDefinitionLanguage = dataDefinitionLanguage;
		this.dataBaseFunction = dataBaseFunction;
	}

	@Override
	public List<MapModelFieldAndColumn> get(MapModelAndTable mapModelAndTable) {
		String database = dataBaseFunction.getCurrentDatabase();
		List<Column> columns = dataDefinitionLanguage.queryColumn(new Table(database, mapModelAndTable.getTableName()));
		List<MapModelFieldAndColumn> fieldAndColumns = new ArrayList<MapModelFieldAndColumn>(columns.size());
		for (Column column : columns) {
			String columnName = column.getName();
			DefaultMapModelFieldAndColumn fieldAndColumn = new DefaultMapModelFieldAndColumn(columnName);
			fieldAndColumn.setAllowNull(column.isAllowNull());
			fieldAndColumn.setSelect(true);
			fieldAndColumn.setDesc(column.getComment());
			fieldAndColumn.setExtend(false);
			fieldAndColumn.setJdbcType(column.getTypeName());
			fieldAndColumn.setMaxLength(column.getLength() == null ? Long.MAX_VALUE : column.getLength());
			fieldAndColumn.setMinLength(0L);
			fieldAndColumn.setPrimaryKey(column.isPrimaryKey());
			fieldAndColumns.add(fieldAndColumn);
		}
		return fieldAndColumns;
	}

	public DataDefinitionLanguage getDataDefinitionLanguage() {
		return dataDefinitionLanguage;
	}

	public DatabaseFunction getDataBaseFunction() {
		return dataBaseFunction;
	}

}

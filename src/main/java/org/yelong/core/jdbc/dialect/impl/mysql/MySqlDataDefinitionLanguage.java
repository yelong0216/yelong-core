/**
 * 
 */
package org.yelong.core.jdbc.dialect.impl.mysql;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.yelong.commons.lang.Strings;
import org.yelong.core.jdbc.BaseDataBaseOperation;
import org.yelong.core.jdbc.dialect.Dialect;
import org.yelong.core.jdbc.sql.BoundSql;
import org.yelong.core.jdbc.sql.SpliceSqlUtils;
import org.yelong.core.jdbc.sql.condition.combination.CombinationConditionSqlFragment;
import org.yelong.core.jdbc.sql.ddl.Column;
import org.yelong.core.jdbc.sql.ddl.DataDefinitionLanguage;
import org.yelong.core.jdbc.sql.ddl.Database;
import org.yelong.core.jdbc.sql.ddl.Table;

/**
 * mysql 数据库定义语言
 * 
 * @since 1.1
 */
public class MySqlDataDefinitionLanguage implements DataDefinitionLanguage {

	protected final Dialect dialect;

	protected final BaseDataBaseOperation baseDataBaseOperation;

	protected static final String BASE_QUERY_TABLE_SQL = "select * from information_schema.tables";

	protected static final String BASE_QUERY_COLUMN_SQL = "SELECT * FROM information_schema.columns";

	public MySqlDataDefinitionLanguage(Dialect dialect, BaseDataBaseOperation baseDataBaseOperation) {
		this.dialect = dialect;
		this.baseDataBaseOperation = baseDataBaseOperation;
	}

	@Override
	public Integer createTable(Table table, boolean override) {
		Collection<Column> columns = table.getColumns();
		if (CollectionUtils.isEmpty(columns)) {
			throw new RuntimeException("1113 - A table must have at least 1 column");
		}
		try {
			// 删除表
			if (override) {
				dropTable(table);
			}
		} catch (Exception e) {

		}
		StringBuilder sql = new StringBuilder("create table ");
		sql.append(table.getName());
		sql.append("(");
		String columnSql = columns.stream().map(x -> generateColumnSql(x)).collect(Collectors.joining(","));
		sql.append(columnSql);
		sql.append(")");
		String charset = table.getCharset();
		if (StringUtils.isNotBlank(charset)) {
			sql.append("DEFAULT CHARSET = ");
			sql.append(charset);
		}
		return _exce(sql.toString());
	}

	protected String generateColumnSql(Column column) {
		List<String> sqlFragment = new ArrayList<>();
		// 列名
		sqlFragment.add(column.getName());
		
		String typeName = column.getTypeName();
		Strings.requireNonBlank(typeName,column+"列没有指定数据类型！");
		
		// 列类型
		sqlFragment.add(typeName);
		// 列长度
		if (null != column.getLength()) {
			sqlFragment.add("(" + column.getLength() + ")");
		}
		// 默认值
		if (null != column.getDefaultValue()) {
			sqlFragment.add("DEFAULT '" + column.getDefaultValue() + "'");
		}
		// 注释
		if (null != column.getComment()) {
			sqlFragment.add("COMMENT '" + column.getComment() + "'");
		}
		if (column.isPrimaryKey()) {
			sqlFragment.add("primary key");
		} else {
			// 列是否允许为空
			if (!column.isAllowNull()) {
				sqlFragment.add("not null");
			} else {
				sqlFragment.add("null");
			}
		}
		return SpliceSqlUtils.spliceSqlFragment(sqlFragment.toArray(new String[] {}));
	}

	@Override
	public Integer renameTable(Table table, String newName) {
		return _exce("rename table " + table.getName() + " to " + newName);
	}

	@Override
	public Integer dropTable(Table table) {
		return _exce("drop table if exists " + table.getName());
	}

	@Override
	public List<Table> queryTable(Database database) {
		return _queryTable(database, null);
	}

	@Override
	public Table queryTable(Database database, String tableName) {
		List<Table> tables = _queryTable(database, tableName);
		if (CollectionUtils.isEmpty(tables)) {
			return null;
		} else {
			return tables.get(0);
		}
	}

	protected List<Table> _queryTable(Database database, String tableName) {
		List<String> sqlFragment = new ArrayList<>();
		sqlFragment.add(BASE_QUERY_TABLE_SQL);
		boolean isWhere = false;
		if (null != database) {
			sqlFragment.add("where");
			sqlFragment.add("TABLE_SCHEMA = '" + database.getName() + "'");
			isWhere = true;
		}
		if (null != tableName) {
			if (!isWhere) {
				sqlFragment.add("where");
				isWhere = true;
			} else {
				sqlFragment.add("and");
			}
			sqlFragment.add("TABLE_NAME = '" + tableName + "'");
		}

		List<Map<String, Object>> result = baseDataBaseOperation
				.select(SpliceSqlUtils.spliceSqlFragment(sqlFragment.toArray(new String[] {})));
		if (CollectionUtils.isEmpty(result)) {
			return Collections.emptyList();
		}
		List<Table> talbes = new ArrayList<Table>();
		for (Map<String, Object> map : result) {
			Table table = InformationSchemaTable.createTable(map);
			talbes.add(table);
		}
		return talbes;
	}

	@Override
	public Integer addColumn(Column column) {
		Strings.requireNonBlank(column.getTypeName(), "'column.typeName'不允许为空！");
		List<String> sqlFragment = new ArrayList<>();
		sqlFragment.add("alter table");
		// 数据库名与表名
		if (null != column.getTable().getDataBaseName()) {
			sqlFragment.add(column.getTable().getDataBaseName() + "." + column.getTableName());
		} else {
			sqlFragment.add(column.getTableName());
		}
		sqlFragment.add("add column");
		// 列名
		sqlFragment.add(column.getName());
		// 列类型
		sqlFragment.add(column.getTypeName());
		// 列长度
		if (null != column.getLength()) {
			sqlFragment.add("(" + column.getLength() + ")");
		}
		// 默认值
		if (null != column.getDefaultValue()) {
			sqlFragment.add("DEFAULT '" + column.getDefaultValue() + "'");
		}
		// 注释
		if (null != column.getComment()) {
			sqlFragment.add("COMMENT '" + column.getComment() + "'");
		}
		if (column.isPrimaryKey()) {
			sqlFragment.add("primary key first");
		} else {
			// 列是否允许为空
			if (!column.isAllowNull()) {
				sqlFragment.add("not null");
			} else {
				sqlFragment.add("null");
			}
		}
		return _exce(sqlFragment);
	}

	@Override
	public Integer dropColumn(Column column) {
		List<String> sqlFragment = new ArrayList<>();
		sqlFragment.add("alter table");
		String tableName = column.getTableName();
		String columnName = column.getName();
		Database database = column.getTable().getDataBase();
		// 数据库名与表名
		if (null != database) {
			sqlFragment.add(database.getName() + "." + tableName);
		} else {
			sqlFragment.add(tableName);
		}
		sqlFragment.add("drop column");
		sqlFragment.add(columnName);
		return _exce(sqlFragment);
	}

	@Override
	public Integer modifyColumn(Column sourceColumn, Column column) {
		List<String> sqlFragment = new ArrayList<>();
		sqlFragment.add("alter table");
		// 数据名
		if (null != column.getTable().getDataBaseName()) {
			sqlFragment.add(column.getTable().getDataBaseName() + "." + column.getTableName());
		} else {
			sqlFragment.add(column.getTableName());
		}
		sqlFragment.add("change column");
		// 源列名
		sqlFragment.add(sourceColumn.getName());
		// 列名
		sqlFragment.add(column.getName());
		// 列类型
		sqlFragment.add(column.getTypeName());
		// 列长度
		if (null != column.getLength()) {
			sqlFragment.add("(" + column.getLength() + ")");
		}
		// 默认值
		if (null != column.getDefaultValue()) {
			sqlFragment.add("DEFAULT '" + column.getDefaultValue() + "'");
		} else {
			sqlFragment.add("null");
		}
		// 注释
		if (null != column.getComment()) {
			sqlFragment.add("COMMENT '" + column.getComment() + "'");
		}
		if (column.isPrimaryKey()) {
			sqlFragment.add("primary key first");
		} else {
			// 列是否允许为空
			if (!column.isAllowNull()) {
				sqlFragment.add("not null");
			}
		}
		return _exce(sqlFragment);
	}

	@Override
	public List<Column> queryColumn(Table table) {
		return _queryColumn(table, null);
	}

	@Override
	public Column getColumn(Table table, String columnName) {
		List<Column> columns = _queryColumn(table, columnName);
		if (CollectionUtils.isEmpty(columns)) {
			return null;
		} else {
			return columns.get(0);
		}
	}

	@Override
	public List<Column> queryPrimaryKeyColumn(Table table) {
		CombinationConditionSqlFragment combinationConditionSqlFragment = dialect.getSqlFragmentFactory()
				.createCombinationConditionSqlFragment();
		if (StringUtils.isNotBlank(table.getDataBaseName())) {
			combinationConditionSqlFragment.and("TABLE_SCHEMA", "=", table.getDataBaseName());
		}
		combinationConditionSqlFragment.and("TABLE_NAME", "=", table.getName());// 表名
		combinationConditionSqlFragment.and("COLUMN_KEY", "=", "PRI");// 主键
		BoundSql boundSql = combinationConditionSqlFragment.getBoundSql();
		return _queryColumn(new BoundSql(BASE_QUERY_COLUMN_SQL + " " + boundSql.getSql(), boundSql.getParams()));
	}

	protected List<Column> _queryColumn(Table table, String columnName) {
		List<String> sqlFragment = new ArrayList<>();
		sqlFragment.add(BASE_QUERY_COLUMN_SQL);
		boolean isWhere = false;
		if (null != table.getDataBase()) {
			sqlFragment.add("where");
			sqlFragment.add("TABLE_SCHEMA = '" + table.getDataBase().getName() + "'");
			isWhere = true;
		}
		if (null != table.getName()) {
			if (!isWhere) {
				sqlFragment.add("where");
				isWhere = true;
			} else {
				sqlFragment.add("and");
			}
			sqlFragment.add("TABLE_NAME = '" + table.getName() + "'");
		}
		if (null != columnName) {
			if (!isWhere) {
				sqlFragment.add("where");
				isWhere = true;
			} else {
				sqlFragment.add("and");
			}
			sqlFragment.add("COLUMN_NAME = '" + columnName + "'");
		}
		return _queryColumn(new BoundSql(SpliceSqlUtils.spliceSqlFragment(sqlFragment.toArray(new String[] {})), null));
	}

	protected List<Column> _queryColumn(BoundSql boundSql) {
		List<Map<String, Object>> records = baseDataBaseOperation.select(boundSql.getSql(), boundSql.getParams());
		if (CollectionUtils.isEmpty(records)) {
			return Collections.emptyList();
		}
		List<Column> columns = new ArrayList<Column>(records.size());
		records.forEach(x -> {
			columns.add(InformationSchemaColumn.createColumn(x));
		});
		return columns;
	}

	@Override
	public Integer setPrimaryKey(Table table, String columnName) {
		return _modifyPrimaryKey(table, new String[] { columnName });
	}

	@Override
	public Integer setPrimaryKey(Table table, String[] columnNames) {
		return _modifyPrimaryKey(table, columnNames);
	}

	@Override
	public Integer dropPrimaryKey(Table table) {
		return _modifyPrimaryKey(table, null);
	}

	@Override
	public Integer dropPrimaryKey(Table table, String columnName) {
		List<String> primaryKeyColumns = queryPrimaryKeyColumn(table).stream().map(Column::getName)
				.collect(Collectors.toList());
		primaryKeyColumns.remove(columnName);
		return _modifyPrimaryKey(table, primaryKeyColumns.toArray(new String[] {}));
	}

	@Override
	public Integer dropPrimaryKey(Table table, String[] columnNames) {
		List<String> primaryKeyColumns = queryPrimaryKeyColumn(table).stream().map(Column::getName)
				.collect(Collectors.toList());
		for (String column : primaryKeyColumns) {
			primaryKeyColumns.remove(column);
		}
		return _modifyPrimaryKey(table, primaryKeyColumns.toArray(new String[] {}));
	}

	/**
	 * 清空主键，并重新对列设置主键
	 * 
	 * @param table       表
	 * @param columnNames 需要设置为主键的列名称。如果为空和清空主键效果相同
	 * @return 受影响的行
	 */
	protected Integer _modifyPrimaryKey(Table table, String[] columnNames) {
		List<String> sqlFragment = new ArrayList<String>();
		String tableName = getSqlTableName(table);
		sqlFragment.add("ALTER TABLE");
		sqlFragment.add(tableName);
		sqlFragment.add("DROP PRIMARY KEY");// 删除主键
		if (null != columnNames && columnNames.length > 0) {
			sqlFragment.add(
					",ADD PRIMARY KEY (" + Arrays.asList(columnNames).stream().collect(Collectors.joining(",")) + ")");
		}
		return _exce(sqlFragment);
	}

	@Override
	public BaseDataBaseOperation getBaseDataBaseOperation() {
		return this.baseDataBaseOperation;
	}

	@Override
	public Dialect getDialect() {
		return this.dialect;
	}

	protected Integer _exce(List<String> sqlFragment, Object... params) {
		return baseDataBaseOperation.update(SpliceSqlUtils.spliceSqlFragment(sqlFragment.toArray(new String[] {})),
				params);
	}

	protected Integer _exce(String sql, Object... params) {
		return baseDataBaseOperation.update(sql, params);
	}

	/**
	 * @param table 表
	 * @return sql语句的表 如： database.tableName
	 */
	protected String getSqlTableName(Table table) {
		String tableName = "";
		if (StringUtils.isNotBlank(table.getDataBaseName())) {
			tableName = table.getDataBaseName() + "." + table.getName();
		} else {
			tableName = table.getName();
		}
		return tableName;
	}

}

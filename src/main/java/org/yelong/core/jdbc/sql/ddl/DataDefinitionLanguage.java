/**
 * 
 */
package org.yelong.core.jdbc.sql.ddl;

import java.util.List;

import org.yelong.core.jdbc.BaseDataBaseOperation;
import org.yelong.core.jdbc.dialect.Dialect;

/**
 * 数据定义语言执行者。
 * 
 * 执行数据库定义语言。
 * 
 * @author PengFei
 * @since 1.1.0
 */
public interface DataDefinitionLanguage {
	
	/**
	 * 创建表。并覆盖之前的表
	 * @param table 表
	 * @return 数据操作行数
	 */
	default Integer createTable(Table table) {
		return createTable(table, true);
	}
	
	/**
	 * 创建表
	 * @param table 表
	 * @param override 是否覆盖 <code>true</code>覆盖。 <code>false</code>不覆盖
	 * @return 数据操作行数
	 */
	Integer createTable(Table table , boolean override);
	
	/**
	 * 重命名表
	 * @param table 表
	 * @param newName 新名称
	 * @return 数据操作行数
	 */
	Integer renameTable(Table table , String newName);
	
	/**
	 * 删除表
	 * @param table 表
	 * @return 数据操作行数
	 */
	Integer dropTable(Table table);
	
	/**
	 * 查询指定数据库下面的表
	 * @param database 数据库
	 * @return database 数据库下所有的表
	 */
	List<Table> queryTable(Database database);
	
	/**
	 * 查询指定数据库下的表信息
	 * @param database 数据库
	 * @param tableName 表
	 * @return 表信息
	 */
	Table queryTable(Database database , String tableName);
	
	/**
	 * 添加一个列
	 * @param column 列
	 * @return 数据操作行数
	 */
	Integer addColumn(Column column);

	/**
	 * 删除一个列
	 * @param column 列
	 * @return 数据操作行数
	 */
	Integer dropColumn(Column column);

	/**
	 * 修改一个列
	 * @param sourceColumn 源列
	 * @param column 新列
	 * @return 数据操作行数
	 */
	Integer modifyColumn(Column sourceColumn , Column column);

	/**
	 * 查询指定表下的列信息
	 * @param table 表
	 * @return 列信息
	 */
	List<Column> queryColumn(Table table);
	
	/**
	 * 查询表下所有主键列
	 * @param table 表
	 * @return 表中所有的主键列
	 */
	List<Column> queryPrimaryKeyColumn(Table table);
	
	/**
	 * 查询指定表下指定列的信息
	 * @param table 表
	 * @param columnName 列名称
	 * @return 列信息
	 */
	Column getColumn(Table table , String columnName);
	
	/**
	 * 设置列为主键列
	 * @param table 表
	 * @param columnName 列名
	 * @return 数据操作行数
	 */
	Integer setPrimaryKey(Table table , String columnName);
	
	/**
	 * 设置列为主键列
	 * @param table 表
	 * @param columnNames 列名
	 * @return 数据操作行数
	 */
	Integer setPrimaryKey(Table table , String [] columnNames);
	
	/**
	 * 删除表中所有主键列
	 * @param table 表
	 * @return 数据操作行数
	 */
	Integer dropPrimaryKey(Table table);
	
	/**
	 * 删除表中指定列的主键
	 * @param table 表
	 * @param columnName 删除的主键列名
	 * @return 数据操作行数
	 */
	Integer dropPrimaryKey(Table table , String columnName);
	
	/**
	 * 删除表中指定列的主键
	 * @param table 表
	 * @param columnNames 删除的主键列名数组
	 * @return 数据操作行数
	 */
	Integer dropPrimaryKey(Table table , String [] columnNames);
	
	/**
	 * @return 数据库操作对象
	 */
	BaseDataBaseOperation getBaseDataBaseOperation();
	
	/**
	 * @return 数据库方言
	 */
	Dialect getDialect();
	
}

/**
 * 
 */
package org.yelong.core.jdbc.dialect;

import java.util.List;

import org.yelong.core.annotation.Nullable;
import org.yelong.core.jdbc.BaseDataBaseOperation;
import org.yelong.core.jdbc.sql.BoundSql;
import org.yelong.core.jdbc.sql.ddl.DataDefinitionLanguage;
import org.yelong.core.jdbc.sql.factory.SqlFragmentFactory;
import org.yelong.core.jdbc.sql.function.DatabaseFunction;

/**
 * 数据库方言
 * 
 * @author PengFei
 */
public interface Dialect {

	/**
	 * 获取基础的delete sql
	 * 
	 * @param tableName  表名称
	 * @param tableAlias 表别名
	 * @return delete sql
	 */
	String getBaseDeleteSql(String tableName, String tableAlias);

	/**
	 * 获取基础的select sql。 列使用 “*”
	 * 
	 * 例子:
	 * <p>
	 * oracle: select ${tableAlias}.* from ${tableName} ${tableAlias} -> 
	 * 			select student.* from TB_STUDENT student
	 * </p>
	 * 
	 * @param tableName  表名称
	 * @param tableAlias 表别名
	 * @return select sql
	 */
	String getBaseSelectSql(String tableName, String tableAlias);

	/**
	 * 获取基础的查询sql 列使用别名加列名的方式，不采用“*”，可以提高sql执行效率
	 * 
	 * 例子:
	 * <p>
	 * oracle: select ${tableAlias}.${column},... from ${tableName} ${tableAlias} ->
	 * 			select student.stuName , student.age ,... from TB_STUDENT student
	 * </p>
	 * 
	 * @param tableName  表名
	 * @param tableAlias 表别名
	 * @param columns    需要查询的列名
	 * @return select sql
	 */
	String getBaseSelectSql(String tableName, String tableAlias, List<String> columns);

	/**
	 * 获取基础的 count sql
	 * 
	 * @param tableName  表名称
	 * @param tableAlias 表别名
	 * @return count sql
	 */
	String getBaseCountSql(String tableName, String tableAlias);

	/**
	 * @return 方言类型
	 */
	@Nullable
	DialectType getDialectType();

	/**
	 * 自定义的方言在{@link DialectType}中不存在时，请务必重写这个方法
	 * 
	 * @return 方言的名称
	 * @since 1.0.7
	 */
	default String getName() {
		return getDialectType() == null ? null : getDialectType().name();
	}

	/**
	 * 分页
	 * 
	 * @param boundSql 源sql与参数
	 * @param pageNum  页码
	 * @param pageSize 页面大小
	 * @return 分页后的boundSql
	 */
	default BoundSql page(BoundSql boundSql, int pageNum, int pageSize) {
		throw new UnsupportedOperationException("方言[" + getName() + "]当前不支持分页！");
	}

	/**
	 * 创建数据库定义语言
	 * 
	 * @param db 数据库操作
	 * @return 数据库定义语言
	 * @since 1.1.0
	 */
	default DataDefinitionLanguage createDataDefinitionLanguage(BaseDataBaseOperation db) {
		throw new UnsupportedOperationException("方言[" + getName() + "]当前不支持DDL语言！");
	}

	/**
	 * 创建数据库方法、函数
	 * 
	 * @param db 数据库操作
	 * @return 数据库方法、功能、函数的sql提供者
	 * @since 1.1.0
	 */
	default DatabaseFunction createDatabaseFunction(BaseDataBaseOperation db) {
		throw new UnsupportedOperationException("方言[" + getName() + "]当前不支持数据库方法、功能、函数的功能！");
	}

	/**
	 * @return sql片段工厂
	 * @since 1.1.0
	 */
	SqlFragmentFactory getSqlFragmentFactory();

}

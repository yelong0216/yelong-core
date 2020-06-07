/**
 * 
 */
package org.yelong.core.jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.yelong.core.annotation.Nullable;

/**
 * 基础数据库操作
 * 
 * @author PengFei
 */
public interface BaseDataBaseOperation {

	/**
	 * 数据库数据查询<br/>
	 * 一条记录为一个map key:字段名 value：值<br/>
	 * 
	 * @param sql sql语句
	 * @param params 参数 可以为空 如果为空则直接执行sql
	 * @return 查询的数据集合。没有记录这将是一个空的集合 {@link Collections#emptyList()}
	 * @throws SQLException
	 */
	List<Map<String, Object>> select(String sql, @Nullable Object... params);

	/**
	 * 查询一行记录
	 * 
	 * @param sql sql语句
	 * @param params 可以为空 如果为空则直接执行sql
	 * @return 一条数据的键值对。没有记录这将是一个空的Map {@link Collections#emptyMap()}
	 * @throws SQLException
	 */
	Map<String, Object> selectRow(String sql, @Nullable Object... params);

	/**
	 * 查询一列数据
	 * 
	 * @param <T> list data type
	 * @param sql sql语句
	 * @param params 可以为空 如果为空则直接执行sql
	 * @return 数据的集合。没有记录这将是一个空的Map {@link Collections#emptyList()}
	 * @throws SQLException
	 */
	<T> List<T> selectColumn(String sql, @Nullable Object... params);

	/**
	 * 查询唯单一的数据值
	 * 
	 * @param <T> data type
	 * @param sql sql语句
	 * @param params 可以为空 如果为空则直接执行sql
	 * @return 查询的第一行第一列的值。如果不存在记录则为null
	 * @throws SQLException
	 */
	<T> T selectSingleObject(String sql, @Nullable Object... params);

	/**
	 * 查询数据库记录数
	 * 
	 * @param sql sql语句
	 * @param params 参数 可以为空 如果为空则直接执行sql
	 * @return 查询的数据库记录数。查询的第一行第一列的值。
	 * @throws SQLException
	 */
	Long count(String sql, @Nullable Object... params);

	/**
	 * 删除数据库记录
	 * 
	 * @param sql 删除语句
	 * @param params 参数 可以为空 如果为空则直接执行sql
	 * @return 删除记录的条数
	 * @throws SQLException
	 */
	Integer delete(String sql, @Nullable Object... params);

	/**
	 * 修改数据库记录
	 * 
	 * @param sql 修改语句
	 * @param params 参数 可以为空 如果为空则直接执行sql
	 * @return 修改的记录数
	 * @throws SQLException
	 */
	Integer update(String sql, @Nullable Object... params);

	/**
	 * 添加数据库记录
	 * 
	 * @param sql 添加语句
	 * @param params 参数 可以为空 如果为空则直接执行sql
	 * @return 添加的记录数
	 * @throws SQLException
	 */
	Integer insert(String sql, @Nullable Object... params);
	
	/**
	 * 获取数据库连接
	 * 
	 * @return {@link java.sql.Connection}
	 */
	Connection getConnection();
	
}

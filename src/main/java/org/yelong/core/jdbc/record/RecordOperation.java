/**
 * 
 */
package org.yelong.core.jdbc.record;

import java.util.List;

import org.yelong.core.jdbc.sql.ddl.Table;

/**
 * 记录操作
 * 
 * 对数据库记录的增删改查操作。
 * 
 * @since 1.1
 */
public interface RecordOperation {

	/**
	 * 新增一条记录
	 * 
	 * @param table  新增记录的表
	 * @param record 记录
	 * @return 数据操作行数
	 */
	Integer insert(Table table, Record record);

	/**
	 * 查询记录
	 * 
	 * @param sql    sql
	 * @param params 参数
	 * @return 查询的记录
	 */
	List<Record> select(String sql, Object... params);

}

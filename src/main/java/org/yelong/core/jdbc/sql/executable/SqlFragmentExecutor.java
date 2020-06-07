/**
 * 
 */
package org.yelong.core.jdbc.sql.executable;

import java.util.List;
import java.util.Map;

/**
 * sql 片段执行者
 * 执行sql片段
 * 
 * @author PengFei
 */
public interface SqlFragmentExecutor {
	
	/**
	 * @Deprecated 不明确的返回值
	 */
	@Deprecated
	<R> R execute(SqlFragmentExecutable sqlFragment);
	
	/**
	 * 执行新增sql
	 * 
	 * @param insertSqlFragment 新增sql片段
	 * @return 新增的记录数
	 */
	Integer execute(InsertSqlFragment insertSqlFragment);
	
	/**
	 * 执行删除sql
	 * 
	 * @param deleteSqlFragment 删除sql片段
	 * @return 删除的记录数
	 */
	Integer execute(DeleteSqlFragment deleteSqlFragment);
	
	/**
	 * 执行修改sql
	 * 
	 * @param updateSqlFragment 修改sql片段
	 * @return 修改的记录数
	 */
	Integer execute(UpdateSqlFragment updateSqlFragment);
	
	/**
	 * 执行查询记录数sql
	 * 
	 * @param countSqlFragment 查询记录数sql
	 * @return 查询的记录数
	 */
	Long execute(CountSqlFragment countSqlFragment);
	
	/**
	 * 执行查询sql
	 * 
	 * @param selectSqlFragment 查询sql片段
	 * @return 多条记录的集合。每条记录的列名对值
	 */
	List<Map<String,Object>> execute(SelectSqlFragment selectSqlFragment);
	
}

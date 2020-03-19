/**
 * 
 */
package org.yelong.core.jdbc.sql.executable;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * sql 片段执行者
 * 执行sql片段
 * @author 彭飞
 * @date 2019年11月4日下午2:05:06
 * @version 1.2
 */
public interface SqlFragmentExecutor {
	
	/**
	 * 执行sql
	 * @author 彭飞
	 * @date 2019年11月4日下午2:16:46
	 * @version 1.2
	 * @param <R>
	 * @param sqlFragment 可执行的sql片段
	 * @return 根据执行的sql类型返回不同的结果值
	 * @throws SQLException
	 * @Deprecated 不明确的返回值
	 */
	@Deprecated
	<R> R execute(SqlFragmentExecutable sqlFragment);
	
	/**
	 * 执行新增sql
	 * @author 彭飞
	 * @date 2019年11月4日下午2:14:39
	 * @version 1.2
	 * @param insertSqlFragment 新增sql片段
	 * @return 新增的记录数
	 * @throws SQLException
	 */
	Integer execute(InsertSqlFragment insertSqlFragment);
	
	/**
	 * 执行删除sql
	 * @author 彭飞
	 * @date 2019年11月4日下午2:15:06
	 * @version 1.2
	 * @param deleteSqlFragment 删除sql片段
	 * @return 删除的记录数
	 * @throws SQLException
	 */
	Integer execute(DeleteSqlFragment deleteSqlFragment);
	
	/**
	 * 执行修改sql
	 * @author 彭飞
	 * @date 2019年11月4日下午2:15:24
	 * @version 1.2
	 * @param updateSqlFragment 修改sql片段
	 * @return 修改的记录数
	 * @throws SQLException
	 */
	Integer execute(UpdateSqlFragment updateSqlFragment);
	
	/**
	 * 执行查询记录数sql
	 * @author 彭飞
	 * @date 2019年11月4日下午2:25:02
	 * @version 1.2
	 * @param countSqlFragment 查询记录数sql
	 * @return 查询的记录数
	 * @throws SQLException
	 */
	Long execute(CountSqlFragment countSqlFragment);
	
	/**
	 * 执行查询sql
	 * @author 彭飞
	 * @date 2019年11月4日下午2:15:41
	 * @version 1.2
	 * @param selectSqlFragment 查询sql片段
	 * @return 多条记录的集合。每条记录的列名对值
	 * @throws SQLException
	 */
	List<Map<String,Object>> execute(SelectSqlFragment selectSqlFragment);
	
	
}

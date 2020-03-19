/**
 * 
 */
package org.yelong.core.jdbc.sql.splice;

/**
 * 拼接sql
 * @author 彭飞
 * @date 2019年9月12日下午4:33:12
 * @version 1.2
 */
public interface SpliceSql {
	
	/**
	 * 拼接insert sql
	 * @author 彭飞
	 * @date 2019年11月1日下午1:59:18
	 * @version 1.2
	 * @param tableName 表名称
	 * @param columns 列名称
	 * @return 
	 */
	String insertSql(String tableName , String [] columns);
	
	/**
	 * 拼接insert sql
	 * @author 彭飞
	 * @date 2019年11月1日下午1:59:47
	 * @version 1.2
	 * @param tableName 表名称
	 * @param attributeSqlFragment 属性片段 ( (userName,realName) value (?,?) )
	 * @return
	 */
	String insertSql(String tableName , String attributeSqlFragment);
	
	/**
	 * 拼接删除sql
	 * @author 彭飞
	 * @date 2019年11月1日下午2:00:10
	 * @version 1.2
	 * @param tableName 表名称
	 * @return
	 */
	String deleteSql(String tableName);
	
	/**
	 * 拼接删除sql
	 * @author 彭飞
	 * @date 2019年11月1日下午2:00:10
	 * @version 1.2
	 * @param tableName 表名称
	 * @param tableAlias 表别名
	 * @return
	 */
	String deleteSql(String tableName , String tableAlias);
	
	/**
	 * 修改sql
	 * @author 彭飞
	 * @date 2019年11月1日下午2:00:55
	 * @version 1.2
	 * @param tableName 表名称
	 * @param updateColumn 修改的列
	 * @return
	 */
	String updateSql(String tableName , String [] updateColumn);
	
	/**
	 * 修改sql
	 * @author 彭飞
	 * @date 2019年11月1日下午2:00:55
	 * @version 1.2
	 * @param tableName 表名称
	 * @param tableAlias 表别名
	 * @param updateColumn 修改的列
	 * @return
	 */
	String updateSql(String tableName , String tableAlias , String [] updateColumn);
	
	/**
	 * @author 彭飞
	 * @date 2019年10月21日下午3:14:05
	 * @version 1.2
	 * @param tableName 表名称
	 * @param tableAlias 表别名
	 * @param attributeSqlFragment 一段sql片段。如： userName = '彭飞' , realName = null 
	 * @return
	 */
	String updateSql(String tableName , String attributeSqlFragment);
	
	/**
	 * @author 彭飞
	 * @date 2019年10月21日下午3:14:05
	 * @version 1.2
	 * @param tableName 表名称
	 * @param tableAlias 表别名
	 * @param attributeSqlFragment 一段sql片段。如： userName = '彭飞' , realName = null 
	 * @return
	 */
	String updateSql(String tableName , String tableAlias , String attributeSqlFragment);
	
	/**
	 * 查询记录sql
	 * @author 彭飞
	 * @date 2019年11月1日下午2:01:12
	 * @version 1.2
	 * @param tableName 表名称
	 * @param tableAlias 表别名
	 * @return
	 */
	String countSql(String tableName , String tableAlias);
	
	/**
	 * 查询sql
	 * @author 彭飞
	 * @date 2019年11月1日下午2:01:27
	 * @version 1.2
	 * @param tableName 表名称
	 * @param tableAlias 表别名
	 * @return
	 */
	String selectSql(String tableName,String tableAlias);
	
	/**
	 * 拼接条件
	 * @author 彭飞
	 * @date 2019年11月1日下午1:57:32
	 * @version 1.2
	 * @param sql 基础的修改、查询、删除sql
	 * @param condition 条件。userName = ? and state = '0' (不带WHERE字样)
	 * @return
	 */
	String spliceCondition(String sql , String condition);
	
	/**
	 * 基础的查询sql
	 * @author 彭飞
	 * @date 2019年11月1日下午1:58:04
	 * @version 1.2
	 * @param sql 
	 * @param sort 排序 createTime desc(不带ORDER BY一样)
	 * @return
	 */
	String spliceSort(String sql , String sort);
	
}

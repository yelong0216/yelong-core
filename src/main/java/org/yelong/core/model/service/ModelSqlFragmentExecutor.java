/**
 * 
 */
package org.yelong.core.model.service;

import java.sql.SQLException;
import java.util.List;

import org.yelong.core.jdbc.sql.executable.SelectSqlFragment;
import org.yelong.core.jdbc.sql.executable.SqlFragmentExecutor;
import org.yelong.core.model.Model;

/**
 * model sql片段执行器
 * @author 彭飞
 * @date 2019年11月4日下午2:26:50
 * @version 1.2
 */
public interface ModelSqlFragmentExecutor extends SqlFragmentExecutor{
	
	/**
	 * 执行查询sql，并映射到model上面
	 * @author 彭飞
	 * @date 2019年11月4日下午2:28:30
	 * @version 1.2
	 * @param <M>
	 * @param modelClass model的类型
	 * @param selectSqlFragment 查询sql片段
	 * @return 查询到的model集合。这可能是一个空集合
	 * @throws SQLException
	 */
	<M extends Model> List<M> execute(Class<M> modelClass,SelectSqlFragment selectSqlFragment);

}

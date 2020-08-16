/**
 * 
 */
package org.yelong.core.model.service;

import java.util.List;

import org.yelong.core.jdbc.sql.executable.SelectSqlFragment;
import org.yelong.core.jdbc.sql.executable.SqlFragmentExecutor;
import org.yelong.core.model.Modelable;

/**
 * model sql片段执行器
 * 
 * @since 1.0
 */
public interface ModelSqlFragmentExecutor extends SqlFragmentExecutor {

	/**
	 * 执行查询SQL，并映射到model上面
	 * 
	 * @param <M>               model type
	 * @param modelClass        model class
	 * @param selectSqlFragment 查询SQL片段
	 * @return 查询到的model集合。这可能是一个空集合
	 */
	<M extends Modelable> List<M> execute(Class<M> modelClass, SelectSqlFragment selectSqlFragment);

}

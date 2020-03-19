/**
 * 
 */
package org.yelong.core.model.sql;

import org.yelong.core.jdbc.sql.attribute.AttributeSqlFragment;
import org.yelong.core.jdbc.sql.executable.CountSqlFragment;
import org.yelong.core.jdbc.sql.executable.DeleteSqlFragment;
import org.yelong.core.jdbc.sql.executable.InsertSqlFragment;
import org.yelong.core.jdbc.sql.executable.SelectSqlFragment;
import org.yelong.core.jdbc.sql.executable.UpdateSqlFragment;
import org.yelong.core.jdbc.sql.factory.SqlFragmentFactory;
import org.yelong.core.model.Model;
import org.yelong.core.model.resolve.ModelAndTableManager;

/**
 * 模型sql片段工厂
 * @author PengFei
 * @date 2020年1月20日下午2:19:21
 */
public interface ModelSqlFragmentFactory extends SqlFragmentFactory{
	
	/**
	 * 创建insert sql
	 * @param <M>
	 * @param modelClass modelClass
	 * @param attributeSqlFragment 属性片段
	 * @return insert sql
	 */
	<M extends Model> InsertSqlFragment createInsertSqlFragment(Class<M> modelClass , AttributeSqlFragment attributeSqlFragment);
	
	/**
	 * 创建delete sql
	 * @param <M>
	 * @param modelClass modelClass
	 * @return delete sql
	 */
	<M extends Model> DeleteSqlFragment createDeleteSqlFragment(Class<M> modelClass);
	
	/**
	 * 创建update sql
	 * @param <M>
	 * @param modelClass modelClass
	 * @param attributeSqlFragment 属性sql
	 * @return update sql
	 */
	<M extends Model> UpdateSqlFragment createUpdateSqlFragment(Class<M> modelClass , AttributeSqlFragment attributeSqlFragment);
	
	/**
	 * 创建select sql
	 * @param <M>
	 * @param modelClass modelClass
	 * @return select sql
	 */
	<M extends Model> SelectSqlFragment createSelectSqlFragment(Class<M> modelClass);
	
	/**
	 * 创建count sql
	 * @param <M>
	 * @param modelClass modelClass
	 * @return count sql
	 */
	<M extends Model> CountSqlFragment createCountSqlFragment(Class<M> modelClass);
	
	/**
	 * @return 模型与表的管理者
	 */
	ModelAndTableManager getModelAndTableManager();
	
}

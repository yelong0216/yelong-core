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
 * @author PengFei
 * @date 2020年1月20日下午2:19:21
 */
public interface ModelSqlFragmentFactory extends SqlFragmentFactory{
	
	<M extends Model> InsertSqlFragment createInsertSqlFragment(Class<M> modelClass , AttributeSqlFragment attributeSqlFragment);
	
	<M extends Model> DeleteSqlFragment createDeleteSqlFragment(Class<M> modelClass);
	
	<M extends Model> UpdateSqlFragment createUpdateSqlFragment(Class<M> modelClass , AttributeSqlFragment attributeSqlFragment);
	
	<M extends Model> SelectSqlFragment createSelectSqlFragment(Class<M> modelClass);
	
	<M extends Model> CountSqlFragment createCountSqlFragment(Class<M> modelClass);
	
	ModelAndTableManager getModelAndTableManager();
	
}

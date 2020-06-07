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
import org.yelong.core.model.Modelable;
import org.yelong.core.model.annotation.Select;
import org.yelong.core.model.resolve.ModelAndTableManager;
import org.yelong.core.model.sql.CustomModelSql.OperationType;

/**
 * 模型sql片段工厂
 * 
 * @author PengFei
 * @date 2020年1月20日下午2:19:21
 */
public interface ModelSqlFragmentFactory extends SqlFragmentFactory{
	
	/**
	 * 创建insert sql
	 * 
	 * @param <M> model type
	 * @param modelClass modelClass
	 * @param attributeSqlFragment 属性片段
	 * @return insert sql
	 */
	<M extends Modelable> InsertSqlFragment createInsertSqlFragment(Class<M> modelClass , AttributeSqlFragment attributeSqlFragment);
	
	/**
	 * 创建delete sql
	 * 
	 * @param <M> model type
	 * @param modelClass modelClass
	 * @return delete sql
	 */
	<M extends Modelable> DeleteSqlFragment createDeleteSqlFragment(Class<M> modelClass);
	
	/**
	 * 创建update sql
	 * 
	 * @param <M>model type
	 * @param modelClass modelClass
	 * @param attributeSqlFragment 属性sql
	 * @return update sql
	 */
	<M extends Modelable> UpdateSqlFragment createUpdateSqlFragment(Class<M> modelClass , AttributeSqlFragment attributeSqlFragment);
	
	/**
	 * 创建select sql
	 * 
	 * model 的默认sql由以下几种方式依次获取：
	 * 		1、model标注{@link Select}注解且注解{@link Select#value()}不为 <code>null</code> 和空白字符时
	 * 		2、model在 {@link CustomModelSql}中注册的 {@link #getDialect()}、{@link OperationType#SELECT}的sql
	 * 		3、根据方言生成默认的查询语句
	 * 
	 * @param <M> model type
	 * @param modelClass modelClass
	 * @return select sql
	 * @since 1.0.7
	 */
	<M extends Modelable> SelectSqlFragment createSelectSqlFragment(Class<M> modelClass);
	
	/**
	 * 创建count sql
	 * 
	 * @param <M> model type
	 * @param modelClass modelClass
	 * @return count sql
	 */
	<M extends Modelable> CountSqlFragment createCountSqlFragment(Class<M> modelClass);
	
	/**
	 * @return 模型与表的管理者
	 */
	ModelAndTableManager getModelAndTableManager();
	
}

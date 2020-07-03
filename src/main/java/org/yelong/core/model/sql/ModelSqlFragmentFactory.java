/**
 * 
 */
package org.yelong.core.model.sql;

import java.util.List;

import org.yelong.core.jdbc.sql.attribute.AttributeSqlFragment;
import org.yelong.core.jdbc.sql.executable.CountSqlFragment;
import org.yelong.core.jdbc.sql.executable.DeleteSqlFragment;
import org.yelong.core.jdbc.sql.executable.InsertSqlFragment;
import org.yelong.core.jdbc.sql.executable.SelectSqlFragment;
import org.yelong.core.jdbc.sql.executable.UpdateSqlFragment;
import org.yelong.core.jdbc.sql.factory.SqlFragmentFactory;
import org.yelong.core.model.Modelable;
import org.yelong.core.model.annotation.Select;
import org.yelong.core.model.exception.ExtendColumnException;
import org.yelong.core.model.exception.NotExistFieldAndColumnException;
import org.yelong.core.model.resolve.FieldAndColumn;
import org.yelong.core.model.resolve.ModelAndTableManager;
import org.yelong.core.model.sql.CustomModelSql.OperationType;

/**
 * 模型sql片段工厂
 * 
 * @author PengFei
 */
public interface ModelSqlFragmentFactory extends SqlFragmentFactory {

	/**
	 * 创建insert sql
	 * 
	 * @param <M>                  model type
	 * @param modelClass           modelClass
	 * @param attributeSqlFragment 属性片段
	 * @return insert sql
	 */
	<M extends Modelable> InsertSqlFragment createInsertSqlFragment(Class<M> modelClass,
			AttributeSqlFragment attributeSqlFragment);

	/**
	 * 创建delete sql
	 * 
	 * @param <M>        model type
	 * @param modelClass modelClass
	 * @return delete sql
	 */
	<M extends Modelable> DeleteSqlFragment createDeleteSqlFragment(Class<M> modelClass);

	/**
	 * 创建update sql
	 * 
	 * @param <M>model             type
	 * @param modelClass           modelClass
	 * @param attributeSqlFragment 属性sql
	 * @return update sql
	 */
	<M extends Modelable> UpdateSqlFragment createUpdateSqlFragment(Class<M> modelClass,
			AttributeSqlFragment attributeSqlFragment);

	/**
	 * 创建select sql<br/>
	 * 
	 * model 的默认sql由以下几种方式依次获取：<br/>
	 * 
	 * 1、model标注{@link Select}注解且注解{@link Select#value()}不为 <code>null</code> 和空白字符时
	 * <br/>
	 * 2、model在 {@link CustomModelSql}中注册的
	 * {@link #getDialect()}、{@link OperationType#SELECT}的sql <br/>
	 * 3、根据方言生成默认的查询语句
	 * 
	 * @param <M>        model type
	 * @param modelClass modelClass
	 * @return select sql
	 * @since 1.0.7
	 */
	<M extends Modelable> SelectSqlFragment createSelectSqlFragment(Class<M> modelClass);

	/**
	 * 根据指定的查询字段创建select sql<br/>
	 * 通过字段名称找到映射的列名并生成查询sql。<br/>
	 * 如果在此model中没有找到字段映射的
	 * {@link FieldAndColumn}，则抛出{@link NotExistFieldAndColumnException}<br/>
	 * 
	 * @param <M>        model type
	 * @param modelClass modelClass
	 * @param fieldNames 查询的字段集合
	 * @return select sql
	 * @since 1.3.0
	 * @throws NotExistFieldAndColumnException 查询的字段不存在映射的列
	 * @throws ExtendColumnException           查询的字段映射的列是一个拓展列
	 */
	<M extends Modelable> SelectSqlFragment createSelectSqlFragmentByFieldNames(Class<M> modelClass,
			List<String> fieldNames) throws NotExistFieldAndColumnException, ExtendColumnException;

	/**
	 * 根据指定的查询列创建select sql
	 * 
	 * @param <M>        model type
	 * @param modelClass modelClass
	 * @param columns    查询的列集合
	 * @return select sql
	 * @since 1.3.0
	 */
	<M extends Modelable> SelectSqlFragment createSelectSqlFragmentByColumns(Class<M> modelClass, List<String> columns);

	/**
	 * 创建count sql
	 * 
	 * @param <M>        model type
	 * @param modelClass modelClass
	 * @return count sql
	 */
	<M extends Modelable> CountSqlFragment createCountSqlFragment(Class<M> modelClass);

	/**
	 * @return 模型与表的管理者
	 */
	ModelAndTableManager getModelAndTableManager();

}

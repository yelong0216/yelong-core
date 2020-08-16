/**
 * 
 */
package org.yelong.core.model.sql;

import java.util.List;

import org.yelong.core.jdbc.sql.attribute.AttributeSqlFragment;
import org.yelong.core.jdbc.sql.count.CountSqlParser;
import org.yelong.core.jdbc.sql.executable.CountSqlFragment;
import org.yelong.core.jdbc.sql.executable.DeleteSqlFragment;
import org.yelong.core.jdbc.sql.executable.InsertSqlFragment;
import org.yelong.core.jdbc.sql.executable.SelectSqlFragment;
import org.yelong.core.jdbc.sql.executable.UpdateSqlFragment;
import org.yelong.core.jdbc.sql.factory.SqlFragmentFactory;
import org.yelong.core.model.Modelable;
import org.yelong.core.model.annotation.Count;
import org.yelong.core.model.annotation.Select;
import org.yelong.core.model.manage.FieldAndColumn;
import org.yelong.core.model.manage.ModelAndTable;
import org.yelong.core.model.manage.ModelManager;
import org.yelong.core.model.manage.exception.FieldAndColumnException;
import org.yelong.core.model.manage.exception.NotExistFieldAndColumnException;
import org.yelong.core.model.sql.CustomModelSql.OperationType;

/**
 * 模型SQL片段工厂
 * 
 * @since 1.0
 */
public interface ModelSqlFragmentFactory extends SqlFragmentFactory {

	/**
	 * 创建insert SQL片段
	 * 
	 * @param modelClass           modelClass
	 * @param attributeSqlFragment 属性片段
	 * @return insert SQL片段
	 */
	InsertSqlFragment createInsertSqlFragment(Class<? extends Modelable> modelClass,
			AttributeSqlFragment attributeSqlFragment);

	/**
	 * 创建delete SQL片段
	 * 
	 * @param modelClass modelClass
	 * @return delete SQL片段
	 */
	DeleteSqlFragment createDeleteSqlFragment(Class<? extends Modelable> modelClass);

	/**
	 * 创建update SQL片段
	 * 
	 * @param modelClass           modelClass
	 * @param attributeSqlFragment 属性片段
	 * @return update SQL片段
	 */
	UpdateSqlFragment createUpdateSqlFragment(Class<? extends Modelable> modelClass,
			AttributeSqlFragment attributeSqlFragment);

	/**
	 * 创建select SQL<br/>
	 * 
	 * model 的默认查询SQL由以下几种方式依次获取：<br/>
	 * 
	 * 1、model标注{@link Select}注解且注解{@link Select#value()}不为 <code>null</code> 和空白字符时
	 * <br/>
	 * 2、model在 {@link CustomModelSql}中注册的
	 * {@link #getDialect()}、{@link OperationType#SELECT}的SQL <br/>
	 * 3、根据方言生成默认的查询语句。默认SQL查询列采用 '*'
	 * 
	 * @param modelClass modelClass
	 * @return select SQL片段
	 * @since 1.0.7
	 */
	default SelectSqlFragment createSelectSqlFragment(Class<? extends Modelable> modelClass) {
		return createSelectSqlFragment(modelClass, SelectSqlColumnMode.STAR, false);
	}

	/**
	 * 创建select SQL<br/>
	 * 
	 * model 的默认查询SQL由以下几种方式依次获取：<br/>
	 * 
	 * 1、model标注{@link Select}注解且注解{@link Select#value()}不为 <code>null</code> 和空白字符时
	 * <br/>
	 * 2、model在 {@link CustomModelSql}中注册的
	 * {@link #getDialect()}、{@link OperationType#SELECT}的SQL <br/>
	 * 3、根据方言和指定的查询SQL列模型生成默认的查询语句。
	 * 
	 * @param modelClass          model class
	 * @param selectSqlColumnMode 查询SQL列模式
	 * @return
	 */
	default SelectSqlFragment createSelectSqlFragment(Class<? extends Modelable> modelClass,
			SelectSqlColumnMode selectSqlColumnMode) {
		return createSelectSqlFragment(modelClass, selectSqlColumnMode, true);
	}

	/**
	 * 创建select SQL<br/>
	 * 
	 * model 的默认查询SQL由以下几种方式依次获取：<br/>
	 * 
	 * 1、model标注{@link Select}注解且注解{@link Select#value()}不为 <code>null</code> 和空白字符时
	 * <br/>
	 * 2、model在 {@link CustomModelSql}中注册的
	 * {@link #getDialect()}、{@link OperationType#SELECT}的SQL <br/>
	 * 3、根据方言和最优的查询列模式生成默认的查询语句
	 * 
	 * @param modelClass          model class
	 * @param selectSqlColumnMode 查询SQL列模式
	 * @param force               是否强制使用入参的 {@link SelectSqlColumnMode}。如果不强制且
	 *                            {@link ModelAndTable#getSelectSqlColumnMode()}不为空，则使用{@link ModelAndTable#getSelectSqlColumnMode()}
	 * @return select SQL片段
	 */
	SelectSqlFragment createSelectSqlFragment(Class<? extends Modelable> modelClass,
			SelectSqlColumnMode selectSqlColumnMode, boolean force);

	/**
	 * 根据指定的查询字段创建select SQL<br/>
	 * 通过字段名称找到映射的列名并生成查询SQL。<br/>
	 * 如果在此model中没有找到字段映射的
	 * {@link FieldAndColumn}，则抛出{@link NotExistFieldAndColumnException}<br/>
	 * 
	 * @param modelClass modelClass
	 * @param fieldNames 查询的字段集合
	 * @return select SQL片段
	 * @since 1.3.0
	 * @throws NotExistFieldAndColumnException 查询的字段不存在映射的列
	 * @throws FieldAndColumnException         查询的字段映射的列是一个拓展列
	 */
	SelectSqlFragment createSelectSqlFragmentByFieldNames(Class<? extends Modelable> modelClass,
			List<String> fieldNames) throws NotExistFieldAndColumnException, FieldAndColumnException;

	/**
	 * 根据指定的查询列创建select SQL
	 * 
	 * @param modelClass modelClass
	 * @param columns    查询的列集合
	 * @return select SQL片段
	 * @since 1.3.0
	 */
	SelectSqlFragment createSelectSqlFragmentByColumns(Class<? extends Modelable> modelClass, List<String> columns);

	/**
	 * 创建count SQL <br/>
	 * 
	 * count SQL 获取顺序如下：<br/>
	 * 1、{@link Count}<br/>
	 * 2、解析 select SQL 来获取 {@link CountSqlParser}
	 * 
	 * @param modelClass modelClass
	 * @return count SQL片段
	 * @since 2.0
	 */
	CountSqlFragment createCountSqlFragment(Class<? extends Modelable> modelClass);

	/**
	 * @return 模型管理者
	 */
	ModelManager getModelManager();

}

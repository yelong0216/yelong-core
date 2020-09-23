/**
 * 
 */
package org.yelong.core.model.annotation.support;

import org.yelong.commons.lang.Strings;
import org.yelong.core.model.Modelable;
import org.yelong.core.model.manage.AbstractModelAndTable;
import org.yelong.core.model.manage.ModelAndTable;
import org.yelong.core.model.resolve.ModelResolveException;
import org.yelong.core.model.resolve.ModelResolver;

/**
 * 模型类注解工具类
 * 
 * @since 2.0
 */
public final class ModelClassAnnotations {

	/**
	 * 设置抽象模型表的属性
	 * 
	 * @param modelAndTable        模型表
	 * @param modelClassAnnotation 模型类注解属性
	 */
	public static void setProperty(AbstractModelAndTable modelAndTable, ModelClassAnnotation modelClassAnnotation) {
		modelAndTable.setTableName(modelClassAnnotation.getTableName());
		modelAndTable.setTableAlias(modelClassAnnotation.getTableAlias());
		modelAndTable.setTableDesc(modelClassAnnotation.getTableDese());
		modelAndTable.setView(modelClassAnnotation.isView());
		modelAndTable.setSelectSqlColumnMode(modelClassAnnotation.getSelectSqlColumnMode());
		modelAndTable.setSelectSql(modelClassAnnotation.getSelectSql());
		modelAndTable.setDeleteSql(modelClassAnnotation.getDeleteSql());
		modelAndTable.setCountSql(modelClassAnnotation.getCountSql());
	}

	/**
	 * 创建模型表
	 * 
	 * @param <T>                  modelAndTable type
	 * @param modelClassAnnotation 模型类注解属性
	 * @param modelAndTableFactory 模型表工厂
	 * @return 模型表
	 */
	public static <T extends ModelAndTable> T createModelAndTable(ModelClassAnnotation modelClassAnnotation,
			ModelAndTableSupplier<T> modelAndTableFactory) {
		return createModelAndTable(null, modelClassAnnotation, modelAndTableFactory);
	}

	/**
	 * 创建模型表
	 * 
	 * @param <T>                  modelAndTable type
	 * @param modelResolver        模型解析器
	 * @param modelClassAnnotation 模型类注解属性
	 * @param modelAndTableFactory 模型表工厂
	 * @return 模型表
	 */
	public static <T extends ModelAndTable> T createModelAndTable(ModelResolver modelResolver,
			ModelClassAnnotation modelClassAnnotation, ModelAndTableSupplier<T> modelAndTableFactory) {
		Class<? extends Modelable> modelClass = modelClassAnnotation.getModelClass();
		if (!modelClassAnnotation.isView()) {// 不是视图则表名为必填项
			if (null == modelClassAnnotation.getTable()) {
				throw new ModelResolveException(modelResolver, modelClass,
						"[" + modelClass.getName() + "]及其父类均未标有Table注解，无法进行解析！");
			}
			Strings.requireNonBlank(modelClassAnnotation.getTableName(), "[" + modelClass + "]声明的表名不允许为空!");
		}
		return modelAndTableFactory.get(modelClass);
	}

	/**
	 * 模型表工厂
	 * 
	 * @param <T> modelAndTable type
	 */
	@FunctionalInterface
	public static interface ModelAndTableSupplier<T extends ModelAndTable> {

		/**
		 * 创建模型表
		 * 
		 * @param modelClass 模型类型
		 * @param tableName  表名称
		 * @return 模型表
		 */
		T get(Class<? extends Modelable> modelClass);

	}

}

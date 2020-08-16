/**
 * 
 */
package org.yelong.core.model.annotation.support;

import org.yelong.core.model.manage.AbstractFieldAndColumn;

/**
 * 模型字段注解工具类
 * 
 * @since 2.0
 */
public final class ModelFieldAnnotations {

	private ModelFieldAnnotations() {
	}

	/**
	 * 抽象字段列设置属性
	 * 
	 * @param fieldAndColumn       字段列
	 * @param modelFieldAnnotation 模型字段注解
	 */
	public static void setProperty(AbstractFieldAndColumn fieldAndColumn, ModelFieldAnnotation modelFieldAnnotation) {
		fieldAndColumn.setAllowBlank(modelFieldAnnotation.isAllowBlank());
		fieldAndColumn.setAllowNull(modelFieldAnnotation.isAllowNull());
		fieldAndColumn.setColumnName(modelFieldAnnotation.getColumnName());
		fieldAndColumn.setDesc(modelFieldAnnotation.getDesc());
		fieldAndColumn.setExtend(modelFieldAnnotation.isExtendColumn());
		fieldAndColumn.setJdbcType(modelFieldAnnotation.getJdbcType());
		fieldAndColumn.setMaxLength(modelFieldAnnotation.getMaxLength());
		fieldAndColumn.setMinLength(modelFieldAnnotation.getMinLength());
		fieldAndColumn.setPrimaryKey(modelFieldAnnotation.isPrimaryKey());
		fieldAndColumn.setSelectColumn(modelFieldAnnotation.getSelectColumnCode());
		fieldAndColumn.setSelect(modelFieldAnnotation.isSelect());
		fieldAndColumn.setTransient(modelFieldAnnotation.isTransient());
		// 是拓展列 ，设置拓展列属性
		if (fieldAndColumn.isExtend()) {
			fieldAndColumn.setExtendColumnAttribute(modelFieldAnnotation.getExtendColumnAttribute(fieldAndColumn));
		}
		fieldAndColumn.setSelectColumnCondition(modelFieldAnnotation.getSelectColumnCondition());
	}

}

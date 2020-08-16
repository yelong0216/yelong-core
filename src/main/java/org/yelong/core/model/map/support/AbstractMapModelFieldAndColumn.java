/**
 * 
 */
package org.yelong.core.model.map.support;

import java.lang.reflect.Field;

import org.yelong.core.model.manage.AbstractFieldAndColumn;
import org.yelong.core.model.map.MapModelFieldAndColumn;

/**
 * 抽象的Map模型字段列实现
 * 
 * @since 1.1
 */
public abstract class AbstractMapModelFieldAndColumn extends AbstractFieldAndColumn implements MapModelFieldAndColumn {

	private final String column;

	private Class<?> fieldType;

	public AbstractMapModelFieldAndColumn(String column) {
		this.column = column;
	}

	@Override
	public String getColumn() {
		return this.column;
	}

	@Override
	public String getFieldName() {
		return getColumn();
	}

	@Override
	public String getSelectColumn() {
		return getColumn();
	}

	public Class<?> getFieldType() {
		return fieldType != null ? fieldType : Object.class;
	}

	public void setFieldType(Class<?> fieldType) {
		this.fieldType = fieldType;
	}

	@Override
	public Field getField() {
		return null;
	}

}

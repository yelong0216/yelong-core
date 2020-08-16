/**
 * 
 */
package org.yelong.core.model.manage.support;

import java.lang.reflect.Field;
import java.util.Objects;

import org.yelong.core.model.manage.AbstractFieldAndColumn;

/**
 * 字段列的默认实现
 * 
 * @since 2.0
 */
public class DefaultFieldAndColumn extends AbstractFieldAndColumn {

	private final Field field;

	private final String column;

	public DefaultFieldAndColumn(Field field, String column) {
		this.field = Objects.requireNonNull(field);
		this.column = Objects.requireNonNull(column);
	}

	@Override
	public Field getField() {
		return this.field;
	}

	@Override
	public String getFieldName() {
		return this.field.getName();
	}

	@Override
	public Class<?> getFieldType() {
		return this.field.getType();
	}

	@Override
	public String getColumn() {
		return this.column;
	}

}

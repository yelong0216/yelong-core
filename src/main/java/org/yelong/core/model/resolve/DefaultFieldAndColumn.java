/**
 * 
 */
package org.yelong.core.model.resolve;

import java.lang.reflect.Field;
import java.util.Objects;

/**
 * 默认的字段和列实现
 * @author PengFei
 * @since 1.0.0
 */
public class DefaultFieldAndColumn extends AbstractFieldAndColumn{
	
	private final ModelAndTable modelAndTable;
	
	private final Field field;
	
	private final String column;
	
	public DefaultFieldAndColumn(ModelAndTable modelAndTable ,Field field, String column) {
		Objects.requireNonNull(modelAndTable, "'modelAndTable'不允许为 null");
		this.modelAndTable = modelAndTable;
		this.field = field;
		this.column = column;
	}
	
	@Deprecated
	public DefaultFieldAndColumn(Field field, String column) {
		this.modelAndTable = null;
		this.field = field;
		this.column = column;
	}
	
	@Override
	public ModelAndTable getModelAndTable() {
		return modelAndTable;
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

	@Override
	public String toString() {
		return "fieldName:"+getFieldName()+"\tcolumn:"+getColumn();
	}
	
}

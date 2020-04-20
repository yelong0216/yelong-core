/**
 * 
 */
package org.yelong.core.model.resolve;

import java.lang.reflect.Field;
import java.util.Objects;

/**
 * 抽象的字段和列
 * @author PengFei
 */
public abstract class AbstractFieldAndColumn implements FieldAndColumn{

	private final ModelAndTable modelAndTable;
	
	private final Field field;
	
	private final String column;
	
	public AbstractFieldAndColumn(ModelAndTable modelAndTable ,Field field, String column) {
		Objects.requireNonNull(modelAndTable, "'modelAndTable'不允许为 null");
		this.modelAndTable = modelAndTable;
		this.field = field;
		this.column = column;
	}
	
	@Deprecated
	public AbstractFieldAndColumn(Field field, String column) {
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
		return "fieldName:"+field.getName()+"\tcolumn:"+column;
	}
	
}

/**
 * 
 */
package org.yelong.core.model.resolve;

import java.lang.reflect.Field;

/**
 * @author PengFei
 * @date 2019年12月27日下午7:05:29
 */
public abstract class AbstractFieldAndColumn implements FieldAndColumn{

	private Field field;
	
	private String column;
	
	public AbstractFieldAndColumn(Field field, String column) {
		this.field = field;
		this.column = column;
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
		return "fieldName:"+field.getName()+"\ncolumn:"+column;
	}
	
}

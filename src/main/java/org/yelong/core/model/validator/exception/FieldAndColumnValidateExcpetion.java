package org.yelong.core.model.validator.exception;

import org.yelong.core.annotation.Nullable;
import org.yelong.core.model.manage.FieldAndColumn;
import org.yelong.core.model.manage.exception.FieldAndColumnException;

/**
 * 字段列验证异常<br/>
 * 
 * @since 2.0
 */
public class FieldAndColumnValidateExcpetion extends FieldAndColumnException {

	private static final long serialVersionUID = 5350952539836061852L;

	@Nullable
	private final Object value;

	/**
	 * @param fieldAndColumn 字段列
	 * @param value          字段值
	 */
	public FieldAndColumnValidateExcpetion(FieldAndColumn fieldAndColumn, Object value) {
		super(fieldAndColumn, "字段\"" + fieldAndColumn.getColumn() + "\"验证失败。值(" + value + ")");
		this.value = value;
	}

	/**
	 * @param fieldAndColumn 字段列
	 * @param value          字段值
	 * @param message        异常信息
	 */
	public FieldAndColumnValidateExcpetion(FieldAndColumn fieldAndColumn, Object value, String message) {
		super(fieldAndColumn, message);
		this.value = value;
	}

	public Object getFieldValue() {
		return value;
	}

}

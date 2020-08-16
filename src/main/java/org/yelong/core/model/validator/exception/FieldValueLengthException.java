package org.yelong.core.model.validator.exception;

import org.yelong.core.model.manage.FieldAndColumn;

/**
 * 字段列长度异常<br/>
 * 
 * 规则：<br/>
 * 值的长度大于或小于规定的最大长度或最小长度
 * 
 * @since 2.0
 */
public class FieldValueLengthException extends FieldAndColumnValidateExcpetion {

	private static final long serialVersionUID = -3202280747287289508L;

	public FieldValueLengthException(FieldAndColumn fieldAndColumn, Object value, String message) {
		super(fieldAndColumn, value, message);
	}

	public FieldValueLengthException(FieldAndColumn fieldAndColumn, Object value) {
		super(fieldAndColumn, value);
	}

}

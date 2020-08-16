package org.yelong.core.model.validator.exception;

import org.yelong.core.model.manage.FieldAndColumn;

/**
 * 字段列值为<code>null</code>异常<br/>
 * 
 * 规则：<br/>
 * 字段列值不允许为 <code>null</code>
 * 
 * @since 2.0
 */
public class FieldValueNullException extends FieldAndColumnValidateExcpetion {

	private static final long serialVersionUID = -2450391000505970247L;

	public FieldValueNullException(FieldAndColumn fieldAndColumn, Object value, String message) {
		super(fieldAndColumn, value, message);
	}

	public FieldValueNullException(FieldAndColumn fieldAndColumn, Object value) {
		super(fieldAndColumn, value);
	}

}

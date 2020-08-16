/**
 * 
 */
package org.yelong.core.model.validator.exception;

import org.yelong.core.model.manage.FieldAndColumn;

/**
 * 字段列值空白异常<br/>
 * 
 * 规则：<br/>
 * 字段列值不允许为空白
 * 
 * @since 2.0
 */
public class FieldValueBlankException extends FieldAndColumnValidateExcpetion {

	private static final long serialVersionUID = 3271606897403371487L;

	public FieldValueBlankException(FieldAndColumn fieldAndColumn, Object value) {
		super(fieldAndColumn, value);
	}

	public FieldValueBlankException(FieldAndColumn fieldAndColumn, Object value, String message) {
		super(fieldAndColumn, value, message);
	}

}

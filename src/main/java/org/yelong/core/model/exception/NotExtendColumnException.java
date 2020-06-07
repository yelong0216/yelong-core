/**
 * 
 */
package org.yelong.core.model.exception;

import org.yelong.core.model.resolve.FieldAndColumn;

/**
 * 不是拓展列异常
 * 
 * @author PengFei
 * @since 1.2.0
 */
public class NotExtendColumnException extends ModelException{

	private static final long serialVersionUID = -3254327914291522694L;

	private final FieldAndColumn fieldAndColumn;
	
	public NotExtendColumnException(FieldAndColumn fieldAndColumn) {
		this(fieldAndColumn,fieldAndColumn.getFieldName()+"不是一个拓展列");
	}
	
	public NotExtendColumnException(FieldAndColumn fieldAndColumn ,String message) {
		super(message);
		this.fieldAndColumn = fieldAndColumn;
	}

	public FieldAndColumn getFieldAndColumn() {
		return fieldAndColumn;
	}
	
}

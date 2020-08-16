/**
 * 
 */
package org.yelong.core.model.manage.exception;

import java.util.Objects;

import org.yelong.core.model.manage.FieldAndColumn;

/**
 * 字段列异常
 * 
 * @since 2.0
 */
public class FieldAndColumnException extends ModelAndTableException {

	private static final long serialVersionUID = 6340559675310810135L;

	/**
	 * 出现异常的字段列
	 */
	private final FieldAndColumn fieldAndColumn;

	public FieldAndColumnException(FieldAndColumn fieldAndColumn) {
		super(Objects.requireNonNull(fieldAndColumn).getModelAndTable());
		this.fieldAndColumn = fieldAndColumn;
	}

	public FieldAndColumnException(FieldAndColumn fieldAndColumn, String message) {
		super(Objects.requireNonNull(fieldAndColumn).getModelAndTable(), message);
		this.fieldAndColumn = fieldAndColumn;
	}

	public FieldAndColumnException(FieldAndColumn fieldAndColumn, Throwable t) {
		super(Objects.requireNonNull(fieldAndColumn).getModelAndTable(), t);
		this.fieldAndColumn = fieldAndColumn;
	}

	public FieldAndColumnException(FieldAndColumn fieldAndColumn, String message, Throwable t) {
		super(Objects.requireNonNull(fieldAndColumn).getModelAndTable(), message, t);
		this.fieldAndColumn = fieldAndColumn;
	}

	public FieldAndColumn getFieldAndColumn() {
		return fieldAndColumn;
	}

}

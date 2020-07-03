/**
 * 
 */
package org.yelong.core.model.exception;

/**
 * @author PengFei
 * @since 1.3.0
 */
public class FieldAndColumnException extends ModelException {

	private static final long serialVersionUID = 6340559675310810135L;

	public FieldAndColumnException() {

	}

	public FieldAndColumnException(String message) {
		super(message);
	}

	public FieldAndColumnException(Throwable t) {
		super(t);
	}

	public FieldAndColumnException(String message, Throwable t) {
		super(message, t);
	}

}

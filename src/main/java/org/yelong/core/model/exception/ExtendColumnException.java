/**
 * 
 */
package org.yelong.core.model.exception;

/**
 * 拓展列异常
 * 
 * @author PengFei
 * @since 1.3.0
 */
public class ExtendColumnException extends FieldAndColumnException {

	private static final long serialVersionUID = 5911878030802603448L;

	public ExtendColumnException() {

	}

	public ExtendColumnException(String message) {
		super(message);
	}

	public ExtendColumnException(Throwable t) {
		super(t);
	}

	public ExtendColumnException(String message, Throwable t) {
		super(message, t);
	}

}

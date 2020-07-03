/**
 * 
 */
package org.yelong.core.model.exception;

/**
 * 不存在的字段与列的映射异常
 * 
 * @author PengFei
 * @since 1.3.0
 */
public class NotExistFieldAndColumnException extends FieldAndColumnException {

	private static final long serialVersionUID = -384612804269069548L;

	public NotExistFieldAndColumnException() {

	}

	public NotExistFieldAndColumnException(String message) {
		super(message);
	}

	public NotExistFieldAndColumnException(Throwable t) {
		super(t);
	}

	public NotExistFieldAndColumnException(String message, Throwable t) {
		super(message, t);
	}

}

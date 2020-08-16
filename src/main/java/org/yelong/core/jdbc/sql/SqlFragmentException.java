/**
 * 
 */
package org.yelong.core.jdbc.sql;

/**
 * @since 2.0
 */
public class SqlFragmentException extends RuntimeException {

	private static final long serialVersionUID = 4315911988832212914L;

	public SqlFragmentException() {
	}

	public SqlFragmentException(String message, Throwable cause) {
		super(message, cause);
	}

	public SqlFragmentException(String message) {
		super(message);
	}

	public SqlFragmentException(Throwable cause) {
		super(cause);
	}

}

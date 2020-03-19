/**
 * 
 */
package org.yelong.core.jdbc.sql.exception;

/**
 * @author PengFei
 */
public class SqlFragmentException extends RuntimeException{

	private static final long serialVersionUID = 4315911988832212914L;

	public SqlFragmentException() {

	}

	public SqlFragmentException(String message) {
		super(message);
	}

	public SqlFragmentException(Throwable t) {
		super(t);
	}

}

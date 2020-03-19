/**
 * 
 */
package org.yelong.core.jdbc.sql.exception;

/**
 * @author 彭飞
 * @date 2019年11月1日上午10:30:45
 * @version 1.2
 */
public class SqlFragmentException extends RuntimeException{

	/**
	 * 
	 */
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

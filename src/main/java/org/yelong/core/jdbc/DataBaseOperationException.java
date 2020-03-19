/**
 * 
 */
package org.yelong.core.jdbc;

/**
 * 数据库操作异常
 * @author PengFei
 */
public class DataBaseOperationException extends RuntimeException {

	private static final long serialVersionUID = 3893212257769581602L;

	public DataBaseOperationException() {

	}

	public DataBaseOperationException(String message) {
		super(message);
	}

	public DataBaseOperationException(Throwable t) {
		super(t);
	}

}

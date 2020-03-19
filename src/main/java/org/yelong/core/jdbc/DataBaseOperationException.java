/**
 * 
 */
package org.yelong.core.jdbc;

/**
 * @author pengfei<yl1430834495@163.com>
 * @date 2019年12月5日下午2:53:39
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

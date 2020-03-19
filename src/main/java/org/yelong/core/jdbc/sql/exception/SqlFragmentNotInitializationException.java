/**
 * 
 */
package org.yelong.core.jdbc.sql.exception;

/**
 * sql片段未初始化异常
 * @author 彭飞
 * @date 2019年11月4日下午4:11:36
 * @version 1.2
 */
public class SqlFragmentNotInitializationException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6620005466331650512L;
	
	

	public SqlFragmentNotInitializationException() {
		
	}
	
	public SqlFragmentNotInitializationException(String message) {
		super(message);
	}
	
	public SqlFragmentNotInitializationException(Throwable t) {
		super(t);
	}
	
	

}

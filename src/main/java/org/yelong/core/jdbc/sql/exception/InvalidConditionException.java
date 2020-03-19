/**
 * 
 */
package org.yelong.core.jdbc.sql.exception;

/**
 * 无效的条件异常
 * @author 彭飞
 * @date 2019年8月12日下午12:05:00
 * @version 1.0
 */
public class InvalidConditionException extends SqlFragmentException{

	private static final long serialVersionUID = 445555484097834716L;

	public InvalidConditionException() {
		
	}
	
	public InvalidConditionException(String message) {
		super(message);
	}
	
	public InvalidConditionException(Throwable t) {
		super(t);
	}
	
	
	
}

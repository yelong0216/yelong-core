/**
 * 
 */
package org.yelong.core.jdbc.sql.exception;

/**
 * 无效的条件异常
 * @author PengFei
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

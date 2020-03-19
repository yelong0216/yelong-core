/**
 * 
 */
package org.yelong.core.jdbc.sql.exception;

/**
 * 无效的排序异常
 * @author 彭飞
 * @date 2019年8月18日下午5:38:01
 * @version 1.0
 */
public class InvalidSortException extends SqlFragmentException{

	private static final long serialVersionUID = 3784392702656015031L;

	public InvalidSortException() {
		
	}
	
	public InvalidSortException(String message) {
		super(message);
	}
	
	public InvalidSortException(Throwable t) {
		super(t);
	}
	
	
	
}

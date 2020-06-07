/**
 * 
 */
package org.yelong.core.jdbc.sql.exception;

/**
 * 无效的排序异常
 * 
 * @author PengFei
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

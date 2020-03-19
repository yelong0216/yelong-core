/**
 * 
 */
package org.yelong.core.model.exception;

/**
 * 主键一场
 * 
 * @author PengFei
 * @date 2019年12月29日下午8:52:23
 * @since 1.0
 */
public class PrimaryKeyException extends ModelException{
	
	/**
	 * @date 2019年12月29日下午8:52:58
	 */
	private static final long serialVersionUID = 4686004552688583074L;

	public PrimaryKeyException() {
		
	}
	
	public PrimaryKeyException(String message) {
		super(message);
	}
	
	public PrimaryKeyException(Throwable t) {
		super(t);
	}

}

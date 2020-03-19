/**
 * 
 */
package org.yelong.core.model.exception;

/**
 * 主键异常
 * @author PengFei
 */
public class PrimaryKeyException extends ModelException{
	
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

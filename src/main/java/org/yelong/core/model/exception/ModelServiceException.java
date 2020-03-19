/**
 * 
 */
package org.yelong.core.model.exception;

/**
 * @author PengFei
 */
public class ModelServiceException extends ModelException{

	private static final long serialVersionUID = 500079897249254712L;

	public ModelServiceException(String message) {
		super(message);
	}
	
	public ModelServiceException(Throwable t) {
		super(t);
	}
	
	public ModelServiceException() {
		
	}
	
}

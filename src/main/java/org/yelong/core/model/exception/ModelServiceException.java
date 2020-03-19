/**
 * 
 */
package org.yelong.core.model.exception;

/**
 * @author 彭飞
 * @date 2019年11月1日上午11:56:38
 * @version 1.2
 */
public class ModelServiceException extends ModelException{

	/**
	 * 
	 */
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

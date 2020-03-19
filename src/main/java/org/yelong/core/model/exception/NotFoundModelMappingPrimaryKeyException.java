/**
 * 
 */
package org.yelong.core.model.exception;

/**
 * 没有找到模型映射的主键异常
 * @author PengFei
 */
public class NotFoundModelMappingPrimaryKeyException extends RuntimeException{

	private static final long serialVersionUID = -2144533295765414691L;
	
	
	public NotFoundModelMappingPrimaryKeyException() {
		
	}
	
	public NotFoundModelMappingPrimaryKeyException(String message) {
		super(message);
	}
	
	public NotFoundModelMappingPrimaryKeyException(Throwable t) {
		super(t);
	}
	
}

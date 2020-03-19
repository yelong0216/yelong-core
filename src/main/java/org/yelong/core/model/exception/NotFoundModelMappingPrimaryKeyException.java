/**
 * 
 */
package org.yelong.core.model.exception;

/**
 * 没有找到模型映射的主键异常
 * @author 彭飞
 * @date 2019年8月1日下午7:06:37
 * @version 1.0
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

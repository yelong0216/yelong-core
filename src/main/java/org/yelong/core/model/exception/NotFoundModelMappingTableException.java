/**
 * 
 */
package org.yelong.core.model.exception;

import org.yelong.core.model.annotation.Table;

/**
 * 未找到model映射的表<br/>
 * model对象应该使用{@link Table}注解进行标注这是一个model
 * @author 彭飞
 * @date 2019年8月2日上午8:40:34
 * @version 1.0
 */
public class NotFoundModelMappingTableException extends RuntimeException{
	
	
	private static final long serialVersionUID = -8129112948834680096L;
	
	
	public NotFoundModelMappingTableException() {
		
	}
	
	
	public NotFoundModelMappingTableException(String message) {
		super(message);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}

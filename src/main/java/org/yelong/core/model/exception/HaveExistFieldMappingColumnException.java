/**
 * 
 */
package org.yelong.core.model.exception;

/**
 * 已经存在的字段映射列异常
 * @author 彭飞
 * @date 2019年8月2日上午10:19:05
 * @version 1.0
 */
public class HaveExistFieldMappingColumnException extends RuntimeException{
	
	
	
	
	private static final long serialVersionUID = -5034435636005465884L;


	public HaveExistFieldMappingColumnException() {
		
	}
	
	
	public HaveExistFieldMappingColumnException(String message) {
		super(message);
	}
	
	

}

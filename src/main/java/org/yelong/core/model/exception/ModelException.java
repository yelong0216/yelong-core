/**
 * 
 */
package org.yelong.core.model.exception;

/**
 * 模型类信息异常
 * 
 * @author PengFei
 * @date 2019年9月12日下午4:06:01
 * @version 1.2
 */
public class ModelException extends RuntimeException{

	private static final long serialVersionUID = 8180022466141924432L;
	
	public ModelException() {
		
	}
	
	public ModelException(String message) {
		super(message);
	}
	
	public ModelException(Throwable t) {
		super(t);
	}

}

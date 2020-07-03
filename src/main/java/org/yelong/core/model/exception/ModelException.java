/**
 * 
 */
package org.yelong.core.model.exception;

/**
 * 模型类信息异常
 * 
 * @author PengFei
 */
public class ModelException extends RuntimeException {

	private static final long serialVersionUID = 8180022466141924432L;

	public ModelException() {

	}

	public ModelException(String message) {
		super(message);
	}

	public ModelException(Throwable t) {
		super(t);
	}

	public ModelException(String message, Throwable t) {
		super(message, t);
	}

}

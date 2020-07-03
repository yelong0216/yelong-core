/**
 * 
 */
package org.yelong.core.model.exception;

/**
 * model 属性异常
 * 
 * @author PengFei
 */
public class ModelPropertyException extends ModelException {

	private static final long serialVersionUID = -7440960233579692479L;

	public ModelPropertyException() {

	}

	public ModelPropertyException(String message) {
		super(message);
	}

	public ModelPropertyException(Throwable t) {
		super(t);
	}

	public ModelPropertyException(String message, Throwable t) {
		super(message, t);
	}

}

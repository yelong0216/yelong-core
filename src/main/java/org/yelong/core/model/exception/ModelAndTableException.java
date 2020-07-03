/**
 * 
 */
package org.yelong.core.model.exception;

/**
 * @author PengFei
 *
 */
public class ModelAndTableException extends ModelException {

	private static final long serialVersionUID = -8553563105580433705L;

	public ModelAndTableException() {

	}

	public ModelAndTableException(String message) {
		super(message);
	}

	public ModelAndTableException(Throwable t) {
		super(t);
	}

	public ModelAndTableException(String message, Throwable t) {
		super(message, t);
	}

}

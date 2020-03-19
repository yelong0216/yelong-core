/**
 * 
 */
package org.yelong.core.model.resolve;

import org.yelong.core.model.exception.ModelException;

/**
 * model解析异常
 * 
 * @author PengFei
 * @date 2019年12月29日下午7:43:02
 * @since 1.0
 */
public class ModelResolveException extends ModelException{

	private static final long serialVersionUID = 7157528523515342273L;


	public ModelResolveException() {

	}

	public ModelResolveException(String message) {
		super(message);
	}

	public ModelResolveException(Throwable t) {
		super(t);
	}

}

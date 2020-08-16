package org.yelong.core.model.resolve;

/**
 * 模型解析器异常
 * 
 * @since 2.0
 */
public class ModelResolverException extends RuntimeException {

	private static final long serialVersionUID = 2231335184571358031L;

	public ModelResolverException() {
	}

	public ModelResolverException(String message, Throwable cause) {
		super(message, cause);
	}

	public ModelResolverException(String message) {
		super(message);
	}

	public ModelResolverException(Throwable cause) {
		super(cause);
	}

}

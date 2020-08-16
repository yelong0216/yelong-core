/**
 * 
 */
package org.yelong.core.model.pojo.field;

/**
 * 字段解析器异常
 * 
 * @since 2.0
 */
public class FieldResolverException extends RuntimeException {

	private static final long serialVersionUID = -4731663678154281091L;

	public FieldResolverException() {
		super();
	}

	public FieldResolverException(String message, Throwable cause) {
		super(message, cause);
	}

	public FieldResolverException(String message) {
		super(message);
	}

	public FieldResolverException(Throwable cause) {
		super(cause);
	}

}

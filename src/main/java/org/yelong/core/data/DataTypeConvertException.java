package org.yelong.core.data;

/**
 * 数据类型转换异常
 * 
 * @since 2.1.6
 */
public class DataTypeConvertException extends Exception {

	private static final long serialVersionUID = 1366647665832972440L;

	public DataTypeConvertException() {
		super();
	}

	public DataTypeConvertException(String message, Throwable cause) {
		super(message, cause);
	}

	public DataTypeConvertException(String message) {
		super(message);
	}

	public DataTypeConvertException(Throwable cause) {
		super(cause);
	}

}

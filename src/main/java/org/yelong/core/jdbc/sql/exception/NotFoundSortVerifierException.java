/**
 * 
 */
package org.yelong.core.jdbc.sql.exception;

/**
 * 未定义排序验证器异常
 * @author 彭飞
 * @date 2019年8月18日下午5:40:43
 * @version 1.0
 */
public class NotFoundSortVerifierException extends SqlFragmentException{
	
	
	private static final long serialVersionUID = 8148883319238683020L;

	public NotFoundSortVerifierException() {

	}

	public NotFoundSortVerifierException(String message) {
		super(message);
	}

	public NotFoundSortVerifierException(Throwable t) {
		super(t);
	}
	
}

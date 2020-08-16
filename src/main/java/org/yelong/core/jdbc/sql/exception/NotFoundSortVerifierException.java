/**
 * 
 */
package org.yelong.core.jdbc.sql.exception;

import org.yelong.core.jdbc.sql.SqlFragmentException;

/**
 * 未定义排序验证器异常
 */
public class NotFoundSortVerifierException extends SqlFragmentException {

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

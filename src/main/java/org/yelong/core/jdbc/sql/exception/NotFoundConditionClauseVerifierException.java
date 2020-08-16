/**
 * 
 */
package org.yelong.core.jdbc.sql.exception;

import org.yelong.core.jdbc.sql.SqlFragmentException;

/**
 * 未定义条件验证器
 */
public class NotFoundConditionClauseVerifierException extends SqlFragmentException {

	private static final long serialVersionUID = -688652983327294776L;

	public NotFoundConditionClauseVerifierException() {

	}

	public NotFoundConditionClauseVerifierException(String message) {
		super(message);
	}

	public NotFoundConditionClauseVerifierException(Throwable t) {
		super(t);
	}

}

/**
 * 
 */
package org.yelong.core.jdbc.sql.exception;

/**
 * 未定义条件验证器
 * @author 彭飞
 * @date 2019年8月13日下午2:38:56
 * @version 1.0
 */
public class NotFoundConditionClauseVerifierException extends SqlFragmentException{

	/**
	 * 
	 */
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

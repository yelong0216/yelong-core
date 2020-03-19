/**
 * 
 */
package org.yelong.core.jdbc.sql.condition.support;

/**
 * 条件解析异常
 * 
 * @author PengFei
 * @date 2020年2月25日上午11:57:48
 * @since 1.0
 */
public class ConditionResolverException extends RuntimeException{

	/**
	 * @date 2020年2月25日上午11:58:33
	 */
	private static final long serialVersionUID = 521560169324312675L;
	
	public ConditionResolverException() {
		
	}
	
	public ConditionResolverException(String message) {
		super(message);
	}

}

/**
 * 
 */
package org.yelong.core.jdbc.sql.condition.support;

/**
 * 条件解析异常
 * @author PengFei
 */
public class ConditionResolverException extends RuntimeException{

	private static final long serialVersionUID = 521560169324312675L;
	
	public ConditionResolverException() {
		
	}
	
	public ConditionResolverException(String message) {
		super(message);
	}

}

/**
 * 
 */
package org.yelong.core.jdbc.sql.condition;

import org.yelong.core.jdbc.SqlKeyword;

/**
 * 条件拼接方式
 * @author PengFei
 */
public enum ConditionConnectWay {
	
	AND(SqlKeyword.AND.getKeyword()),
	
	OR(SqlKeyword.OR.getKeyword());
	
	private final String keyword;
	
	private ConditionConnectWay(String keyword) {
		this.keyword = keyword;
	}

	public String getKeyword() {
		return keyword;
	}
	
}

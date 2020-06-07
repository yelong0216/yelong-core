/**
 * 
 */
package org.yelong.core.jdbc;

/**
 * SQL 保留关键字
 * 
 * @author PengFei
 */
public enum SqlKeyword {

	AND("AND"),
	OR("OR"),
	IN("IN"),
	NOT("NOT"),
	LIKE("LIKE"),
	EQ("="),
	NE("<>"),
	GT(">"),
	GE(">="),
	LT("<"),
	LE("<="),
	IS_NULL("IS NULL"),
	IS_NOT_NULL("IS NOT NULL"),
	GROUP_BY("GROUP BY"),
	HAVING("HAVING"),
	ORDER_BY("ORDER BY"),
	EXISTS("EXISTS"),
	BETWEEN("BETWEEN"),
	ASC("ASC"),
	DESC("DESC");

	private final String keyword;

	SqlKeyword(final String keyword) {
		this.keyword = keyword;
	}

	public String getKeyword() {
		return keyword;
	}

	/**
	 * 是否存在keyword关键字
	 * 
	 * @param keyword 关键字
	 * @return <tt>true</tt> 如果关键字存在
	 */
	public static boolean test(String keyword) {
		for (SqlKeyword sqlKeyword : values()) {
			if( sqlKeyword.getKeyword().equalsIgnoreCase(keyword) ) {
				return true;
			}
		}
		return false;
	}

}

/**
 * 
 */
package org.yelong.core.jdbc;

/**
 * SQL 保留关键字
 */
public enum SqlKeyword {

	/** 且 */
	AND("AND"),
	/** 或者 */
	OR("OR"),
	/** 包含 */
	IN("IN"),
	/** 非 */
	NOT("NOT"),
	/** like */
	LIKE("LIKE"),
	/** 等于 */
	EQ("="),
	/** 不等于 */
	NE("<>"),
	/** 大于 */
	GT(">"),
	/** 大于等于 */
	GE(">="),
	/** 小于 */
	LT("<"),
	/** 小于等于 */
	LE("<="),
	/** 为空 */
	IS_NULL("IS NULL"),
	/** 非空 */
	IS_NOT_NULL("IS NOT NULL"),
	/** 分组 */
	GROUP_BY("GROUP BY"),
	/** 分组条件 */
	HAVING("HAVING"),
	/** 排序 */
	ORDER_BY("ORDER BY"),
	/** 存在 */
	EXISTS("EXISTS"),
	/** 两者间 */
	BETWEEN("BETWEEN"),
	/** 正序 */
	ASC("ASC"),
	/** 倒叙 */
	DESC("DESC");

	private final String keyword;

	SqlKeyword(final String keyword) {
		this.keyword = keyword;
	}

	public String getKeyword() {
		return keyword;
	}

	/**
	 * 是否是keyword关键字
	 * 
	 * @param keyword 关键字
	 * @return <tt>true</tt> 如果关键字存在
	 */
	public static boolean test(String keyword) {
		for (SqlKeyword sqlKeyword : values()) {
			if (sqlKeyword.getKeyword().equalsIgnoreCase(keyword)) {
				return true;
			}
		}
		return false;
	}

}

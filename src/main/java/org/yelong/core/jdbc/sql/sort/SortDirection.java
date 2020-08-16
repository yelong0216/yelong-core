package org.yelong.core.jdbc.sql.sort;

/**
 * 排序方向
 * 
 * @since 1.3
 */
public enum SortDirection {

	/** 降序 */
	ASC("ASC"),

	/** 升序 */
	DESC("DESC");

	public final String KEYWORD;

	private SortDirection(final String keyword) {
		this.KEYWORD = keyword;
	}

	/**
	 * 根据排序方向获取排序方向对象
	 * 
	 * @param keyword 排序方向关键字
	 * @return 排序方向的对象
	 */
	public static final SortDirection valueOfByDirection(String keyword) {
		for (SortDirection sortDirection : SortDirection.values()) {
			if (keyword.toUpperCase().equals(sortDirection.KEYWORD)) {
				return sortDirection;
			}
		}
		return null;
	}

	/**
	 * 测试是否存在此排序方向
	 * 
	 * @param direction 排序方向
	 * @return <tt>true</tt>如果存在此排序方向
	 */
	public static final boolean test(String direction) {
		if (null == valueOfByDirection(direction)) {
			return false;
		}
		return true;
	}

}

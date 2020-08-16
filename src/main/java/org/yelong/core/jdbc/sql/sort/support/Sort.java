package org.yelong.core.jdbc.sql.sort.support;

import org.yelong.core.jdbc.sql.sort.SortDirection;

/**
 * 排序
 */
public class Sort {

	/** 降序 */
	public static final String ASC = SortDirection.ASC.KEYWORD;

	/** 升序 */
	public static final String DESC = SortDirection.DESC.KEYWORD;

	private final String column;

	private final String direction;

	/**
	 * 默认为降序
	 * 
	 * @param column 列名
	 */
	public Sort(String column) {
		this(column, ASC);
	}

	/**
	 * @param column    列名
	 * @param direction 排序方向
	 */
	public Sort(String column, String direction) {
		this.column = column;
		this.direction = direction;
	}

	/**
	 * @param column        列名
	 * @param sortDirection 排序方向
	 */
	public Sort(String column, SortDirection sortDirection) {
		this(column, sortDirection.KEYWORD);
	}

	public String getColumn() {
		return column;
	}

	public String getDirection() {
		return direction;
	}

}

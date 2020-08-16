/**
 * 
 */
package org.yelong.core.jdbc.sql.exception;

import org.yelong.core.jdbc.sql.SqlFragmentException;

/**
 * 无效的排序异常
 */
public class InvalidSortException extends SqlFragmentException {

	private static final long serialVersionUID = 3784392702656015031L;

	public InvalidSortException() {

	}

	public InvalidSortException(String message) {
		super(message);
	}

	public InvalidSortException(Throwable t) {
		super(t);
	}

}

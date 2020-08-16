/**
 * 
 */
package org.yelong.core.jdbc;

import java.sql.SQLException;

import org.yelong.core.annotation.Nullable;

/**
 * 数据库操作异常
 */
public class DataBaseOperationException extends RuntimeException {

	private static final long serialVersionUID = 3893212257769581602L;

	@Nullable
	private SQLException sqlException;

	public DataBaseOperationException(String message) {
		super(message);
	}

	public DataBaseOperationException(SQLException sqlException) {
		super(sqlException);
		this.sqlException = sqlException;
	}

	public DataBaseOperationException(String message, SQLException sqlException) {
		super(message, sqlException);
		this.sqlException = sqlException;
	}

}

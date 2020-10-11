package org.yelong.core.jdbc.database.backup;

/**
 * 数据库备份异常
 */
public class DatabaseBackupException extends Exception {

	private static final long serialVersionUID = -6639713073851928321L;

	public DatabaseBackupException() {
		super();
	}

	public DatabaseBackupException(String message, Throwable cause) {
		super(message, cause);
	}

	public DatabaseBackupException(String message) {
		super(message);
	}

	public DatabaseBackupException(Throwable cause) {
		super(cause);
	}

}

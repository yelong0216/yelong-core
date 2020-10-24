package test.org.yelong.core.jdbc.database.backup;

import java.io.IOException;
import java.util.Properties;

import org.yelong.commons.lang.runtime.CommandExecuteResult;
import org.yelong.core.jdbc.database.backup.DatabaseBackup;
import org.yelong.core.jdbc.database.backup.DatabaseBackupConfig;
import org.yelong.core.jdbc.database.backup.DatabaseBackupException;
import org.yelong.core.jdbc.dialect.impl.mysql.MysqlDatabaseBackup;

public class DatabaseBackupTest {

	public static DatabaseBackup databaseBackup = new MysqlDatabaseBackup();

	public static void main(String[] args) throws DatabaseBackupException, IOException {
		Properties properties = new Properties();
		properties.put(MysqlDatabaseBackup.MYSQLDUMP_BIN_PATH_NAME, "D:/mysql/MySQL Server 5.5/bin");
		DatabaseBackupConfig databaseBackupConfig = new DatabaseBackupConfig("root", "123456", "localhost", "K:\\");
		CommandExecuteResult commandExecuteResult = databaseBackup.backup(databaseBackupConfig, "test", "co_dict", properties);
		System.out.println(commandExecuteResult.getErrorInfo());
		System.out.println("----------------");
		System.out.println(commandExecuteResult.getResultInfo());
	}

}

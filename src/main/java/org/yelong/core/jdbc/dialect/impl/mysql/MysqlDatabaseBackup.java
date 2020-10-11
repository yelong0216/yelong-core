package org.yelong.core.jdbc.dialect.impl.mysql;

import java.io.File;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.yelong.commons.io.FilenameUtilsE;
import org.yelong.commons.lang.Strings;
import org.yelong.commons.lang.runtime.CommandExecuteResult;
import org.yelong.core.annotation.Nullable;
import org.yelong.core.jdbc.database.backup.AbstractDatabaseBackup;
import org.yelong.core.jdbc.database.backup.DatabaseBackup;
import org.yelong.core.jdbc.database.backup.DatabaseBackupConfig;
import org.yelong.core.jdbc.database.backup.DatabaseBackupException;

/**
 * mysql备份使用 mysqldump 命令。如果该命令没有设置到classpath中时需要在
 * {@link Properties}{@link #MYSQLDUMP_BIN_PATH_NAME} 设置mysqldump命令文件所在的目录
 * 
 */
public class MysqlDatabaseBackup extends AbstractDatabaseBackup {

	public static final DatabaseBackup INSTANCE = new MysqlDatabaseBackup();
	
	/**mysqldump所在目录*/
	public static final String MYSQLDUMP_BIN_PATH_NAME = "mysqldumpBinPath";

	@Override
	public CommandExecuteResult backup(DatabaseBackupConfig databaseBackupConfig, String databaseName,
			Properties properties) throws DatabaseBackupException {
		return _backup(databaseBackupConfig, databaseName, null, properties);
	}

	@Override
	public CommandExecuteResult backup(DatabaseBackupConfig databaseBackupConfig, String databaseName, String tableName,
			Properties properties) throws DatabaseBackupException {
		Strings.requireNonBlank(tableName);
		return _backup(databaseBackupConfig, databaseName, tableName, properties);
	}

	public CommandExecuteResult _backup(DatabaseBackupConfig databaseBackupConfig, String databaseName,
			@Nullable String tableName, Properties properties) throws DatabaseBackupException {
		String username = Strings.requireNonBlank(databaseBackupConfig.getUsername());
		if (null == properties) {
			properties = new Properties();
		}
		String password = databaseBackupConfig.getPassword();
		String mysqldumpBinPath = properties.getProperty(MYSQLDUMP_BIN_PATH_NAME);
		String address = Strings.requireNonBlank(databaseBackupConfig.getAddress());
		String savePath = Strings.requireNonBlank(databaseBackupConfig.getSavePath());
		String fileName = databaseBackupConfig.getFileName();
		if (StringUtils.isBlank(fileName)) {
			if (StringUtils.isNotBlank(tableName)) {
				fileName = tableName + ".sql";
			} else {
				fileName = databaseName + ".sql";
			}
		}
		String saveFile = FilenameUtilsE.getFilePath(savePath, fileName);
		StringBuffer command = new StringBuffer();
		command.append("mysqldump ");
		command.append("--opt ");
		command.append("-h ");
		command.append(address);
		command.append(" ");
		command.append("--user=");
		command.append(username);
		command.append(" ");
		command.append("--password=");
		command.append(password);
		command.append(" ");
		command.append("--lock-all-tables=true ");
		command.append("--result-file=");
		command.append(saveFile);
		command.append(" ");
		command.append("--default-character-set=utf8 ");
		command.append(databaseName);
		if (StringUtils.isNotBlank(tableName)) {
			command.append(" ");
			command.append(tableName);
		}
		try {
			if (StringUtils.isBlank(mysqldumpBinPath)) {
				return commandExecutor.execute(command.toString());
			} else {
				return commandExecutor.execute(command.toString(), new File(mysqldumpBinPath));
			}
		} catch (Exception e) {
			throw new DatabaseBackupException(e);
		}
	}

}

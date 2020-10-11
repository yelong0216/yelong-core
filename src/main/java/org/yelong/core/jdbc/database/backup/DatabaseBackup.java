package org.yelong.core.jdbc.database.backup;

import java.util.Properties;

import org.yelong.commons.lang.runtime.CommandExecuteResult;
import org.yelong.commons.lang.runtime.CommandExecutor;
import org.yelong.core.annotation.Nullable;

/**
 * 数据库备份。备份数据库使用命令行的方式
 */
public interface DatabaseBackup {

	/**
	 * 备份数据库
	 * 
	 * @param databaseBackupConfig 数据库备份配置
	 * @param databaseName         需要备份的数据库名称
	 * @param properties           备份数据库时的其他配置属性
	 * @throws DatabaseBackupException 数据库备份异常
	 */
	CommandExecuteResult backup(DatabaseBackupConfig databaseBackupConfig, String databaseName,
			@Nullable Properties properties) throws DatabaseBackupException;

	/**
	 * 备份数据库中指定的表
	 * 
	 * @param databaseBackupConfig 数据库备份配置
	 * @param databaseName         需要备份的数据库名称
	 * @param tableName            备份的表名称
	 * @param properties           备份数据库时的其他配置属性
	 * @throws DatabaseBackupException 数据库备份异常
	 */
	CommandExecuteResult backup(DatabaseBackupConfig databaseBackupConfig, String databaseName, String tableName,
			@Nullable Properties properties) throws DatabaseBackupException;

	/**
	 * 设置命令行执行器
	 * 
	 * @param commandExecutor 命令行执行器
	 */
	void setCommandExecutor(CommandExecutor commandExecutor);

	/**
	 * @return 命令行执行器
	 */
	CommandExecutor getCommandExecutor();

}

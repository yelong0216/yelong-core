package org.yelong.core.jdbc.database.backup;

import org.yelong.core.annotation.Nullable;

/**
 * 数据库备份配置
 */
public class DatabaseBackupConfig {

	/** 用户名称 */
	private String username;

	/** 用户密码 */
	private String password;

	/** mysql 服务地址 */
	private String address;

	/** 备份后文件存放的目录 */
	private String savePath;

	/**
	 * 备份到的文件名称。如果为 <code>null</code>使用默认的名称，该名称由实现类来定义
	 */
	@Nullable
	private String fileName;

	public DatabaseBackupConfig() {
	}

	public DatabaseBackupConfig(String username, String password, String address, String savePath) {
		this.username = username;
		this.password = password;
		this.address = address;
		this.savePath = savePath;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getSavePath() {
		return savePath;
	}

	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}

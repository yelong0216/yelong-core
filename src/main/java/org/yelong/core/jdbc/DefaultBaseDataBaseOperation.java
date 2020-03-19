/**
 * 
 */
package org.yelong.core.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.commons.lang3.ClassUtils;

/**
 * 使用jdbc连接操作数据库
 * @author 彭飞
 * @date 2019年7月16日下午12:57:59
 */
public class DefaultBaseDataBaseOperation extends AbstractBaseDataBaseOperation{

	private String url;
	private String username;
	private String password;
	
	public DefaultBaseDataBaseOperation(DataSourceProperties dataSourceProperties) throws ClassNotFoundException {
		this(dataSourceProperties.getDriverClassName(),
				dataSourceProperties.getUrl(),
				dataSourceProperties.getUsername(),
				dataSourceProperties.getPassword());
	}
	
	public DefaultBaseDataBaseOperation(String driver, String url, String username, String password) throws ClassNotFoundException {
		this.url = url;
		this.username = username;
		this.password = password;
		ClassUtils.getClass(driver);
	}

	

	@Override
	public Connection getConnection(){
		try {
			return DriverManager.getConnection(url, username, password);
		} catch (Exception e) {
			throw new DataBaseOperationException(e);
		}
	}

	@Override
	public void afterExecute(Connection conn, Object result, DataBaseOperationType operationType) throws SQLException {
		if( null != conn) {
			conn.close();
		}
	}
	
}

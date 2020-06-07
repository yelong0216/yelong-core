/**
 * 
 */
package org.yelong.core.jdbc;

/**
 * 数据库属性配置
 * 
 * @author PengFei
 */
public class DataSourceProperties {

	private String url;
	
	private String username;
	
	private String password;

	private String driverClassName;

	public DataSourceProperties() {
		
	}
	
	public DataSourceProperties(String url, String username, String password, String driverClassName) {
		this.url = url;
		this.username = username;
		this.password = password;
		this.driverClassName = driverClassName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
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

	public String getDriverClassName() {
		return driverClassName;
	}

	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}
	
}

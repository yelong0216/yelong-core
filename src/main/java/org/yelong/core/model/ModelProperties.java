/**
 * 
 */
package org.yelong.core.model;

/**
 * @author 彭飞
 * @date 2019年10月30日下午5:24:48
 * @version 1.2
 */
public class ModelProperties {

	/**
	 * 数据库方言
	 */
	private String databaseDialect;
	
	/**
	 * 下划线转换为驼峰
	 */
	@Deprecated
	private boolean underscoreToCamelCase;

	/**
	 * 驼峰转换为下划线
	 */
	private boolean camelCaseToUnderscore;

	
	public String getDatabaseDialect() {
		return databaseDialect;
	}

	public void setDatabaseDialect(String databaseDialect) {
		this.databaseDialect = databaseDialect;
	}

	@Deprecated
	public boolean isUnderscoreToCamelCase() {
		return underscoreToCamelCase;
	}

	@Deprecated
	public void setUnderscoreToCamelCase(boolean underscoreToCamelCase) {
		this.underscoreToCamelCase = underscoreToCamelCase;
	}

	public boolean isCamelCaseToUnderscore() {
		return camelCaseToUnderscore;
	}

	public void setCamelCaseToUnderscore(boolean camelCaseToUnderscore) {
		this.camelCaseToUnderscore = camelCaseToUnderscore;
	}
	
	 
	
}

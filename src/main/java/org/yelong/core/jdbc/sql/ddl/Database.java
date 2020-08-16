/**
 * 
 */
package org.yelong.core.jdbc.sql.ddl;

import org.yelong.commons.lang.Strings;

/**
 * 数据库
 * 
 * @since 1.1
 */
public class Database {

	private String name;

	public Database(String name) {
		this.name = Strings.requireNonBlank(name);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}

}

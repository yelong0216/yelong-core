/**
 * 
 */
package org.yelong.core.jdbc.dialect;

/**
 * 数据库方言工厂
 */
public class DialectFactory {

	/**
	 * 根据数据库连接url获取数据库方言
	 * 
	 * @param url
	 * @return 数据库方言
	 */
	public static Dialect createByUrl(String url) {
		for (Dialects dialect : Dialects.values()) {
			if (url.indexOf(":" + dialect.name().toLowerCase() + ":") != -1) {
				return dialect.getDialect();
			}
		}
		return null;
	}

}

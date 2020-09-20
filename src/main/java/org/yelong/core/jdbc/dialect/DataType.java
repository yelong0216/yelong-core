package org.yelong.core.jdbc.dialect;

/**
 * 数据库中的数据类型
 * 
 * @since 2.1.2
 */
public interface DataType {

	/**
	 * @param dataType 数据库数据类型
	 * @return 数据库数据类型默认映射的Java数据类型
	 */
	Class<?> getDefaultMappingJavaType(String dataType);

	/**
	 * @param javaType Java数据类型
	 * @return Java数据类型默认映射的数据库数据类型
	 */
	String getDefaultMappingDatabaseDataType(Class<?> javaType);

}

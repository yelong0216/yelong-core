/**
 * 
 */
package org.yelong.core.jdbc.dialect.impl.oracle;

import java.util.Date;

import org.yelong.core.jdbc.dialect.DataType;

/**
 * @since 2.1.2
 */
public class OracleDataType implements DataType {

	public static final DataType INSTANCE = new OracleDataType();

	@Override
	public Class<?> getDefaultMappingJavaType(String dataType) {
		switch (dataType) {
		case "varchar2":
		case "text":
		case "mediumtext":
		case "longtext":
			return String.class;
		case "int":
		case "integer":
			return Integer.class;
		case "double":
		case "float":
			return Double.class;
		case "timestamp":
			return Date.class;
		default:
			return null;
		}
	}

	@Override
	public String getDefaultMappingDatabaseDataType(Class<?> javaType) {
		String javaTypeName = javaType.getName();
		switch (javaTypeName) {
		case "java.lang.Byte":
		case "java.lang.Stort":
		case "java.lang.Integer":
		case "java.lang.Long":
			return "integer";
		case "java.lang.Float":
		case "java.lang.Double":
			return "double";
		case "java.lang.String":
			return "varchar2";
		case "java.util.Date":
			return "timestamp";
		default:
			return null;
		}
	}

}

/**
 * 
 */
package org.yelong.core.jdbc.dialect.impl.mysql;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.yelong.core.jdbc.dialect.DataType;

/**
 * mysql数据类型
 * 
 * @since 1.1
 */
public class MySqlDataType implements DataType {

	public static final DataType INSTANCE = new MySqlDataType();
	
	protected static final List<String> DATA_TYPES = new ArrayList<>();

	static {
		DATA_TYPES.add("varchar");
		DATA_TYPES.add("int");
		DATA_TYPES.add("integer");
		DATA_TYPES.add("double");
		DATA_TYPES.add("float");
		DATA_TYPES.add("timestamp");
		DATA_TYPES.add("blob");
		DATA_TYPES.add("text");
		DATA_TYPES.add("mediumtext");
		DATA_TYPES.add("longtext");
	}

	public static List<String> getDataTypes() {
		return Collections.unmodifiableList(DATA_TYPES);
	}

	@Override
	public Class<?> getDefaultMappingJavaType(String dataType) {
		switch (dataType) {
		case "varchar":
		case "text":
		case "mediumtext":
		case "longtext":
			return String.class;
		case "int":
		case "integer":
			return Integer.class;
		case "double":
		case "float": 
		case "decimal":
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
			return "int";
		case "java.lang.Float":
		case "java.lang.Double":
			return "double";
		case "java.lang.String":
			return "varchar";
		case "java.util.Date":
			return "timestamp";
		default:
			return null;
		}
	}

}

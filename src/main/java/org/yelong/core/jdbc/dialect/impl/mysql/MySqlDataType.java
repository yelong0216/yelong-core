/**
 * 
 */
package org.yelong.core.jdbc.dialect.impl.mysql;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * mysql数据类型
 * 
 * @since 1.1
 */
public class MySqlDataType {

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

}

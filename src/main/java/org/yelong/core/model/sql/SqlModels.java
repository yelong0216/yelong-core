package org.yelong.core.model.sql;

import java.util.Arrays;
import java.util.List;

/**
 * SqlModel工具类
 * 
 * @since 2.1.6
 */
public final class SqlModels {

	// 需要忽略的字段
	public static final List<String> SQLMODEL_FIELDNAMES;

	static {
		SQLMODEL_FIELDNAMES = Arrays.asList("conditionOperators", "extendAttributes", "sortFields", "groupColumns",
				"conditions", "modelClass", "model");
	}

	private SqlModels() {
	}

	/**
	 * @return SqlModel中所有的字段名称
	 */
	public static List<String> getSqlModelFieldNames() {
		return SQLMODEL_FIELDNAMES;
	}
	
	/**
	 * @return SqlModel中所有的字段名称
	 */
	public static String [] getSqlModelFieldNameArray() {
		return SQLMODEL_FIELDNAMES.toArray(new String[SQLMODEL_FIELDNAMES.size()]);
	}

}

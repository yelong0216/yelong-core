/**
 * 
 */
package org.yelong.core.model.pojo.field;

import org.yelong.core.model.Modelable;

/**
 * 字段解析范围
 * 
 * @since 2.0
 */
public class FieldResolveScopePOJO {

	private final Class<? extends Modelable> modelClass;

	private final String[] fieldNames;

	public FieldResolveScopePOJO(Class<? extends Modelable> modelClass, String[] fieldNames) {
		this.modelClass = modelClass;
		this.fieldNames = fieldNames;
	}

	public Class<? extends Modelable> getModelClass() {
		return modelClass;
	}

	public String[] getFieldNames() {
		return fieldNames;
	}

}

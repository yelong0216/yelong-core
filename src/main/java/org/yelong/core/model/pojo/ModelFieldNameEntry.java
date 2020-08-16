package org.yelong.core.model.pojo;

import java.util.Map;

import org.yelong.core.model.Modelable;

/**
 * 模型与字段名称的实体。该类重写了 {@link #equals(Object)} {@link #hashCode()} 保证在
 * {@link Map}中当作 Key值保持唯一
 * 
 * @since 2.0
 */
final class ModelFieldNameEntry {

	private final Class<? extends Modelable> modelClass;

	private final String fieldName;

	public ModelFieldNameEntry(Class<? extends Modelable> modelClass, String fieldName) {
		this.modelClass = modelClass;
		this.fieldName = fieldName;
	}

	@Override
	public int hashCode() {
		return modelClass.getName().hashCode() ^ fieldName.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ModelFieldNameEntry) {
			ModelFieldNameEntry o = (ModelFieldNameEntry) obj;
			return modelClass.equals(o.modelClass) && fieldName.equals(o.fieldName);
		}
		return false;
	}

	@Override
	public String toString() {
		return modelClass + "." + fieldName;
	}

}

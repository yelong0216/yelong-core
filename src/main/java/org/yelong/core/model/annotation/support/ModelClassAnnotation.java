/**
 * 
 */
package org.yelong.core.model.annotation.support;

import java.lang.reflect.Field;
import java.util.Objects;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.yelong.commons.lang.annotation.AnnotationUtilsE;
import org.yelong.commons.support.Entry;
import org.yelong.core.annotation.Nullable;
import org.yelong.core.model.Modelable;
import org.yelong.core.model.annotation.Count;
import org.yelong.core.model.annotation.Delete;
import org.yelong.core.model.annotation.ExtendColumns;
import org.yelong.core.model.annotation.ExtendTable;
import org.yelong.core.model.annotation.FACWrapperAppointMap;
import org.yelong.core.model.annotation.FACWrapperAppoints;
import org.yelong.core.model.annotation.PrimaryKeys;
import org.yelong.core.model.annotation.Select;
import org.yelong.core.model.annotation.Table;
import org.yelong.core.model.annotation.Transients;
import org.yelong.core.model.annotation.View;
import org.yelong.core.model.manage.wrapper.FieldAndColumnWrapper;
import org.yelong.core.model.sql.SelectSqlColumnMode;

/**
 * model 类所支持的注解以及可以获取注解的值。
 * 
 * @since 2.0
 */
public class ModelClassAnnotation {

	private final Class<? extends Modelable> modelClass;

	@Nullable
	private final Table table;

	@Nullable
	private final Select select;

	@Nullable
	private final Delete delete;

	@Nullable
	private final Count count;

	@Nullable
	private final Entry<Class<?>, Transients> transientsEntry;

	@Nullable
	private final Entry<Class<?>, PrimaryKeys> primaryKeysEntry;

	@Nullable
	private final Entry<Class<?>, ExtendColumns> extendColumnsEntry;

	@Nullable
	private final FACWrapperAppoints fieldAndColumnWrapperAppoints;

	@Nullable
	private final View view;

	/**
	 * @param modelClass model class
	 */
	public ModelClassAnnotation(final Class<? extends Modelable> modelClass) {
		this.modelClass = Objects.requireNonNull(modelClass);
		this.table = AnnotationUtilsE.getAnnotation(modelClass, Table.class, true);
		this.select = AnnotationUtilsE.getAnnotation(modelClass, Select.class, true);
		this.delete = AnnotationUtilsE.getAnnotation(modelClass, Delete.class, true);
		this.count = AnnotationUtilsE.getAnnotation(modelClass, Count.class, true);
		this.transientsEntry = AnnotationUtilsE.getAnnotationEntry(modelClass, Transients.class, true);
		this.primaryKeysEntry = AnnotationUtilsE.getAnnotationEntry(modelClass, PrimaryKeys.class, true);
		this.extendColumnsEntry = AnnotationUtilsE.getAnnotationEntry(modelClass, ExtendColumns.class, true);
		this.fieldAndColumnWrapperAppoints = AnnotationUtilsE.getAnnotation(modelClass, FACWrapperAppoints.class, true);
		this.view = AnnotationUtilsE.getAnnotation(modelClass, View.class, true);
	}

	/**
	 * @return 表名
	 */
	@Nullable
	public String getTableName() {
		return null == table ? null : table.value();
	}

	/**
	 * 别名
	 * 
	 * @return
	 */
	@Nullable
	public String getTableAlias() {
		if (null == table) {
			return null;
		}
		String alias = table.alias();
		if (StringUtils.isBlank(alias)) {
			alias = modelClass.getSimpleName().substring(0, 1).toLowerCase() + modelClass.getSimpleName().substring(1);
		}
		return alias;
	}

	/**
	 * @return 表说明
	 */
	@Nullable
	public String getTableDese() {
		return null == table ? null : table.desc();
	}

	/**
	 * @return count sql
	 */
	@Nullable
	public String getCountSql() {
		return null == count ? null : count.value();
	}

	/**
	 * @return select sql
	 */
	@Nullable
	public String getSelectSql() {
		return null == select ? null : select.value();
	}

	/**
	 * @return delete sql
	 */
	@Nullable
	public String getDeleteSql() {
		return null == delete ? null : delete.value();
	}

	/**
	 * @return 查询列模式
	 */
	@Nullable
	public SelectSqlColumnMode getSelectSqlColumnMode() {
		return null == select ? null : select.columnMode();
	}

	public Class<? extends Modelable> getModelClass() {
		return modelClass;
	}

	public Table getTable() {
		return table;
	}

	@Nullable
	public Select getSelect() {
		return select;
	}

	@Nullable
	public Count getCount() {
		return count;
	}

	@Nullable
	public Delete getDelete() {
		return delete;
	}
	
	@Nullable
	public Entry<Class<?>, Transients> getTransientsEntry() {
		return transientsEntry;
	}

	public Entry<Class<?>, PrimaryKeys> getPrimaryKeysEntry() {
		return primaryKeysEntry;
	}

	public Entry<Class<?>, ExtendColumns> getExtendColumnsEntry() {
		return extendColumnsEntry;
	}

	@Nullable
	public FACWrapperAppoints getFieldAndColumnWrapperAppoints() {
		return fieldAndColumnWrapperAppoints;
	}

	@Nullable
	public View getView() {
		return view;
	}

	/**
	 * 通过{@link FACWrapperAppoints}注解获取指定字段指派的字段列包装器
	 * 
	 * @param fieldName 字段名称
	 * @return 字段列包装器类型
	 */
	@Nullable
	public Class<? extends FieldAndColumnWrapper> getFieldAndColumnWrapperClass(String fieldName) {
		Objects.requireNonNull(fieldName);
		if (null == fieldAndColumnWrapperAppoints) {
			return null;
		}
		FACWrapperAppointMap[] fieldAndColumnWrapperAppointMaps = fieldAndColumnWrapperAppoints.value();
		if (ArrayUtils.isEmpty(fieldAndColumnWrapperAppointMaps)) {
			return null;
		}
		for (FACWrapperAppointMap fieldAndColumnWrapperAppointMap : fieldAndColumnWrapperAppointMaps) {
			if (fieldName.equals(fieldAndColumnWrapperAppointMap.fieldName())) {
				return fieldAndColumnWrapperAppointMap.appoint().value();
			}
		}
		return null;
	}

	public boolean isTransient(Field field) {
		Objects.requireNonNull(field);
		if (null == transientsEntry) {
			return false;
		}
		Class<?> modelClass = transientsEntry.getKey();
		if (modelClass.isAssignableFrom(field.getDeclaringClass())) {
			return false;
		}
		Transients transients = transientsEntry.getValue();
		String[] fieldNames = transients.value();
		return ArrayUtils.contains(fieldNames, field.getName());
	}

	public boolean isTransient(String fieldName) {
		Objects.requireNonNull(fieldName);
		if (null == transientsEntry) {
			return false;
		}
		Transients transients = transientsEntry.getValue();
		String[] fieldNames = transients.value();
		return ArrayUtils.contains(fieldNames, fieldName);
	}

	public boolean isPrimaryKey(Field field) {
		Objects.requireNonNull(field);
		if (null == primaryKeysEntry) {
			return false;
		}
		Class<?> modelClass = primaryKeysEntry.getKey();
		if (modelClass.isAssignableFrom(field.getDeclaringClass())) {
			return false;
		}
		PrimaryKeys primaryKeys = primaryKeysEntry.getValue();
		String[] fieldNames = primaryKeys.value();
		return ArrayUtils.contains(fieldNames, field.getName());
	}

	public boolean isPrimaryKey(String fieldName) {
		Objects.requireNonNull(fieldName);
		if (null == primaryKeysEntry) {
			return false;
		}
		PrimaryKeys primaryKeys = primaryKeysEntry.getValue();
		String[] fieldNames = primaryKeys.value();
		return ArrayUtils.contains(fieldNames, fieldName);
	}

	public boolean isExtendColumn(Field field) {
		Objects.requireNonNull(field);
		// 先判断是不是拓展表
		if (field.getDeclaringClass().isAnnotationPresent(ExtendTable.class)) {
			return true;
		}
		if (null == extendColumnsEntry) {
			return false;
		}
		Class<?> modelClass = extendColumnsEntry.getKey();
		// 只有该字段所在的类型及其子类的该注解才会起作用
		if (modelClass.isAssignableFrom(field.getDeclaringClass())) {
			return false;
		}
		ExtendColumns extendColumns = extendColumnsEntry.getValue();
		String[] fieldNames = extendColumns.value();
		return ArrayUtils.contains(fieldNames, field.getName());
	}

	public boolean isExtendColumn(String fieldName) {
		Objects.requireNonNull(fieldName);
		if (null == extendColumnsEntry) {
			return false;
		}
		// 只有该字段所在的类型及其子类的该注解才会起作用
		ExtendColumns extendColumns = extendColumnsEntry.getValue();
		String[] fieldNames = extendColumns.value();
		return ArrayUtils.contains(fieldNames, fieldName);
	}

	public boolean isView() {
		return this.view != null;
	}

}

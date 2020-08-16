/**
 * 
 */
package org.yelong.core.model.annotation.support;

import java.lang.reflect.Field;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.yelong.commons.lang.annotation.AnnotationUtilsE;
import org.yelong.core.annotation.Nullable;
import org.yelong.core.model.Modelable;
import org.yelong.core.model.annotation.Column;
import org.yelong.core.model.annotation.ExtendColumn;
import org.yelong.core.model.annotation.ExtendColumnIgnore;
import org.yelong.core.model.annotation.FACWrapperAppoint;
import org.yelong.core.model.annotation.FACWrapperAppointMap;
import org.yelong.core.model.annotation.FACWrapperAppoints;
import org.yelong.core.model.annotation.Id;
import org.yelong.core.model.annotation.PrimaryKey;
import org.yelong.core.model.annotation.SelectColumn;
import org.yelong.core.model.annotation.SelectColumnConditionalOnProperty;
import org.yelong.core.model.annotation.Transient;
import org.yelong.core.model.manage.ExtendColumnAttribute;
import org.yelong.core.model.manage.FieldAndColumn;
import org.yelong.core.model.manage.SelectColumnCondition;
import org.yelong.core.model.manage.wrapper.FieldAndColumnWrapper;

/**
 * model 字段所支持的注解获取以及获取注解的一些常用值
 * 
 * @since 2.0
 */
public class ModelFieldAnnotation {

	private final Class<? extends Modelable> modelClass;

	private final ModelClassAnnotation modelClassAnnotation;

	private final Field field;

	private final Column column;

	private final Id id;

	private final PrimaryKey primaryKey;

	private final ExtendColumn extendColumn;

	private final ExtendColumnIgnore extendColumnIgnore;

	private final SelectColumn selectColumn;

	private final SelectColumnConditionalOnProperty selectColumnConditionalOnProperty;

	private final Transient tran;

	private final FACWrapperAppoint fieldAndColumnWrapperAppoint;

	public ModelFieldAnnotation(Class<? extends Modelable> modelClass, final Field field) {
		this.modelClass = Objects.requireNonNull(modelClass);
		this.field = Objects.requireNonNull(field);
		if (!field.getDeclaringClass().isAssignableFrom(modelClass)) {
			throw new UnsupportedOperationException(field + "不属于" + modelClass);
		}
		modelClassAnnotation = ModelClassAnnotationFactory.get(modelClass);
		this.column = AnnotationUtilsE.getAnnotation(field, Column.class);
		this.id = AnnotationUtilsE.getAnnotation(field, Id.class);
		this.primaryKey = AnnotationUtilsE.getAnnotation(field, PrimaryKey.class);
		this.extendColumn = AnnotationUtilsE.getAnnotation(field, ExtendColumn.class);
		this.extendColumnIgnore = AnnotationUtilsE.getAnnotation(field, ExtendColumnIgnore.class);
		this.selectColumn = AnnotationUtilsE.getAnnotation(field, SelectColumn.class);
		this.selectColumnConditionalOnProperty = AnnotationUtilsE.getAnnotation(field,
				SelectColumnConditionalOnProperty.class);
		this.tran = AnnotationUtilsE.getAnnotation(field, Transient.class);
		this.fieldAndColumnWrapperAppoint = AnnotationUtilsE.getAnnotation(field, FACWrapperAppoint.class);
	}

	/**
	 * @return 列名
	 */
	public String getColumnCode() {
		if (null == column) {
			return field.getName();
		}
		String columnCode = null;
		columnCode = column.column();
		if (StringUtils.isBlank(columnCode)) {
			columnCode = column.value();
		}
		if (StringUtils.isBlank(columnCode)) {
			columnCode = field.getName();
		}
		return columnCode;
	}

	/**
	 * @return 列名称。如 姓名、年龄等
	 */
	@Nullable
	public String getColumnName() {
		return null == column ? null : column.columnName();
	}

	/**
	 * @return 列允许的最大长度
	 */
	public Long getMaxLength() {
		if (null == column) {
			return null;
		}
		long maxLength = column.maxLength();
		return maxLength > 0 ? maxLength : null;
	}

	/**
	 * @return 列允许的最小长度
	 */
	public Long getMinLength() {
		if (null == column) {
			return null;
		}
		long minLength = column.minLength();
		return minLength >= 0 ? minLength : null;
	}

	/**
	 * 是否允许字符串类型为空白
	 * 
	 * @return <tt>true</tt> 允许
	 */
	public boolean isAllowBlank() {
		if (isPrimaryKey()) {// 主键默认不允许为空
			return false;
		}
		return null == column ? true : column.allowBlank();
	}

	/**
	 * 是否允许为 <code>null</code>
	 * 
	 * @return <tt>true</tt> 允许
	 */
	public boolean isAllowNull() {
		if (isPrimaryKey()) {// 主键默认不允许为空
			return false;
		}
		return null == column ? true : column.allowNull();
	}

	/**
	 * @return 列描述
	 */
	@Nullable
	public String getDesc() {
		return null == column ? null : column.desc();
	}

	/**
	 * @return 列映射的数据库数据类型
	 */
	@Nullable
	public String getJdbcType() {
		return null == column ? null : column.jdbcType();
	}

	/**
	 * 是否是主键
	 * 
	 * @return <tt>true</tt> 字段映射的列为主键
	 */
	public boolean isPrimaryKey() {
		if (null != primaryKey || null != id) {
			return true;
		}
		return modelClassAnnotation.isPrimaryKey(field);
	}

	/**
	 * 是否是拓展列
	 * 
	 * @return <tt>true</tt> 拓展字段（列）
	 */
	public boolean isExtendColumn() {
		if (null != extendColumn) {
			return true;
		}
		// 忽略了拓展列
		if (null != extendColumnIgnore) {
			return false;
		}
		return modelClassAnnotation.isExtendColumn(field);
	}

	/**
	 * 获取拓展列所属的model class
	 * 
	 * @return 拓展列所属的model class
	 * @throws UnsupportedOperationException 该列不是拓展列
	 */
	@Nullable
	public Class<? extends Modelable> getExtendColumnModelClass() {
		if (!isExtendColumn()) {
			throw new UnsupportedOperationException("[" + field.getName() + "]字段不是拓展列，无法获取拓展列所属的 model class");
		}
		if (null == extendColumn) {
			return null;
		}
		Class<? extends Modelable> modelClass = null;
		modelClass = extendColumn.value();
		if (modelClass == ExtendColumn.DEFAULT_MODEL_CLASS) {
			modelClass = extendColumn.modelClass();
		}
		return modelClass == ExtendColumn.DEFAULT_MODEL_CLASS ? null : modelClass;
	}

	/**
	 * 获取拓展列所属的表名
	 * 
	 * @return 拓展列所属的表名
	 * @throws UnsupportedOperationException 该列不是拓展列
	 */
	@Nullable
	public String getExtendColumnTableName() {
		if (!isExtendColumn()) {
			throw new UnsupportedOperationException("[" + field.getName() + "]字段不是拓展列，无法获取拓展列所属的表名");
		}
		if (null == extendColumn) {
			return null;
		}
		String tableName = extendColumn.tableName();
		if (StringUtils.isNotBlank(tableName)) {
			return tableName;
		}
		Class<? extends Modelable> extendColumnModelClass = getExtendColumnModelClass();
		if (null == extendColumnModelClass) {
			return null;
		}
		ModelClassAnnotation modelClassAnnotation = new ModelClassAnnotation(extendColumnModelClass);
		return modelClassAnnotation.getTableName();
	}

	/**
	 * 获取拓展列所属的表的别名
	 * 
	 * @return 拓展列所属的表的别名
	 * @throws UnsupportedOperationException 该列不是拓展列
	 */
	@Nullable
	public String getExtendColumnTableAlias() {
		if (!isExtendColumn()) {
			throw new UnsupportedOperationException("[" + field.getName() + "]字段不是拓展列，无法获取拓展列所属表的别名");
		}
		if (null == extendColumn) {
			return null;
		}
		String tableAlias = extendColumn.tableAlias();
		if (StringUtils.isNotBlank(tableAlias)) {
			return tableAlias;
		}
		Class<? extends Modelable> extendColumnModelClass = getExtendColumnModelClass();
		if (null == extendColumnModelClass) {
			return null;
		}
		ModelClassAnnotation modelClassAnnotation = new ModelClassAnnotation(extendColumnModelClass);
		return modelClassAnnotation.getTableAlias();
	}

	/**
	 * @return 拓展属性列
	 */
	@Nullable
	public ExtendColumnAttribute getExtendColumnAttribute(FieldAndColumn fieldAndColumn) {
		if (isTransient()) {
			return null;
		}
		if (!isExtendColumn()) {
			return null;
		}
		return new ExtendColumnAttribute(fieldAndColumn, getExtendColumnModelClass(), getExtendColumnTableName(),
				getExtendColumnTableAlias());
	}

	/**
	 * @return 查询列条件
	 */
	@Nullable
	public SelectColumnCondition getSelectColumnCondition() {
		if (isExtendColumn() || isTransient()) {
			return null;
		}
		if (null == selectColumnConditionalOnProperty) {
			return null;
		}
		String property = StringUtils.isNotEmpty(selectColumnConditionalOnProperty.value()) ? // 如果value不为空则为value
				selectColumnConditionalOnProperty.value()
				: StringUtils.isNotEmpty(selectColumnConditionalOnProperty.name()) ? // 如果name不为空则为name
						selectColumnConditionalOnProperty.name() : field.getName();// 否则为字段名称
		return new SelectColumnCondition(property, selectColumnConditionalOnProperty.havingValue(),
				selectColumnConditionalOnProperty.matchIfMissing());
	}

	/**
	 * 是否是临时的字段
	 * 
	 * @return <tt>true</tt> 临时的字段。需要忽略的字段
	 */
	public boolean isTransient() {
		if (null != tran) {
			return true;
		}
		return modelClassAnnotation.isTransient(getField());
	}

	/**
	 * @return 查询时的字段映射的列名称
	 */
	public String getSelectColumnCode() {
		if (null == selectColumn) {
			return getColumnCode();
		}
		String selectColumnCode = selectColumn.column();
		if (StringUtils.isBlank(selectColumnCode)) {
			selectColumnCode = selectColumn.value();
		}
		if (StringUtils.isBlank(selectColumnCode)) {
			selectColumnCode = getColumnCode();
		}
		return selectColumnCode;
	}

	/**
	 * @return 对与ORM框架来说，查询时是否映射该字段
	 */
	public boolean isSelect() {
		return null == selectColumn ? true : selectColumn.select();
	}

	/**
	 * 获取此字段后的字段列包装器类型
	 * 
	 * @return 字段列包装器类型
	 * @see FACWrapperAppoint
	 * @see FACWrapperAppoints
	 * @see FACWrapperAppointMap
	 */
	@Nullable
	public Class<? extends FieldAndColumnWrapper> getFieldAndColumnWrapperClass() {
		if (null != fieldAndColumnWrapperAppoint) {
			return fieldAndColumnWrapperAppoint.value();
		}
		return modelClassAnnotation.getFieldAndColumnWrapperClass(getFieldName());
	}

	public Column getColumn() {
		return column;
	}

	public Id getId() {
		return id;
	}

	public PrimaryKey getPrimaryKey() {
		return primaryKey;
	}

	public ExtendColumn getExtendColumn() {
		return extendColumn;
	}

	public ExtendColumnIgnore getExtendColumnIgnore() {
		return extendColumnIgnore;
	}

	public SelectColumn getSelectColumn() {
		return selectColumn;
	}

	public SelectColumnConditionalOnProperty getSelectColumnConditionalOnProperty() {
		return selectColumnConditionalOnProperty;
	}

	public Transient getTransient() {
		return tran;
	}

	public FACWrapperAppoint getFieldAndColumnWrapperAppoint() {
		return fieldAndColumnWrapperAppoint;
	}

	public Field getField() {
		return field;
	}

	public String getFieldName() {
		return field.getName();
	}

	public Class<? extends Modelable> getModelClass() {
		return modelClass;
	}

	public ModelClassAnnotation getModelClassAnnotation() {
		return modelClassAnnotation;
	}

}

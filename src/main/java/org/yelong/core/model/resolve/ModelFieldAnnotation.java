/**
 * 
 */
package org.yelong.core.model.resolve;

import java.lang.reflect.Field;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.yelong.commons.annotation.AnnotationUtils;
import org.yelong.core.model.Modelable;
import org.yelong.core.model.annotation.Column;
import org.yelong.core.model.annotation.ExtendColumn;
import org.yelong.core.model.annotation.ExtendColumnIgnore;
import org.yelong.core.model.annotation.ExtendTable;
import org.yelong.core.model.annotation.Id;
import org.yelong.core.model.annotation.PrimaryKey;
import org.yelong.core.model.annotation.SelectColumn;
import org.yelong.core.model.annotation.Transient;

/**
 * model 字段所支持的注解获取以及获取注解的一些常用值
 * 
 * @author PengFei
 */
public class ModelFieldAnnotation {
	
	private final Field field;
	
	private final Column column;
	
	private final Id id;
	
	private final PrimaryKey primaryKey;
	
	private final ExtendColumn extendColumn;
	
	private final ExtendColumnIgnore extendColumnIgnore;
	
	private final SelectColumn selectColumn;
	
	private final Transient tran;
	
	public ModelFieldAnnotation(final Field field) {
		this.field = Objects.requireNonNull(field);
		this.column = AnnotationUtils.getAnnotation(field, Column.class);
		this.id = AnnotationUtils.getAnnotation(field, Id.class);
		this.primaryKey = AnnotationUtils.getAnnotation(field, PrimaryKey.class);
		this.extendColumn = AnnotationUtils.getAnnotation(field, ExtendColumn.class);
		this.extendColumnIgnore = AnnotationUtils.getAnnotation(field, ExtendColumnIgnore.class);
		this.selectColumn = AnnotationUtils.getAnnotation(field, SelectColumn.class);
		this.tran = AnnotationUtils.getAnnotation(field, Transient.class);
	}
	
	/**
	 * @return 列名
	 */
	public String getColumnCode() {
		if( null == column ) {
			return field.getName();
		}
		String columnCode = null;
		columnCode = column.column();
		if( StringUtils.isBlank(columnCode) ) {
			columnCode = column.value();
		}
		if( StringUtils.isBlank(columnCode) ) {
			columnCode = field.getName();
		}
		return columnCode;
	}
	
	/**
	 * @return 列名称。如 姓名、年龄等
	 */
	public String getColumnName() {
		return null == column ? null : column.columnName();
	}
	
	/**
	 * @return 列允许的最大长度
	 */
	public long getMaxLength() {
		return null == column ? Long.MAX_VALUE : column.maxLength();
	}
	
	/**
	 * @return 列允许的最小长度
	 */
	public long getMinLength() {
		return null == column ? 0L : column.minLength();
	}
	
	/**
	 * 是否允许字符串类型为空白
	 * @return <tt>true</tt> 允许
	 */
	public boolean isAllowBlank() {
		if(isPrimaryKey()) {//主键默认不允许为空
			return false;
		}
		return null == column ? true : column.allowBlank();
	}
	
	/**
	 * 是否允许为 <code>null</code>
	 * @return <tt>true</tt> 允许
	 */
	public boolean isAllowNull() {
		if(isPrimaryKey()) {//主键默认不允许为空
			return false;
		}
		return null == column ? true : column.allowNull();
	}
	
	/**
	 * @return 列描述
	 */
	public String getDesc() {
		return null == column ? null :column.desc();
	}
	
	/**
	 * @return 列映射的数据库数据类型
	 */
	public String getJdbcType() {
		return null == column ? null : column.jdbcType();
	}
	
	/**
	 * 是否是主键
	 * @return <tt>true</tt> 字段映射的列为主键
	 */
	public boolean isPrimaryKey() {
		return null != primaryKey || null != id;
	}
	
	/**
	 * 是否是拓展列
	 * @return <tt>true</tt> 拓展字段（列）
	 */
	public boolean isExtendColumn() {
		if( null != extendColumn ) {
			return true;
		}
		//忽略了拓展列
		if( null != extendColumnIgnore ) {
			return false;
		}
		return field.getDeclaringClass().isAnnotationPresent(ExtendTable.class);
	}
	
	/**
	 * 获取拓展列所属的model class
	 * 
	 * @return 拓展列所属的model class
	 * @throws UnsupportedOperationException 该列不是拓展列
	 */
	public Class<? extends Modelable> getExtendColumnModelClass(){
		if(!isExtendColumn()) {
			throw new UnsupportedOperationException("["+field.getName()+"]字段不是拓展列，无法获取拓展列所属的 model class");
		}
		if( null == extendColumn ) {
			return null;
		}
		Class<? extends Modelable> modelClass = null;
		modelClass = extendColumn.value();
		if( modelClass == ExtendColumn.DEFAULT_MODEL_CLASS) {
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
	public String getExtendColumnTableName() {
		if(!isExtendColumn()) {
			throw new UnsupportedOperationException("["+field.getName()+"]字段不是拓展列，无法获取拓展列所属的表名");
		}
		if( null == extendColumn ) {
			return null;
		}
		String tableName = extendColumn.tableName();
		if(StringUtils.isNotBlank(tableName)) {
			return tableName;
		}
		Class<? extends Modelable> extendColumnModelClass = getExtendColumnModelClass();
		if( null == extendColumnModelClass) {
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
	public String getExtendColumnTableAlias() {
		if(!isExtendColumn()) {
			throw new UnsupportedOperationException("["+field.getName()+"]字段不是拓展列，无法获取拓展列所属表的别名");
		}
		if( null == extendColumn ) {
			return null;
		}
		String tableAlias = extendColumn.tableAlias();
		if(StringUtils.isNotBlank(tableAlias)) {
			return tableAlias;
		}
		Class<? extends Modelable> extendColumnModelClass = getExtendColumnModelClass();
		if( null == extendColumnModelClass) {
			return null;
		}
		ModelClassAnnotation modelClassAnnotation = new ModelClassAnnotation(extendColumnModelClass);
		return modelClassAnnotation.getTableAlias();
	}
	
	/**
	 * 是否是临时的字段
	 * 
	 * @return <tt>true</tt> 临时的字段。需要忽略的字段
	 */
	public boolean isTransient() {
		return null != tran;
	}
	
	/**
	 * @return 查询时的字段映射的列名称
	 */
	public String getSelectColumnCode() {
		if( null == selectColumn ) {
			return getColumnCode();
		}
		String selectColumnCode = selectColumn.column();
		if(StringUtils.isBlank(selectColumnCode)) {
			selectColumnCode = selectColumn.value();
		}
		if(StringUtils.isBlank(selectColumnCode)) {
			selectColumnCode = getColumnCode();
		}
		return selectColumnCode;
	}
	
	/**
	 * @return 对与ORM框架来说，查询时是否映射该字段
	 */
	public boolean isSelectMapping() {
		return null == selectColumn ? true : selectColumn.mapping();
	}
	
	/**
	 * @return Column annotation
	 */
	public Column getColumn() {
		return column;
	}

	/**
	 * @return Id annotation
	 */
	public Id getId() {
		return id;
	}

	/**
	 * @return PrimaryKey annotation
	 */
	public PrimaryKey getPrimaryKey() {
		return primaryKey;
	}

	/**
	 * @return ExtendColumn annotation
	 */
	public ExtendColumn getExtendColumn() {
		return extendColumn;
	}

	/**
	 * @return ExtendColumnIgnore annotation
	 */
	public ExtendColumnIgnore getExtendColumnIgnore() {
		return extendColumnIgnore;
	}
	
	/**
	 * @return SelectColumn annotation
	 */
	public SelectColumn getSelectColumn() {
		return selectColumn;
	}

	/**
	 * @return Transient annotation
	 */
	public Transient getTransient() {
		return tran;
	}

	/**
	 * @return field
	 */
	public Field getField() {
		return field;
	}
	
}

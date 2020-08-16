/**
 * 
 */
package org.yelong.core.model.manage;

import java.lang.reflect.Field;

import org.yelong.core.annotation.Nullable;
import org.yelong.core.model.manage.exception.FieldAndColumnException;

/**
 * 字段与列的映射信息
 * 
 * @see ModelAndTable
 * @since 1.0
 */
public interface FieldAndColumn {

	/**
	 * @return 该字段列所属的模型表
	 */
	ModelAndTable getModelAndTable();

	/**
	 * @return 字段
	 */
	@Nullable
	Field getField();

	/**
	 * @return 字段名称
	 */
	String getFieldName();

	/**
	 * @return 字段类型
	 */
	Class<?> getFieldType();

	/**
	 * @return 列名称
	 */
	String getColumn();

	/**
	 * 获取该字段查询时映射的列名称。 字段在增删改和查询可能映射不同的列
	 * 
	 * @return 字段查询时映射的列名称
	 */
	String getSelectColumn();

	/**
	 * 获取列映射的条件。根据某些条件可以选择性的映射这些列
	 * 
	 * @return 是否进行映射列的条件对象
	 */
	@Nullable
	SelectColumnCondition getSelectColumnCondition();

	/**
	 * 查询时，是否映射该字段
	 * 
	 * @return <tt>true</tt> 映射
	 */
	boolean isSelect();

	/**
	 * 该列是否是拓展列。 拓展列：此列不在当前表中，这可能是关联表查询的列
	 * 
	 * @return <tt>true</tt> 是拓展列
	 */
	boolean isExtend();

	/**
	 * 获取这个列的拓展列属性
	 * 
	 * @return 这个列的拓展列属性。如果这个列不是一个拓展列返回 <code>null</code>
	 * @since 1.2.0
	 */
	@Nullable
	ExtendColumnAttribute getExtendColumnAttribute();

	/**
	 * 是否是主键
	 * 
	 * @return <tt>true</tt> 是主键
	 */
	boolean isPrimaryKey();

	/**
	 * @return column 允许的最小长度
	 */
	@Nullable
	Long getMinLength();

	/**
	 * @return column 允许的最大长度
	 */
	@Nullable
	Long getMaxLength();

	/**
	 * @return column 是否允许为空白
	 */
	boolean isAllowBlank();

	/**
	 * @return column 是否允许为空
	 */
	boolean isAllowNull();

	/**
	 * @return column 对应的数据库类型
	 */
	@Nullable
	String getJdbcType();

	/**
	 * @return column 描述
	 */
	@Nullable
	String getDesc();

	/**
	 * @return 列说明。如：age则一般为年龄。name则为名称
	 * @since 1.0.1
	 */
	@Nullable
	String getColumnName();

	/**
	 * 是否相同
	 * 
	 * @param fieldAndColumn fieldAndColumn
	 * @return <code>true</code> 相同
	 * @since 1.3.0
	 */
	boolean equals(FieldAndColumn fieldAndColumn);

	/**
	 * 是否是临时的字段列
	 * 
	 * @return <code>true</code> 是临时的字段列
	 */
	boolean isTransient();

	/**
	 * 初始化绑定的模型表<br/>
	 * 这个方法只允许被调用一次，在调用该方法一次之后每次调用均会抛出{@link FieldAndColumnException}<br/>
	 * 这个方法一般在 {@link ModelAndTable#initPossessFieldAndColumns(java.util.List)}中被调用
	 * 
	 * @param modelAndTable 该字段列所属的模型表
	 * @since 2.0
	 */
	void initBelongModelAndTable(ModelAndTable modelAndTable);

	default FieldAndColumnType getFieldAndColumnType() {
		if (isTransient()) {
			return FieldAndColumnType.TRANSIENT;
		}
		if (isExtend()) {
			return FieldAndColumnType.EXTEND;
		}
		if (isPrimaryKey()) {
			return FieldAndColumnType.PRIMARYKEY;
		}
		return FieldAndColumnType.ORDINARY;
	}

}

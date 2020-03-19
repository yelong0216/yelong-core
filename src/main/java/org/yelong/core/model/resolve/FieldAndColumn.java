/**
 * 
 */
package org.yelong.core.model.resolve;

import java.lang.reflect.Field;

/**
 * 字段与列的映射信息
 * 
 * @author PengFei
 * @date 2019年12月26日下午9:29:02
 */
public interface FieldAndColumn {
	
	/**
	 * @return 字段
	 */
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
	 * 获取该字段查询时映射的列名称。
	 * 字段在增删改和查询可能映射不同的列
	 * @return 字段查询时映射的列名称
	 */
	String getSelectColumn();
	
	/**
	 * 获取列映射的条件。根据某些条件可以选择性的映射这些列
	 * @return 是否进行映射列的条件对象
	 */
	SelectColumnCondition getSelectColumnCondition();
	
	/**
	 * 该列是否是拓展列。
	 * 拓展列：此列不在当前表中，这可能是关联表查询的列
	 * @return <tt>true</tt> 是拓展列
	 */
	boolean isExtend();
	
	/**
	 * 是否是主键
	 * @return <tt>true</tt> 是主键
	 */
	boolean isPrimaryKey();
	
	/**
	 * @return column 允许的最小长度
	 */
	Long getMinLength();
	
	/**
	 * @return column 允许的最大长度
	 */
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
	String getJdbcType();
	
	/**
	 * @return column 描述
	 */
	String getDesc();
	
}

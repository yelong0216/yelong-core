/**
 * 
 */
package org.yelong.core.model.map;

import java.lang.reflect.Field;

import org.yelong.core.annotation.test.NotImplemented;
import org.yelong.core.model.resolve.FieldAndColumn;
import org.yelong.core.model.resolve.ModelAndTable;
import org.yelong.core.model.resolve.SelectColumnCondition;

/**
 * map model 字段与列的映射。
 * 
 * 对于 {@link MapModelable}来说，属性均为其Key值，所以是不存在 {@link Field}的。s
 * 
 * @author PengFei
 * @since 1.0.7
 */
public interface MapModelFieldAndColumn extends FieldAndColumn{
	
	@Override
	default Field getField() {
		throw new UnsupportedOperationException("map model 不支持获取 Field 实例 ， 因为在这些字段是不存在的。你可以使用 getFieldName 来获取字段名称（也就是map的key）");
	}

	@Override
	default Class<?> getFieldType() {
		return Object.class;
	}
	
	@Override
	default Long getMinLength() {
		return 0L;
	}
	
	/**
	 * 该列不存在映射的字段，而是直接放到map中
	 */
	default boolean isSelectMapping() {
		return false;
	}
	
	@Override
	default String getFieldName() {
		return getColumn();
	}
	
	@Override
	default @NotImplemented SelectColumnCondition getSelectColumnCondition() {
		throw new UnsupportedOperationException();
	}
	
	@Override
	default String getSelectColumn() {
		return getColumn();
	}
	
	@Override
	default boolean isExtend() {
		return false;
	}
	
	@Override
	default ModelAndTable getModelAndTable() {
		return getMapModelAndTable();
	}
	
	/**
	 * @return map model and table
	 */
	MapModelAndTable getMapModelAndTable();
	
}

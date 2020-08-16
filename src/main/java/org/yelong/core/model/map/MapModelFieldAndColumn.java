/**
 * 
 */
package org.yelong.core.model.map;

import java.lang.reflect.Field;

import org.yelong.core.model.manage.FieldAndColumn;

/**
 * map model 字段与列的映射。
 * 
 * 对于 {@link MapModelable}来说，属性均为其Key值，所以是不存在 {@link Field}的。
 * 
 * @since 1.1
 */
public interface MapModelFieldAndColumn extends FieldAndColumn {

	@Override
	default Field getField() {
		throw new UnsupportedOperationException(
				"map model 不支持获取 Field 实例 ， 因为在这些字段是不存在的。你可以使用 getFieldName 来获取字段名称（也就是map的key）");
	}

}

/**
 * 
 */
package org.yelong.core.model.property;

import java.beans.PropertyDescriptor;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.yelong.commons.beans.BeanUtilsE;
import org.yelong.core.model.Modelable;
import org.yelong.core.model.map.MapModelable;

/**
 * 
 * 已以下顺序获取属性值
 * 
 * <pre>
 * 1、如果Model实现MapModel，则通过{@link Map#get(Object)}方式获取
 * 2、使用{@link PropertyDescriptor}来获取属性值。
 * 3、使用反射来获取字段值
 * </pre>
 * 
 * 设置值同理
 * 
 * @since 1.0
 */
public class DefaultModelProperty implements ModelProperty {

	public static final DefaultModelProperty INSTANCE = new DefaultModelProperty();

	protected DefaultModelProperty() {

	}

	@SuppressWarnings("unchecked")
	@Override
	public <V> V get(Modelable model, String property) {
		Objects.requireNonNull(model);
		Objects.requireNonNull(property);
		Object value = null;
		if (model instanceof MapModelable) {
			MapModelable mapModel = (MapModelable) model;
			value = mapModel.get(property);
		} else {
			try {
				value = BeanUtilsE.getProperty(model, property);
			} catch (NoSuchMethodException e) {
				try {
					value = FieldUtils.readField(model, property, true);
				} catch (IllegalAccessException e1) {
					throw new ModelPropertyException(model.getClass(), e1);
				}
			}
		}
		return (V) value;
	}

	@Override
	public <V> V get(Modelable model, String property, V defaultValue) {
		V value = get(model, property);
		return value != null ? value : defaultValue;
	}

	@Override
	public <V> void set(Modelable model, String property, V value) {
		Objects.requireNonNull(model);
		Objects.requireNonNull(property);
		if (model instanceof MapModelable) {
			MapModelable mapModel = (MapModelable) model;
			mapModel.put(property, value);
		} else {
			try {
				BeanUtilsE.setProperty(model, property, value);
			} catch (NoSuchMethodException e) {
				try {
					FieldUtils.writeField(model, property, value, true);
				} catch (IllegalAccessException e1) {
					throw new ModelPropertyException(model.getClass(), e1);
				}
			}
		}
	}

}

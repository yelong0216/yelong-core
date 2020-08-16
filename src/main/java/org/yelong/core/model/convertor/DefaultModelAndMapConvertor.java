/**
 * 
 */
package org.yelong.core.model.convertor;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.function.Supplier;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.yelong.core.model.Modelable;
import org.yelong.core.model.map.MapModelable;

/**
 * 模型与Map转换器默认实现。如果这个模型是
 * {@link MapModelable}，在进行模型与Map转换时，仅是两个Map的{@link Map#putAll(Map)}方法调用。
 * 对于普通的模型，Map转换为模型时，将获取模型的所有字段，且按照字段名称为Map的键在Map中取值来作为字段的值进行设置。
 * 对于模型转换为Map则是反之。
 * 
 * @since 2.0
 */
public class DefaultModelAndMapConvertor implements ModelAndMapConvertor {

	public static final ModelAndMapConvertor INSTANCE = new DefaultModelAndMapConvertor();

	@Override
	public <M extends Modelable> M toModel(Map<String, Object> map, M model) {
		if (MapUtils.isEmpty(map)) {
			return model;
		}
		if (model instanceof MapModelable) {
			MapModelable mapModel = (MapModelable) model;
			mapModel.putAll(map);
			return model;
		}
		Field[] fields = FieldUtils.getAllFields(model.getClass());
		for (Field field : fields) {
			Object value = map.get(field.getName());
			try {
				FieldUtils.writeField(field, model, value, true);
			} catch (IllegalAccessException e) {

			}
		}
		return model;
	}

	@Override
	public <T extends Map<String, Object>> T toMap(Modelable model, Supplier<T> mapFactory) {
		T map = mapFactory.get();
		if (model instanceof MapModelable) {
			MapModelable mapModel = (MapModelable) model;
			map.putAll(mapModel);
			return map;
		}
		Field[] fields = FieldUtils.getAllFields(model.getClass());
		for (Field field : fields) {
			try {
				Object value = FieldUtils.readField(field, model, true);
				map.put(field.getName(), value);
			} catch (IllegalAccessException e) {

			}
		}
		return map;
	}

}

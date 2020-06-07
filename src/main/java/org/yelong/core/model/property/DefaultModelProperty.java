/**
 * 
 */
package org.yelong.core.model.property;

import java.beans.PropertyDescriptor;
import java.util.Map;

import org.yelong.commons.beans.BeanUtilsE;
import org.yelong.core.model.exception.ModelPropertyException;
import org.yelong.core.model.map.MapModel;

/**
 * 
 * 已以下顺序获取属性值
 * 1、使用{@link PropertyDescriptor}来获取属性值。
 * 2、如果Model实现MapModel，则通过{@link Map#get(Object)}方式获取
 * 
 * 对于MapModel来说，也是先通过get/set方法来设置、获取，没有找到属性时才会调用{@link Map#get(Object)}
 * 
 * @author PengFei
 */
public class DefaultModelProperty implements ModelProperty{

	public static final DefaultModelProperty INSTANCE = new DefaultModelProperty();
	
	protected DefaultModelProperty() {
		
	}
	
	@Override
	public Object get(Object model, String property) {
		Object value = null;
		try {
			value = BeanUtilsE.getProperty(model, property);
		} catch (NoSuchMethodException e) {
			//不存在get/is方法
			if( model instanceof MapModel) {
				MapModel mapModel = (MapModel)model;
				value = mapModel.get(property);
			} else {
				throw new ModelPropertyException(e);
			}
		}
		return value;
	}

	@Override
	public void set(Object model, String property, Object value) {
		try {
			BeanUtilsE.setProperty(model, property, value);;
		} catch (NoSuchMethodException e) {
			//不存在set/is方法
			if( model instanceof MapModel) {
				MapModel mapModel = (MapModel)model;
				value = mapModel.put(property, value);
			} else {
				throw new ModelPropertyException(e);
			}
		}
	}

}

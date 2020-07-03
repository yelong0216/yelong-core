/**
 * 
 */
package org.yelong.core.model.property;

import java.beans.PropertyDescriptor;

import org.yelong.core.model.exception.ModelPropertyException;

/**
 * model 属性操作。
 * 
 * 可以设置、获取model的值。
 * 
 * 一般通过反射或者{@link PropertyDescriptor}来实现
 * 
 * @author PengFei
 */
public interface ModelProperty {

	/**
	 * 获取model属性对应的值
	 * 
	 * @param model    model
	 * @param property 属性名称
	 * @return 属性对应的值。
	 * @throws ModelPropertyException 属性获取异常
	 */
	Object get(Object model, String property);

	/**
	 * 设置属性值
	 * 
	 * @param model    model
	 * @param property 属性名称
	 * @param value    值
	 * @throws ModelPropertyException 属性设置异常
	 */
	void set(Object model, String property, Object value);

}

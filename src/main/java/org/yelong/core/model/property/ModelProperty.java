/**
 * 
 */
package org.yelong.core.model.property;

import org.yelong.core.annotation.Nullable;
import org.yelong.core.model.Modelable;

/**
 * model 属性操作。
 * 
 * 可以设置、获取model的值。
 * 
 * @since 1.0
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
	@Nullable
	<V> V get(Modelable model, String property) throws ModelPropertyException;

	/**
	 * 获取model属性对应的值
	 * 
	 * @param model    model
	 * @param property 属性名称
	 * @return 属性对应的值。
	 * @throws ModelPropertyException 属性获取异常
	 */
	@Nullable
	<V> V get(Modelable model, String property, @Nullable V defaultValue) throws ModelPropertyException;

	/**
	 * 设置属性值
	 * 
	 * @param model    model
	 * @param property 属性名称
	 * @param value    值
	 * @throws ModelPropertyException 属性设置异常
	 */
	<V> void set(Modelable model, String property, @Nullable V value) throws ModelPropertyException;

}

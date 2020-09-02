/**
 * 
 */
package org.yelong.core.model;

import java.util.HashMap;
import java.util.Map;

import org.yelong.core.annotation.Nullable;

/**
 * 模型运行时属性配置工具。这些属性仅能获取一次。<br/>
 * 自定义设置的属性键值不要以{@link #MODEL_RUNING_PROPERTY_PREFIX}字符开头，防止与框架中定义的键值冲突
 * 
 * @since 2.0
 */
public final class Models {

	private static final ModelRuningPropertiesThreadLocal MODEL_RUNNING_PROPERTIES = new ModelRuningPropertiesThreadLocal();

	/**
	 * 框架中使用的模型运行时属性前缀。自定义的键值最好不要以该字符串开头
	 */
	public static final String MODEL_RUNING_PROPERTY_PREFIX = "MODEL_RUNING_PROPERTY_";

	private Models() {
	}

	// ==================================================get/set==================================================

	/**
	 * 设置一个属性
	 * 
	 * @param name  属性名称
	 * @param value 属性值
	 */
	public static void setProperty(String name, Object value) {
		MODEL_RUNNING_PROPERTIES.get().put(name, value);
	}

	/**
	 * 获取属性值。值仅能被获取一次，获取后将会移除这个属性
	 * 
	 * @param <V>  result type
	 * @param name 属性名称
	 * @return 属性值
	 */
	@Nullable
	@SuppressWarnings("unchecked")
	public static <V> V getProperty(String name) {
		Object value = MODEL_RUNNING_PROPERTIES.get().get(name);
		MODEL_RUNNING_PROPERTIES.get().remove(name);
		return (V) value;
	}

	/**
	 * 获取属性值。值仅能被获取一次，获取后将会移除这个属性。如果值不存在则返回默认值。
	 * 
	 * @param <V>          result type
	 * @param name         属性名称
	 * @param defaultValue 值不存在时返回的默认值
	 * @return 属性值。如果这个值为 <code>null</code>则返回默认值
	 */
	@Nullable
	public static <V> V getProperty(String name,@Nullable V defaultValue) {
		V value = getProperty(name);
		return value != null ? value : defaultValue;
	}

	// ==================================================model-support==================================================
	
	
	
	
	// ==================================================thread-local==================================================

	private static final class ModelRuningPropertiesThreadLocal extends ThreadLocal<Map<String, Object>> {

		@Override
		protected Map<String, Object> initialValue() {
			return new HashMap<>();
		}

	}

}

/**
 * 
 */
package org.yelong.core.model.convertor;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import org.yelong.core.model.Modelable;

/**
 * 模型与Map的转换器。
 * 
 * @since 2.0
 */
public interface ModelAndMapConvertor {

	/**
	 * 将Map转换为模型
	 * 
	 * @param <M>        model type
	 * @param map        被转换的Map
	 * @param modelClass 转换为的模型类型
	 * @return 转换后的模型实例
	 * @throws InstantiationException {@link Class#newInstance()}
	 * @throws IllegalAccessException {@link Class#newInstance()}
	 */
	default <M extends Modelable> M toModel(Map<String, Object> map, Class<M> modelClass)
			throws InstantiationException, IllegalAccessException {
		return toModel(map, modelClass.newInstance());
	}

	/**
	 * 将Map转换为模型
	 * 
	 * @param <M>          model type
	 * @param map          被转换的Map
	 * @param modelFactory 转换为的模型工厂
	 * @return 转换后的模型实例
	 */
	default <M extends Modelable> M toModel(Map<String, Object> map, Supplier<M> modelFactory) {
		return toModel(map, modelFactory.get());
	}

	/**
	 * 将Map转换为模型
	 * 
	 * @param <M>   model type
	 * @param map   被转换的Map
	 * @param model 转换为的模型实例
	 * @return 转换后的模型实例
	 */
	<M extends Modelable> M toModel(Map<String, Object> map, M model);

	/**
	 * 将模型转换为Map
	 * 
	 * @param model 模型实例
	 * @return 转换后的Map
	 */
	default Map<String, Object> toMap(Modelable model) {
		return toMap(model, HashMap::new);
	}

	/**
	 * 将模型转换为Map
	 * 
	 * @param <T>        map type
	 * @param model      模型实例
	 * @param mapFactory map 工厂
	 * @return 转换后的map
	 */
	<T extends Map<String, Object>> T toMap(Modelable model, Supplier<T> mapFactory);

}

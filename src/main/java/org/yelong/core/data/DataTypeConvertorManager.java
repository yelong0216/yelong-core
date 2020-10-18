package org.yelong.core.data;

import java.util.HashMap;
import java.util.Map;

/**
 * 数据类型转换器管理器
 *
 * @param <S> 源数据类型
 * @since 2.1.6
 */
public class DataTypeConvertorManager<S> {

	protected final Map<Class<?>, DataTypeConvertor<S, ?>> dataTypeConvertors = new HashMap<Class<?>, DataTypeConvertor<S, ?>>();

	/**
	 * 注册数据类型转换器
	 * 
	 * @param <R>               相互转换的数据类型
	 * @param requiredType      相互转换的数据类型
	 * @param dataTypeConvertor 数据类型转换器
	 */
	public <R> void registerDataTypeConvertor(Class<R> requiredType, DataTypeConvertor<S, R> dataTypeConvertor) {
		dataTypeConvertors.put(requiredType, dataTypeConvertor);
	}

	/**
	 * 移除一个数据类型转换器
	 * 
	 * @param <R>          相互转换的数据类型
	 * @param requiredType 相互转换的数据类型
	 */
	public void removeDataTypeConvertor(Class<?> requiredType) {
		dataTypeConvertors.remove(requiredType);
	}

	/**
	 * 获取数据类型转换器
	 * 
	 * @param <R>          相互转换的数据类型
	 * @param requiredType 相互转换的数据类型
	 * @return 数据类型转换器
	 */
	@SuppressWarnings("unchecked")
	public <R> DataTypeConvertor<S, R> getDataTypeConvertor(Class<R> requiredType) {
		return (DataTypeConvertor<S, R>) dataTypeConvertors.get(requiredType);
	}

}

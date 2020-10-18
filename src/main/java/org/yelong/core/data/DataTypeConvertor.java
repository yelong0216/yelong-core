package org.yelong.core.data;

import org.yelong.core.annotation.Nullable;

/**
 * 数据类型转换器
 * 
 * @param <S> source Type 源数据类型
 * @param <R> required Type 转换后的数据类型
 * @since 2.1.6
 */
public interface DataTypeConvertor<S, R> {

	/**
	 * 将源数据类型转换为指定的数据类型数据
	 * 
	 * @param sourceObject 源数据
	 * @return 指定类型后的数据
	 * @throws DataTypeConvertException 数据类型转换异常
	 */
	R convert(@Nullable S sourceObject) throws DataTypeConvertException;

	/**
	 * 将指定的数据反向转换为源数据类型
	 * 
	 * @param requiredObject 转换后的数据
	 * @return 源数据
	 * @throws DataTypeConvertException 数据类型转换异常
	 */
	S reverseConvert(@Nullable R requiredObject) throws DataTypeConvertException;

}

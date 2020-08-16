/**
 * 
 */
package org.yelong.core.model.pojo;

import java.lang.reflect.Field;
import java.util.List;

import org.apache.commons.lang3.ClassUtils;
import org.yelong.core.model.Modelable;
import org.yelong.core.model.manage.FieldAndColumn;
import org.yelong.core.model.pojo.field.FieldResolver;
import org.yelong.core.model.pojo.field.FieldResolverException;
import org.yelong.core.model.resolve.ModelResolver;

/**
 * 普通JAVA类模型解析器。JAVA类中的所有非静态、常量的字段默认使用 {@link FieldResolver}解析为
 * {@link FieldAndColumn}作为模型的字段列。<br/>
 * 
 * 可以注册多个字段解析器，并在解析字段时寻找该字段最合适的解析器进行解析字段
 * 
 * @see FieldResolver
 * @since 2.0
 */
public interface POJOModelResolver extends ModelResolver {

	/**
	 * 获取最合适的字段解析器
	 * 
	 * 查找合适解析器的顺序：<br/>
	 * 1、已经注册的解析器中是否有直接指定该模型与字段的解析器<br/>
	 * 2、已经注册的解析器中是否有直接指定该模型的解析器<br/>
	 * 2、已经注册的解析器中是否有该模型类型父类的解析器（父类迭代至Object）<br/>
	 * 3、已经注册的解析器中是否有该模型类型实现的接口类型的解析器<br/>
	 * （接口类型详见{@link ClassUtils#getAllInterfaces(Class)}）<br/>
	 * 
	 * @param modelClass 被解析的模型
	 * @param field      被解析的字段
	 * @return 最合适的字段解析器
	 * @throws FieldResolverException 没有合适的字段解析器异常
	 */
	FieldResolver getOptimalFieldResolver(Class<? extends Modelable> modelClass, Field field) throws FieldResolverException;

	/**
	 * 注册模型解析器。所有注册的字段解析器所解析的模型与字段范围不能冲突。
	 * 
	 * @param fieldResolver 字段解析器
	 * @throws FieldResolverException 解析器注册失败
	 */
	void registerFieldResovler(FieldResolver fieldResolver) throws FieldResolverException;

	/**
	 * 获取所有注册的字段解析器
	 * 
	 * @return 所有注册的字段解析器
	 */
	List<FieldResolver> getAllFieldResolver();

	/**
	 * 是否存在字段解析器
	 * 
	 * @return <code>true</code> 存在字段解析器
	 */
	boolean existFieldResolver();

}

/**
 * 
 */
package org.yelong.core.model.resolve;

import java.util.List;

import org.apache.commons.lang3.ClassUtils;
import org.yelong.core.model.Modelable;

/**
 * 模型解析器管理。可以注册多个模型解析器，并根据每个模型解析器所解析的模型范围进行分类管理。
 * 之后可以通过{@link #getOptimalModelResolver(Class)}在已经注册的模型解析器中找到模型最合适的解析器
 *
 * @since 2.0
 */
public interface ModelResolverManager {

	/**
	 * 在已经注册的模型解析器中找到解析该模型最合适的解析器<br/>
	 * 
	 * 查找合适解析器的顺序：<br/>
	 * 1、已经注册的解析器中是否有直接指定该模型的解析器<br/>
	 * 2、已经注册的解析器中是否有该模型类型父类的解析器（父类迭代至Object）<br/>
	 * 3、已经注册的解析器中是否有该模型类型实现的接口类型的解析器<br/>
	 * （接口类型详见{@link ClassUtils#getAllInterfaces(Class)}）<br/>
	 * 
	 * @param modelClass model class
	 * @return 在已经注册的解析中对该模型最合适的解析器
	 * @throws ModelResolverException 没有找到合适的解析器
	 */
	ModelResolver getOptimalModelResolver(Class<? extends Modelable> modelClass) throws ModelResolverException;

	/**
	 * 注册模型解析器。所有注册的模型解析器所解析的模型范围不能冲突。
	 * 
	 * @param modelResolver 模型解析器
	 * @see ModelResolver#getResolveScopes()
	 * @throws ModelResolverException 解析器注册失败
	 */
	void registerModelResolver(ModelResolver modelResolver) throws ModelResolverException;

	/**
	 * 获取所有已经注册的模型解析器
	 * 
	 * @return 所有已注册的模型解析器
	 */
	List<ModelResolver> getAllModelResolve();

	/**
	 * 是否存在模型解析器
	 * 
	 * @return <code>true</code> 存在模型解析器
	 */
	boolean existModelResolver();

}

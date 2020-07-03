/**
 * 
 */
package org.yelong.core.model.resolve;

import org.yelong.core.model.Modelable;

/**
 * 模型解析器
 * 
 * 将model解析为框架中所使用的对象 {@link ModelAndTable}
 * 
 * @author PengFei
 * @see Modelable
 * @see ModelAndTable
 */
public interface ModelResolver {

	/**
	 * 解析model
	 * 
	 * @param <M>        model type
	 * @param modelClass model class
	 * @return 模型与表映射
	 * @throws ModelResolveException 不能解析的model或者model解析失败了
	 */
	<M extends Modelable> ModelAndTable resolve(Class<M> modelClass) throws ModelResolveException;

}

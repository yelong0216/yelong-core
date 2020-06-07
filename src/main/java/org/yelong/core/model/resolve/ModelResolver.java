/**
 * 
 */
package org.yelong.core.model.resolve;

import org.yelong.core.model.Modelable;

/**
 * 模型解析器
 * 
 * @author PengFei
 */
public interface ModelResolver {
	
	/**
	 * 解析model
	 * @param <M> model type
	 * @param modelClass model class
	 * @return 模型与表映射
	 */
	<M extends Modelable> ModelAndTable resolve(Class<M> modelClass) throws ModelResolveException;
	
}

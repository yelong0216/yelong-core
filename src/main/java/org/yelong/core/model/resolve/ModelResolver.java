/**
 * 
 */
package org.yelong.core.model.resolve;

import org.yelong.core.model.Model;

/**
 * @author PengFei
 * @date 2019年12月29日下午7:39:59
 * @since 1.0
 */
public interface ModelResolver {
	
	/**
	 * 解析model
	 * @param <M>
	 * @param modelClass 模型类
	 * @return 模型与表映射
	 */
	<M extends Model> ModelAndTable resolve(Class<M> modelClass) throws ModelResolveException;
	
}

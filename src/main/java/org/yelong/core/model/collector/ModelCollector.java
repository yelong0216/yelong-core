/**
 * 
 */
package org.yelong.core.model.collector;

import org.yelong.core.model.Modelable;
import org.yelong.core.model.service.SqlModelService;

/**
 * 一种可变的模型操作，它将输入元素累积到可变结果容器中，可选地在所有输入元素被处理后将累积的结果转换为最终表示。
 * 
 * @author PengFei
 *
 * @param <M> model type
 * @param <R> return type 返回的结果类型
 * @param <T> this type 模型收集者的最终实现类型
 * @see SqlModelService#collect(ModelCollector)
 * @see ModelCollectors
 * @since 1.3.0
 */
public interface ModelCollector<M extends Modelable, R, T extends ModelCollector<M, R, T>> {

	/**
	 * 收集结果
	 * 
	 * 不同的实现类通过不同的操作功能返回不同的结果
	 * 
	 * @param modelService model service
	 * @return R 收集的结果
	 */
	R collect(SqlModelService modelService);

	/**
	 * @return 操作的模型class
	 */
	Class<M> getModelClass();

	/**
	 * 设置模型收集返回前的拦截器
	 * 
	 * 在拦截器中可以修改返回的值
	 * 
	 * @param returnInterceptor 返回结果前的拦截器
	 * @return this
	 */
	T returnIntercept(ModelCollectReturnInterceptor<M, R> returnInterceptor);

}

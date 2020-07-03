package org.yelong.core.model.collector;

import org.yelong.core.model.Modelable;
import org.yelong.core.model.service.SqlModelService;

/**
 * 模型收集返回前的拦截器
 * 
 * 可以拦截模型收集后的返回值
 * 
 * @author PengFei
 *
 * @param <M> model type
 * @param <R> return type
 * @see ModelCollector
 * @since 1.3.0
 */
public interface ModelCollectReturnInterceptor<M extends Modelable, R> {

	/**
	 * 执行拦截器操作
	 * 
	 * @param modelService model service
	 * @param modelClass   model class
	 * @param r            model收集者的返回值。
	 * @return r 拦截器后修改的响应值
	 */
	R intercept(SqlModelService modelService, Class<M> modelClass, R r);

}

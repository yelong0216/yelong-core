/**
 * 
 */
package org.yelong.core.model.collector;

import java.util.Objects;

import org.yelong.core.model.Modelable;
import org.yelong.core.model.service.SqlModelService;

/**
 * 模型收集者的默认实现
 * 
 * 默认实现了对拦截器的管理、执行操作
 * 
 * @author PengFei
 *
 * @param <M> model type
 * @param <R> return type
 * @param <T> this type
 * @since 1.3.0
 */
public abstract class ModelCollectorImpl<M extends Modelable, R, T extends ModelCollector<M, R, T>>
		implements ModelCollector<M, R, T> {

	protected final Class<M> modelClass;

	private ModelCollectReturnInterceptor<M, R> returnInterceptor;

	public ModelCollectorImpl(Class<M> modelClass) {
		this.modelClass = Objects.requireNonNull(modelClass);
	}

	@Override
	public final R collect(SqlModelService modelService) {
		Objects.requireNonNull(modelService);
		R r = doCollect(modelService);
		if (null != returnInterceptor) {
			r = returnInterceptor.intercept(modelService, modelClass, r);
		}
		return r;
	}

	/**
	 * 执行收集操作 子类重写此方法完成真正的model收集工作
	 * 
	 * @param modelService model service
	 * @return R 收集后的结果值
	 */
	protected abstract R doCollect(SqlModelService modelService);

	@Override
	public Class<M> getModelClass() {
		return this.modelClass;
	}

	@SuppressWarnings("unchecked")
	@Override
	public T returnIntercept(ModelCollectReturnInterceptor<M, R> returnInterceptor) {
		this.returnInterceptor = returnInterceptor;
		return (T) this;
	}

}
/**
 * 
 */
package org.yelong.core.model.collector;

import org.yelong.core.interceptor.Interceptor;
import org.yelong.core.interceptor.Intercepts;
import org.yelong.core.interceptor.Invocation;
import org.yelong.core.interceptor.Signature;
import org.yelong.core.model.service.SqlModelService;

/**
 * {@link SqlModelService#collect(ModelCollector)}方法在直接使用 {this} 去执行
 * {@link ModelCollector#collect(SqlModelService)}方法时, {this} 对象是未被
 * {@link Interceptor}包装的源对象。 这会导致所有添加的拦截器({@link Interceptor})不起作用。
 * 
 * 添加此拦截器在执行{@link SqlModelService#collect(ModelCollector)}方法时使用拦截器包装后的{@link SqlModelService}解决此问题
 * 
 * @since 1.3
 */
@Intercepts({ @Signature(type = SqlModelService.class, method = "collect", args = ModelCollector.class) })
public abstract class ModelServiceCollectInterceptor implements Interceptor {

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		Object[] args = invocation.getArgs();
		ModelCollector<?, ?, ?> modelCollector = (ModelCollector<?, ?, ?>) args[0];
		return modelCollector.collect(getInterceptorWrapAfterSqlModelService());
	}

	/**
	 * @return 获取拦截器包装之后的{@link SqlModelService}
	 */
	protected abstract SqlModelService getInterceptorWrapAfterSqlModelService();

}

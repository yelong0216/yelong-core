/**
 * 
 */
package org.yelong.core.model.service.function;

import java.lang.reflect.Method;

import org.yelong.core.interceptor.Interceptor;
import org.yelong.core.interceptor.Intercepts;
import org.yelong.core.interceptor.Invocation;
import org.yelong.core.interceptor.Signature;
import org.yelong.core.model.collector.ModelCollector;
import org.yelong.core.model.service.SqlModelService;

/**
 * {@link SqlModelService#doFunction(MSFunction)}方法在直接使用 {this} 去执行
 * {@link ModelCollector#collect(SqlModelService)}方法时, {this} 对象是未被
 * {@link Interceptor}包装的源对象。 这会导致所有添加的拦截器({@link Interceptor})不起作用。
 * 
 * 添加此拦截器在执行{@link SqlModelService#doFunction(MSFunction)}方法时使用拦截器包装后的{@link SqlModelService}解决此问题
 * 
 * @author PengFei
 * @since 1.3.0
 */
@Intercepts({
		@Signature(type = SqlModelService.class, method = MSFunctionInterceptor.DO_FUNCTION_METHOD_NAME, args = MSFunction.class),
		@Signature(type = SqlModelService.class, method = MSFunctionInterceptor.DO_CONSUMER_METHOD_NAME, args = MSConsumer.class) })
public abstract class MSFunctionInterceptor implements Interceptor {

	protected static final String DO_FUNCTION_METHOD_NAME = "doFunction";

	protected static final String DO_CONSUMER_METHOD_NAME = "doConsumer";

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		Object[] args = invocation.getArgs();
		Method method = invocation.getMethod();
		String methodName = method.getName();
		if (methodName.equals(DO_FUNCTION_METHOD_NAME)) {
			MSFunction<?> function = (MSFunction<?>) args[0];
			return function.apply(getInterceptorWrapAfterSqlModelService());
		} else if (methodName.equals(DO_CONSUMER_METHOD_NAME)) {
			MSConsumer consumer = (MSConsumer) args[0];
			consumer.accept(getInterceptorWrapAfterSqlModelService());
			return null;
		}
		throw new UnsupportedOperationException("");
	}

	/**
	 * @return 获取拦截器包装之后的{@link SqlModelService}
	 */
	protected abstract SqlModelService getInterceptorWrapAfterSqlModelService();

}

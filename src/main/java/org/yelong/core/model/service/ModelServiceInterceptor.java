package org.yelong.core.model.service;

import org.yelong.core.interceptor.Interceptor;

/**
 * 声明式接口。一般在注入拦截 {@link ModelService}的拦截器。用来区分多种不同类型的拦截器
 */
public interface ModelServiceInterceptor extends Interceptor {

}

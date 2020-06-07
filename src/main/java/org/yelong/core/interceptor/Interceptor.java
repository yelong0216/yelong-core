/**
 * 
 */
package org.yelong.core.interceptor;

public interface Interceptor {

	Object intercept(Invocation invocation) throws Throwable;

}

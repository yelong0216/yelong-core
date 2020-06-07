/**
 * 
 */
package org.yelong.core.interceptor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InterceptorChain {

	private final List<Interceptor> interceptors = new ArrayList<>();

	public Object pluginAll(Object target) {
		for (Interceptor interceptor : interceptors) {
			target = Plugin.wrap(target, interceptor);
		}
		return target;
	}
	
	public Object pluginAll(Object target ,Class<?>[] targetInterfaces) {
		for (Interceptor interceptor : interceptors) {
			target = Plugin.wrap(target, interceptor,targetInterfaces);
		}
		return target;
	}

	public void addInterceptor(Interceptor interceptor) {
		interceptors.add(interceptor);
	}

	public void addInterceptor(List<Interceptor> interceptors) {
		this.interceptors.addAll(interceptors);
	}

	public List<Interceptor> getInterceptors() {
		return Collections.unmodifiableList(interceptors);
	}
	
}

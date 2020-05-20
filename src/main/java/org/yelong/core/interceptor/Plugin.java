/**
 * 
 */
package org.yelong.core.interceptor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Plugin implements InvocationHandler{

	private final Object target;
	private final Interceptor interceptor;
	private final Map<Class<?>, Set<Method>> signatureMap;

	private Plugin(Object target, Interceptor interceptor, Map<Class<?>, Set<Method>> signatureMap) {
		this.target = target;
		this.interceptor = interceptor;
		this.signatureMap = signatureMap;
	}

	public static Object wrap(Object target, Interceptor interceptor) {
		Map<Class<?>, Set<Method>> signatureMap = getSignatureMap(interceptor);
		Class<?> type = target.getClass();
		//		Class<?>[] interfaces = getAllInterfaces(type, signatureMap);
		Class<?>[] interfaces = type.getInterfaces();//获取实现类的所有接口
		if (interfaces.length > 0) {
			return Proxy.newProxyInstance(
					type.getClassLoader(),
					interfaces,
					new Plugin(target, interceptor, signatureMap));
		}
		return target;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		try {
			Set<Method> methods = signatureMap.get(method.getDeclaringClass());
			if (methods != null && methods.contains(method)) {
				return interceptor.intercept(new Invocation(target, method, args));
			}
			return method.invoke(target, args);
		} catch (Throwable e) {
			//把包装的异常拿出来
			Throwable unwrapped = e;
			while (true) {
				if (unwrapped instanceof InvocationTargetException) {
					unwrapped = ((InvocationTargetException) unwrapped).getTargetException();
				} else if (unwrapped instanceof UndeclaredThrowableException) {
					unwrapped = ((UndeclaredThrowableException) unwrapped).getUndeclaredThrowable();
				} else {
					break;
				}
			}
			throw unwrapped;
		}
	}

	private static Map<Class<?>, Set<Method>> getSignatureMap(Interceptor interceptor) {
		Intercepts interceptsAnnotation = interceptor.getClass().getAnnotation(Intercepts.class);
		// issue #251
		if (interceptsAnnotation == null) {
			throw new RuntimeException("No @Intercepts annotation was found in interceptor " + interceptor.getClass().getName());
		}
		Signature[] sigs = interceptsAnnotation.value();
		Map<Class<?>, Set<Method>> signatureMap = new HashMap<>();
		for (Signature sig : sigs) {
			Set<Method> methods = signatureMap.computeIfAbsent(sig.type(), k -> new HashSet<>());
			try {
				Method method = sig.type().getMethod(sig.method(), sig.args());
				methods.add(method);
			} catch (NoSuchMethodException e) {
				throw new RuntimeException("Could not find method on " + sig.type() + " named " + sig.method() + ". Cause: " + e, e);
			}
		}
		return signatureMap;
	}

	protected static Class<?>[] getAllInterfaces(Class<?> type, Map<Class<?>, Set<Method>> signatureMap) {
		Set<Class<?>> interfaces = new HashSet<>();
		while (type != null) {
			for (Class<?> c : type.getInterfaces()) {
				if (signatureMap.containsKey(c)) {
					interfaces.add(c);
				}
			}
			type = type.getSuperclass();
		}
		return interfaces.toArray(new Class<?>[interfaces.size()]);
	}

}
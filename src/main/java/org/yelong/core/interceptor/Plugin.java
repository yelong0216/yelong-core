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

import org.yelong.commons.annotation.AnnotationUtils;

public class Plugin implements InvocationHandler {

	private final Object target;
	private final Interceptor interceptor;
	private final Map<Class<?>, Set<Method>> signatureMap;

	private Plugin(Object target, Interceptor interceptor, Map<Class<?>, Set<Method>> signatureMap) {
		this.target = target;
		this.interceptor = interceptor;
		this.signatureMap = signatureMap;
	}

	public static Object wrap(Object target, Interceptor interceptor) {
		return wrap(target, interceptor, target.getClass().getInterfaces());
	}

	/**
	 * target 如果是代理对象的话，无法从target中获取其实现类，需要手动设置
	 * 
	 * @param target      源
	 * @param interceptor 拦截器
	 * @param interfaces  实现的接口数组
	 * @return 代理后的对象
	 */
	public static Object wrap(Object target, Interceptor interceptor, Class<?>[] interfaces) {
		Map<Class<?>, Set<Method>> signatureMap = getSignatureMap(interceptor);
		Class<?> type = target.getClass();
		// Class<?>[] interfaces = getAllInterfaces(type, signatureMap);
		if (interfaces.length > 0) {
			return Proxy.newProxyInstance(type.getClassLoader(), interfaces,
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
			// 把包装的异常拿出来
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
		// Intercepts interceptsAnnotation =
		// interceptor.getClass().getAnnotation(Intercepts.class);
		Intercepts interceptsAnnotation = AnnotationUtils.getAnnotation(interceptor.getClass(), Intercepts.class, true);
		// issue #251
		if (interceptsAnnotation == null) {
			throw new RuntimeException(
					"No @Intercepts annotation was found in interceptor " + interceptor.getClass().getName());
		}
		Signature[] sigs = interceptsAnnotation.value();
		Map<Class<?>, Set<Method>> signatureMap = new HashMap<>();
		for (Signature sig : sigs) {
			Set<Method> methods = signatureMap.computeIfAbsent(sig.type(), k -> new HashSet<>());
			try {
				Method method = sig.type().getMethod(sig.method(), sig.args());
				methods.add(method);
			} catch (NoSuchMethodException e) {
				throw new RuntimeException(
						"Could not find method on " + sig.type() + " named " + sig.method() + ". Cause: " + e, e);
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

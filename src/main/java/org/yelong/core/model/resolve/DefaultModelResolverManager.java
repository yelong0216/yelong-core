/**
 * 
 */
package org.yelong.core.model.resolve;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ClassUtils;
import org.yelong.core.model.Modelable;

/**
 * 默认的模型解析器管理器实现
 * 
 * @since 2.0
 */
public class DefaultModelResolverManager implements ModelResolverManager {

	private final Map<Class<? extends Modelable>, ModelResolver> modelResolverMap = new HashMap<>();

	@Override
	public void registerModelResolver(ModelResolver modelResolver) {
		Objects.requireNonNull(modelResolver);
		Class<? extends Modelable>[] resolveScopes = modelResolver.getResolveScopes();
		if (ArrayUtils.isEmpty(resolveScopes)) {
			throw new ModelResolverException(modelResolver + "没有指定解析的模型范围");
		}
		for (Class<? extends Modelable> modelClass : resolveScopes) {
			ModelResolver _modelResolver = modelResolverMap.get(modelClass);
			if (null != _modelResolver) {
				throw new ModelResolverException(modelResolver + " 支持的类型 " + modelClass + " 已经存在解析器" + _modelResolver);
			}
			modelResolverMap.put(modelClass, modelResolver);
		}
	}

	@Override
	public ModelResolver getOptimalModelResolver(Class<? extends Modelable> modelClass) {
		Objects.requireNonNull(modelClass);
		List<Class<?>> classes = new ArrayList<Class<?>>();
		classes.add(modelClass);
		classes.addAll(ClassUtils.getAllSuperclasses(modelClass));
		classes.addAll(ClassUtils.getAllInterfaces(modelClass));
		for (Class<?> cls : classes) {
			ModelResolver modelResolver = modelResolverMap.get(cls);
			if (null != modelResolver) {
				return modelResolver;
			}
		}
		throw new ModelResolverException("没有找到可以解析类型 " + modelClass + " 的解析器");
	}

	@Override
	public List<ModelResolver> getAllModelResolve() {
		return Collections.unmodifiableList(modelResolverMap.values().stream().collect(Collectors.toList()));
	}

	@Override
	public boolean existModelResolver() {
		return !modelResolverMap.isEmpty();
	}

}

/**
 * 
 */
package org.yelong.core.model.manage;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.yelong.core.model.Modelable;
import org.yelong.core.model.resolve.ModelResolver;
import org.yelong.core.model.resolve.ModelResolverManager;

/**
 * 模型管理器的默认实现
 * 
 * @since 2.0
 */
public class DefaultModelManager implements ModelManager {

	private final ModelResolverManager modelResolverManager;

	private final Map<Class<? extends Modelable>, ModelAndTable> modelAndTableMap = new HashMap<>();

	public DefaultModelManager(ModelResolverManager modelResolverManager) {
		this.modelResolverManager = Objects.requireNonNull(modelResolverManager);
	}

	@Override
	public ModelAndTable getModelAndTable(Class<? extends Modelable> modelClass, boolean ignoreCache) {
		if (ignoreCache) {
			return resolveModelAndAddCache(modelClass);
		}
		ModelAndTable modelAndTable = modelAndTableMap.get(modelClass);
		if (null != modelAndTable) {
			return modelAndTable;
		}
		synchronized (this) {
			modelAndTable = modelAndTableMap.get(modelClass);
			if (null != modelAndTable) {
				return modelAndTable;
			}
			return resolveModelAndAddCache(modelClass);
		}
	}

	/**
	 * 解析模型并放入缓存中，如果缓存中已经存在则会覆盖
	 * 
	 * @param modelClass model class
	 * @return 模型表
	 */
	protected synchronized ModelAndTable resolveModelAndAddCache(Class<? extends Modelable> modelClass) {
		ModelResolver modelResolver = modelResolverManager.getOptimalModelResolver(modelClass);
		ModelAndTable modelAndTable = modelResolver.resolve(modelClass);
		modelAndTableMap.put(modelClass, modelAndTable);
		return modelAndTable;
	}

	@Override
	public void clearCacheModelAndTable() {
		this.modelAndTableMap.clear();
	}

	@Override
	public void removeCacheModelAndTable(Class<? extends Modelable> modelClass) {
		this.modelAndTableMap.remove(modelClass);
	}

	@Override
	public ModelResolverManager getModelResolverManager() {
		return modelResolverManager;
	}

	@Override
	public Map<Class<? extends Modelable>, ModelAndTable> getModelAndTableMap() {
		return Collections.unmodifiableMap(this.modelAndTableMap);
	}

}

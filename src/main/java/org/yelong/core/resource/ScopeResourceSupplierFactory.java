package org.yelong.core.resource;

import org.yelong.core.annotation.Nullable;

/**
 * 范围资源供应商工厂
 * 
 * @since 2.2
 */
public interface ScopeResourceSupplierFactory {

	/**
	 * 创建范围资源供应商
	 * 
	 * @param urlMapping        urlMapping
	 * @param resourceScopePath 资源范围路径
	 * @return 范围资源供应商
	 */
	ScopeResourceSupplier create(@Nullable String urlMapping, @Nullable String resourceScopePath);

}

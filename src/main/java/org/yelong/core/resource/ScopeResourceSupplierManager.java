package org.yelong.core.resource;

import java.io.InputStream;

import org.yelong.core.annotation.Nullable;

/**
 * 范围资源供应商管理器
 * 
 * @since 2.2
 */
public interface ScopeResourceSupplierManager {

	/**
	 * 注册资源供应商
	 * 
	 * @param scopeResourceSupplier 资源供应商
	 */
	void registerScopeResourceSupplier(ScopeResourceSupplier scopeResourceSupplier);

	/**
	 * 获取资源。这个资源将从注册的所有供应商中根据URLMapping相关方式去获取资源
	 * 
	 * @param resourcePath 资源路径
	 * @return 资源流
	 */
	@Nullable
	InputStream getResourceAsStream(String resourcePath);

}

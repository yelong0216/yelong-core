package org.yelong.core.resource.defaults;

import org.yelong.core.resource.ScopeResourceSupplier;
import org.yelong.core.resource.ScopeResourceSupplierFactory;

/**
 * 默认实现
 * 
 * @since 2.2
 */
public class DefaultScopeResourceSupplierFactory implements ScopeResourceSupplierFactory {

	@Override
	public ScopeResourceSupplier create(String urlMapping, String resourceScopePath) {
		return new DefaultScopeResourceSupplier(urlMapping, resourceScopePath);
	}

}

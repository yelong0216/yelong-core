package org.yelong.core.resource.defaults;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.yelong.core.resource.ScopeResourceSupplier;
import org.yelong.core.resource.ScopeResourceSupplierManager;

/**
 * 默认实现
 * 
 * @since 2.2
 */
public class DefaultScopeResourceSupplierManager implements ScopeResourceSupplierManager {

	protected final List<ScopeResourceSupplier> scopeResourceSuppliers = new ArrayList<>();

	@Override
	public void registerScopeResourceSupplier(ScopeResourceSupplier scopeResourceSupplier) {
		scopeResourceSuppliers.add(scopeResourceSupplier);
	}

	@Override
	public InputStream getResourceAsStream(String resourcePath) {
		for (ScopeResourceSupplier scopeResourceSupplier : scopeResourceSuppliers) {
			InputStream inputStream = scopeResourceSupplier.getResourceAsStream(resourcePath, true);
			if (null != inputStream) {
				return inputStream;
			}
		}
		return null;
	}

}

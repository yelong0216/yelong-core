package org.yelong.core.resource;

import org.yelong.core.annotation.Nullable;

/**
 * 抽象范围资源供应商
 * 
 * @since 2.2
 */
public abstract class AbstractScopeResourceSupplier implements ScopeResourceSupplier {

	@Nullable
	protected final String urlMapping;

	@Nullable
	protected final String resourceScopePath;

	public AbstractScopeResourceSupplier(@Nullable String urlMapping, @Nullable String resourceScopePath) {
		this.urlMapping = urlMapping;
		this.resourceScopePath = resourceScopePath != null ? resourceScopePath : "/";
	}

	@Override
	public String getURLMapping() {
		return urlMapping;
	}

	@Override
	public String getResourceScopePath() {
		return resourceScopePath;
	}

}

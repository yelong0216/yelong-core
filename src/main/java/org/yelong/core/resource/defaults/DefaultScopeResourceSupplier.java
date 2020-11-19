package org.yelong.core.resource.defaults;

import java.io.InputStream;
import java.util.Objects;

import org.yelong.commons.lang.StringUtilsE;
import org.yelong.commons.util.ResourcesUtils;
import org.yelong.core.annotation.Nullable;
import org.yelong.core.resource.AbstractScopeResourceSupplier;
import org.yelong.core.resource.ScopeResourceSupplier;

/**
 * 简单的范围资源供应商
 * 
 * @since 2.2
 */
public class DefaultScopeResourceSupplier extends AbstractScopeResourceSupplier implements ScopeResourceSupplier {

	public DefaultScopeResourceSupplier(@Nullable String urlMapping, @Nullable String resourceScopePath) {
		super(urlMapping, resourceScopePath);
	}

	@Override
	public InputStream getResourceAsStream(String resourcePath, boolean urlMapping) {
		Objects.requireNonNull(resourcePath);
		if (urlMapping && this.urlMapping != null) {
			// 如果资源路径是URL映射才会取获取资源，不是此URL的映射直接返回null
			if (resourcePath.startsWith(this.urlMapping)) {
				resourcePath = resourcePath.substring(this.urlMapping.length());
			} else {
				return null;
			}
		}
		String resourceScopePath = StringUtilsE.deleteEndsSlash(this.resourceScopePath);
		resourcePath = StringUtilsE.appendStartsSlash(resourcePath);
		return ResourcesUtils.getResourceAsStream(resourceScopePath + resourcePath,
				Thread.currentThread().getContextClassLoader(), DefaultScopeResourceSupplier.class);
	}

}

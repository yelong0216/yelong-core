package org.yelong.core.resource.defaults;

import java.io.InputStream;

import org.yelong.commons.util.ResourcesUtils;
import org.yelong.core.resource.ResourceSupplier;

/**
 * 简单的资源供应商
 * 
 * @since 2.2
 */
public final class SimpleResourceSupplier implements ResourceSupplier{

	public static final ResourceSupplier INSTANCE = new SimpleResourceSupplier();
	
	private SimpleResourceSupplier() {
	}
	
	@Override
	public InputStream getResourceAsStream(String resourcePath) {
		return ResourcesUtils.getResourceAsStream(resourcePath);
	}

}

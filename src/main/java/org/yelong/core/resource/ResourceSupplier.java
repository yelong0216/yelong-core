/**
 * 
 */
package org.yelong.core.resource;

import java.io.InputStream;

import org.yelong.core.annotation.Nullable;

/**
 * 资源供应商
 * 
 * @since 2.2
 */
public interface ResourceSupplier {

	/**
	 * 获取资源
	 * 
	 * @param resourcePath 资源路径
	 * @return 资源流
	 */
	@Nullable
	InputStream getResourceAsStream(String resourcePath);

}

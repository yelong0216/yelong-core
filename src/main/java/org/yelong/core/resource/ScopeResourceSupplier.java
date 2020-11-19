/**
 * 
 */
package org.yelong.core.resource;

import java.io.InputStream;

import org.yelong.core.annotation.Nullable;

/**
 * 范围资源供应商
 * 
 * @since 2.2
 */
public interface ScopeResourceSupplier extends ResourceSupplier {

	/**
	 * 资源的URL映射。这个不支持正则。仅是一个普通的字符串名称
	 * 
	 * <pre>
	 * 例子：/yelong/resources
	 * </pre>
	 * 
	 * @return 资源范围
	 * @see #getResourceStream(String, boolean)
	 */
	@Nullable
	String getURLMapping();

	/**
	 * 资源所处于的范围，也就是资源的根路径。
	 * 
	 * <pre>
	 * 例子：org/yelong/core/resource
	 * </pre>
	 * 
	 * @return 资源范围
	 */
	@Nullable
	String getResourceScopePath();

	/**
	 * 获取资源。这个资源将从指定的范围内获取，且资源的路径是从指定的范围开始的。
	 * 
	 * <pre>
	 * 资源范围：org/yelong/core/resource
	 * 资源路径：yml/application.yml
	 * 获取的资源真实路径：org/yelong/core/resource/yml/application.yml
	 * </pre>
	 * 
	 * @param resourcePath 资源路径
	 * @return 资源流
	 */
	@Override
	default InputStream getResourceAsStream(String resourcePath) {
		return getResourceAsStream(resourcePath, true);
	}

	/**
	 * 获取资源。这个资源将从指定的范围内获取，且资源的路径是从指定的范围开始的。
	 * 
	 * <pre>
	 * 例：
	 * 资源范围：org/yelong/core/resource
	 * 资源路径：yml/application.yml
	 * 获取的资源真实路径：org/yelong/core/resource/yml/application.yml
	 * ----
	 * 也可以通过 URLMapping作为请求资源的前缀。该功能仅在urlMapping为true起作用。
	 * 且如果资源路径不是以URLMapping为前缀则采用源方式获取资源。
	 * 例：
	 * URLMapping：yelongCoreResource
	 * 资源范围：org/yelong/core/resource
	 * 资源路径：yelongCoreResource/yml/application.yml
	 * 获取的资源真实路径：org/yelong/core/resource/yml/application.yml
	 * </pre>
	 * 
	 * @param resourcePath 资源路径
	 * @param urlMapping   是否启用URL映射作为资源前缀。
	 * @return 资源流
	 */
	@Nullable
	InputStream getResourceAsStream(String resourcePath, boolean urlMapping);

}

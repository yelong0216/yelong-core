/**
 * 
 */
package org.yelong.core.order;

/**
 * 顺序支持
 * 
 * <pre>
 * 任何实现了该接口的集合都可以通过后面方法进行排序: list.sort(OrderComparator.ASC_INSTANCE);
 * </pre>
 * 
 * @since 1.3
 */
public interface Orderable {

	/**
	 * 最高优先级
	 * 
	 * @see java.lang.Integer#MIN_VALUE
	 */
	int HIGHEST_PRECEDENCE = Integer.MIN_VALUE;

	/**
	 * 最低优先级
	 * 
	 * @see java.lang.Integer#MAX_VALUE
	 */
	int LOWEST_PRECEDENCE = Integer.MAX_VALUE;

	/**
	 * @return 顺序
	 */
	int getOrder();

}

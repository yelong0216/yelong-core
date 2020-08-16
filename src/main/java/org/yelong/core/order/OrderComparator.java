/**
 * 
 */
package org.yelong.core.order;

import java.util.Comparator;
import java.util.Objects;

/**
 * 顺序比较器
 * 
 * @since 1.3
 */
public class OrderComparator implements Comparator<Orderable> {

	private final OrderDirection orderDirection;

	/**
	 * 升序排序（从小到大）
	 */
	public static final OrderComparator ASC_INSTANCE = new OrderComparator(OrderDirection.ASC);

	/**
	 * 降序排序（从大到小）
	 */
	public static final OrderComparator DESC_INSTANCE = new OrderComparator(OrderDirection.DESC);

	public OrderComparator() {
		this(OrderDirection.ASC);
	}

	public OrderComparator(OrderDirection orderDirection) {
		this.orderDirection = Objects.requireNonNull(orderDirection);
	}

	public static OrderComparator getOrderComparator(OrderDirection orderDirection) {
		Objects.requireNonNull(orderDirection);
		switch (orderDirection) {
		case ASC:
			return ASC_INSTANCE;
		case DESC:
			return DESC_INSTANCE;
		}
		throw new UnsupportedOperationException();
	}

	@Override
	public int compare(Orderable o1, Orderable o2) {
		if (orderDirection == OrderDirection.DESC) {
			return Integer.compare(o2.getOrder(), o1.getOrder());
		} else {
			return Integer.compare(o1.getOrder(), o2.getOrder());
		}
	}

}

/**
 * 
 */
package org.yelong.core.support;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.yelong.core.annotation.Nullable;
import org.yelong.core.order.OrderComparator;
import org.yelong.core.order.OrderDirection;
import org.yelong.core.order.Orderable;

/**
 * 字典
 * 
 * @param <T> 字典类型 type
 * @param <V> 值 type
 * @param <C> 内容 type
 * @since 1.2
 */
public class Dictionary<T, V, C> {

	@Nullable
	private final T type;

	private final Map<V, DictionaryAttribute<V, C>> dictionaryAttributeMap = new LinkedHashMap<>();

	/**
	 * @param type 字典类型
	 */
	public Dictionary(@Nullable final T type) {
		this.type = type;
	}

	// ==================================================add==================================================

	/**
	 * 添加字典属性
	 * 
	 * @param value   属性值
	 * @param content 属性内容
	 * @return this
	 */
	public Dictionary<T, V, C> addAttribute(@Nullable V value, @Nullable C content) {
		return addAttribute(value, content, null);
	}

	/**
	 * 添加字典属性
	 * 
	 * @param value   属性值
	 * @param content 属性内容
	 * @param order   属性的顺序
	 * @return this
	 */
	public Dictionary<T, V, C> addAttribute(@Nullable V value, @Nullable C content, @Nullable Integer order) {
		return addAttribute(new DictionaryAttributeImpl<V, C>(value, content, order));
	}

	/**
	 * 添加字典属性。在字典里面字典属性值时唯一的。如果已经存在了相同的字典值将会替换掉已存在得字典属性值。
	 * 
	 * @param dictionaryAttribute 字典属性
	 * @return this
	 */
	public Dictionary<T, V, C> addAttribute(DictionaryAttribute<V, C> dictionaryAttribute) {
		dictionaryAttributeMap.put(dictionaryAttribute.getValue(), dictionaryAttribute);
		return this;
	}

	// ==================================================get==================================================

	/**
	 * 根据字典值获取字典属性
	 * 
	 * @param value 字典的属性值
	 * @return 字典属性值为指定值的字典属性
	 */
	@Nullable
	public DictionaryAttribute<V, C> getDictionaryAttribute(V value) {
		return dictionaryAttributeMap.get(value);
	}

	/**
	 * 根据字典值的内容获取字典属性。
	 * 
	 * @param content 字典的属性内容
	 * @return 字典属性内容为指定内容的自动那属性集合
	 */
	public List<DictionaryAttribute<V, C>> getDictionaryAttributeByContent(@Nullable C content) {
		return getDictionaryAttributes().stream().filter(x -> {
			if (content == null) {
				return null == x.getContent();
			}
			return x.getContent().equals(content);
		}).collect(Collectors.toList());
	}

	/**
	 * 根据属性值获取属性内容
	 * 
	 * @param value 属性值
	 * @return 属性值对应的属性的内容，如果字典中不存在该属性值，则返回 <code>null</code>
	 */
	@Nullable
	public C getContent(V value) {
		DictionaryAttribute<V, C> dictionaryAttribute = getDictionaryAttribute(value);
		if (null == dictionaryAttribute) {
			return null;
		}
		return dictionaryAttribute.getContent();
	}

	/**
	 * 根据属性值获取属性内容，当属性内容为 <code>null</code> 则返回 defaultValue
	 * 
	 * @param value          属性值
	 * @param defaultContent 属性内容为空时返回的默认属性内容
	 * @return 属性内容
	 */
	public C getContent(V value, C defaultContent) {
		C content = getContent(value);
		return content != null ? content : defaultContent;
	}

	/**
	 * @return 获取所有的属性值映射
	 */
	public Map<V, DictionaryAttribute<V, C>> getDictionaryAttributeMap() {
		return dictionaryAttributeMap;
	}

	/**
	 * 获取所有的字典属性
	 * 
	 * @return 所有的字典属性
	 */
	@Nullable
	public List<DictionaryAttribute<V, C>> getDictionaryAttributes() {
		return getDictionaryAttributes(null);
	}

	/**
	 * 获取所有的字典属性，属性默认根据排序方向进行排序
	 * 
	 * @return 排序后的所有字典属性
	 */
	@Nullable
	public List<DictionaryAttribute<V, C>> getDictionaryAttributes(@Nullable OrderDirection orderDirection) {
		List<DictionaryAttribute<V, C>> arrayList = new ArrayList<>(dictionaryAttributeMap.values());
		if (null != orderDirection) {
			arrayList.sort(OrderComparator.getOrderComparator(orderDirection));
		}
		return Collections.unmodifiableList(arrayList);
	}

	/**
	 * @return 字典类型
	 */
	@Nullable
	public T getType() {
		return type;
	}

	// ==================================================build==================================================

	/**
	 * 构建字典
	 * 
	 * @param <T>        type type
	 * @param <V>        value type
	 * @param <C>        content type
	 * @param type       字典类型
	 * @param attributes 字典属性集合
	 * @return 字典
	 */
	public static <T, V, C> Dictionary<T, V, C> build(@Nullable T type,
			@Nullable List<? extends DictionaryAttribute<V, C>> attributes) {
		Dictionary<T, V, C> dictionary = new Dictionary<T, V, C>(type);
		if (null != attributes) {
			attributes.forEach(dictionary::addAttribute);
		}
		return dictionary;
	}

	/**
	 * 构建字典
	 * 
	 * @param <T>        type type
	 * @param <V>        value type
	 * @param <C>        content type
	 * @param <A>        字典属性类型
	 * @param type       字典类型
	 * @param attributes 字典属性数组
	 * @return 字典
	 */
	public static <T, V, C, A extends DictionaryAttribute<V, C>> Dictionary<T, V, C> build(@Nullable T type,
			@Nullable A[] attributes) {
		Dictionary<T, V, C> dictionary = new Dictionary<T, V, C>(type);
		if (null != attributes) {
			for (A a : attributes) {
				dictionary.addAttribute(a);
			}
		}
		return dictionary;
	}

	// ==================================================DictionaryAttribute==================================================

	/**
	 * 字典屬性
	 *
	 * @param <V> value type
	 * @param <C> content type
	 */
	public static interface DictionaryAttribute<V, C> extends Orderable {

		/**
		 * @return 字典值
		 */
		@Nullable
		V getValue();

		/**
		 * @return 字典内容
		 */
		@Nullable
		C getContent();

		/**
		 * @return 字典顺序
		 */
		int getOrder();

	}

	/**
	 * 字典属性
	 */
	private static class DictionaryAttributeImpl<V, C>
			implements DictionaryAttribute<V, C>, Comparable<DictionaryAttribute<V, C>> {

		private final V value;

		private final C content;

		private final int order;

		public final static int DEFAULT_ORDER = LOWEST_PRECEDENCE;

		/**
		 * @param value   顺序值
		 * @param content 属性内容
		 * @param order   属性的顺序。默认为 {@link #DEFAULT_ORDER}
		 */
		private DictionaryAttributeImpl(@Nullable V value, @Nullable C content, @Nullable Integer order) {
			this.value = value;
			this.content = content;
			this.order = order == null ? DEFAULT_ORDER : order;
		}

		public V getValue() {
			return value;
		}

		public C getContent() {
			return content;
		}

		public int getOrder() {
			return order;
		}

		@Override
		public int compareTo(DictionaryAttribute<V, C> o) {
			return OrderComparator.ASC_INSTANCE.compare(this, o);
		}

	}

}

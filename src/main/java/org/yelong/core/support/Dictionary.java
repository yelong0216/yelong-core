/**
 * 
 */
package org.yelong.core.support;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.yelong.core.annotation.Nullable;

/**
 * 字典
 * 
 * @author PengFei
 * @param <T> 字典类型 type
 * @param <V> 值 type
 * @param <C> 内容 type
 * @since 1.2.0
 */
public class Dictionary<T, V, C> {

	private final T type;

	private final Map<V, DictionaryAttribute<V, C>> dictionaryAttributeMap = new HashMap<>();

	/**
	 * @param type 字典类型
	 */
	public Dictionary(final T type) {
		this.type = Objects.requireNonNull(type);
	}

	/**
	 * 添加字典属性
	 * 
	 * @param value   属性值
	 * @param content 属性内容
	 * @return this
	 */
	public Dictionary<T, V, C> addAttribute(V value, C content) {
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
	public Dictionary<T, V, C> addAttribute(V value, C content, @Nullable Integer order) {
		dictionaryAttributeMap.put(value, new DictionaryAttribute<V, C>(value, content, order));
		return this;
	}

	/**
	 * 根据属性值获取属性内容
	 * 
	 * @param value 属性值
	 * @return 属性的内容，如果字典中不存在该属性值，则返回 <code>null</code>
	 */
	@Nullable
	public C getContent(V value) {
		if (null == value) {
			return null;
		}
		DictionaryAttribute<V, C> dictionaryAttribute = dictionaryAttributeMap.get(value);
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
	 * 获取所有的字典属性，属性默认根据顺序进行排序（升序）
	 * 
	 * @return 排序后的所有字典属性
	 */
	public List<DictionaryAttribute<V, C>> getDictionaryAttributes() {
		List<DictionaryAttribute<V, C>> arrayList = new ArrayList<>(dictionaryAttributeMap.values());
		arrayList.sort((x, y) -> {
			return y.compareTo(x);
		});
		return Collections.unmodifiableList(arrayList);
	}

	/**
	 * @return 字典类型
	 */
	public T getType() {
		return type;
	}

	/**
	 * 字典属性
	 */
	public static class DictionaryAttribute<V, C> implements Comparable<DictionaryAttribute<V, C>> {

		private final V value;

		private final C content;

		private final Integer order;

		public final static Integer DEFAULT_ORDER = Integer.MAX_VALUE;

		/**
		 * @param value   顺序值
		 * @param content 属性内容
		 * @param order   属性的顺序。默认为 {@link #DEFAULT_ORDER}
		 */
		public DictionaryAttribute(V value, C content, @Nullable Integer order) {
			this.value = Objects.requireNonNull(value);
			this.content = Objects.requireNonNull(content);
			this.order = order == null ? DEFAULT_ORDER : order;
		}

		public V getValue() {
			return value;
		}

		public C getContent() {
			return content;
		}

		public Integer getOrder() {
			return order;
		}

		@Override
		public int compareTo(DictionaryAttribute<V, C> obj) {
			return Integer.compare(obj.order, this.order);
		}

	}

}

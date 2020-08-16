/**
 * 
 */
package org.yelong.core.jdbc.sql.condition.support;

import static org.yelong.core.jdbc.sql.condition.ConditionalOperator.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * 条件工厂
 * 
 * @since 2.0
 */
public final class ConditionFactory {

	private ConditionFactory() {
	}

	/**
	 * 创建某列等于某值的条件
	 * 
	 * @param column 列
	 * @param value  值
	 * @return 条件
	 */
	public static Condition equal(String column, Object value) {
		return new Condition(column, EQUAL, value);
	}

	/**
	 * 创建某列包含某些值得条件
	 * 
	 * @param column 列
	 * @param value  值
	 * @return 条件（支持集合、数组）
	 */
	@SuppressWarnings("unchecked")
	public static Condition in(String column, Object value) {
		List<Object> values = null;
		if (value == null) {
			values = new ArrayList<Object>(1);
			values.add(null);
		} else if (value.getClass().isArray()) {
			values = Arrays.asList((Object[]) value);
		} else if (value instanceof List) {
			values = (List<Object>) value;
		} else if (value instanceof Collection) {
			values = new ArrayList<Object>((Collection<Object>) value);
		} else {
			value = Arrays.asList(value);
		}
		return new Condition(column, IN, values);
	}

}

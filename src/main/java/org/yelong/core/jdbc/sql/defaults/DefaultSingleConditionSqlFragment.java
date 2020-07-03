/**
 * 
 */
package org.yelong.core.jdbc.sql.defaults;

import static org.yelong.core.jdbc.sql.SpliceSqlUtils.spliceSqlFragment;

import java.util.List;

import org.yelong.core.jdbc.SqlKeyword;
import org.yelong.core.jdbc.dialect.Dialect;
import org.yelong.core.jdbc.sql.condition.single.AbstractSingleConditionSqlFragment;

/**
 * 默认的单一条件实现
 * 
 * @author PengFei
 */
public class DefaultSingleConditionSqlFragment extends AbstractSingleConditionSqlFragment {

	// 默认参数占位符
	private static final String DEFAULT_PARAM_PLACEHOLDER = "?";

	private static final String LEFT_BRACKET = "(";

	private static final String RIGHT_BRACKET = ")";

	private static final String COMMA = ",";

	private static final String AND = SqlKeyword.AND.getKeyword();

	/**
	 * @param column    列名称
	 * @param condition 条件
	 */
	public DefaultSingleConditionSqlFragment(Dialect dialect, String column, String condition) {
		super(dialect, column, condition);
	}

	/**
	 * @param column    列名称
	 * @param condition 条件
	 * @param value     值。如果value为List类型则表示条件为不定值类型的参数
	 */
	public DefaultSingleConditionSqlFragment(Dialect dialect, String column, String condition, Object value) {
		super(dialect, column, condition, value);
	}

	/**
	 * 这是一个between条件
	 * 
	 * @param column      列名称
	 * @param condition   条件
	 * @param value       第一个值
	 * @param secondValue 第二个值
	 */
	public DefaultSingleConditionSqlFragment(Dialect dialect, String column, String condition, Object value,
			Object secondValue) {
		super(dialect, column, condition, value, secondValue);
	}

	@SuppressWarnings("unchecked")
	@Override
	public String getSqlFragment() {
		if (isNoValue()) {
			return spliceSqlFragment(getColumn(), getConditionOperator());
		} else if (isSingleValue()) {
			return spliceSqlFragment(getColumn(), getConditionOperator(), DEFAULT_PARAM_PLACEHOLDER);
		} else if (isBetweenValue()) {
			return spliceSqlFragment(getColumn(), getConditionOperator(), DEFAULT_PARAM_PLACEHOLDER, AND,
					DEFAULT_PARAM_PLACEHOLDER);
		} else if (isListValue()) {
			List<Object> conditionValue = (List<Object>) getValue();
			int length = conditionValue.size();
			StringBuilder placeholder = new StringBuilder();
			String[] placeholders = new String[length * 2 - 1];
			for (int i = 0; i < length; i++) {
				placeholders[i * 2] = DEFAULT_PARAM_PLACEHOLDER;
				if (i != length - 1) {
					placeholders[i * 2 + 1] = COMMA;
				}
			}
			placeholder.append(spliceSqlFragment(placeholders));
			return where(spliceSqlFragment(getColumn(), getConditionOperator(), LEFT_BRACKET, placeholder.toString(),
					RIGHT_BRACKET));
		}
		throw new RuntimeException("未知的condition类型！");
	}

}

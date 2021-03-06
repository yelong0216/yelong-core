/**
 * 
 */
package org.yelong.core.jdbc.sql.condition.support;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.yelong.commons.lang.Strings;
import org.yelong.core.annotation.Nullable;
import org.yelong.core.jdbc.sql.condition.ConditionConnectWay;
import org.yelong.core.jdbc.sql.condition.ConditionalOperator;
import org.yelong.core.jdbc.sql.condition.ConditionalOperatorResolver;

/**
 * 条件对象
 */
public class Condition {

	// 列名
	private String column;

	// 运算符
	@Nullable
	private String operator;

	// 与 operator 只有一方可以为空
	@Nullable
	private ConditionalOperator conditionalOperator;

	// 列值。
	private Object value;

	private Object secondValue;

	private boolean noValue;

	private boolean singleValue;

	private boolean betweenValue;

	private boolean listValue;

	// 分组名称。在一组Condition中。groupName相同的视为一组条件
	// 用 ()将其包起来，且组第一个connectOperator是该组条件与之前条件的连接符
	private String groupName;

	// 此条件与前一条条件的 连接符 一般为 AND、OR
	private ConditionConnectWay connectWay = ConditionConnectWay.AND;

	public Condition(String column, String operator) {
		this.column = Strings.requireNonBlank(column);
		this.operator = Strings.requireNonBlank(operator);
		this.noValue = true;
	}

	/**
	 * @since 2.0
	 */
	public Condition(String column, ConditionalOperator conditionalOperator) {
		this.column = Strings.requireNonBlank(column);
		this.conditionalOperator = Objects.requireNonNull(conditionalOperator);
		this.noValue = true;
	}

	public Condition(String column, String operator, Object value) {
		this.column = Strings.requireNonBlank(column);
		this.operator = Strings.requireNonBlank(operator);
		this.value = value;
		if (null == value) {
			this.singleValue = true;
		} else if (value.getClass().isArray()) {
			// this.value = Arrays.asList(value); value为数组时经常转换不为集合
			this.value = Arrays.asList((Object[]) value);
			this.listValue = true;
		} else if (value instanceof List) {
			this.listValue = true;
		} else {
			this.singleValue = true;
		}
	}

	/**
	 * @since 2.0
	 */
	public Condition(String column, ConditionalOperator conditionalOperator, Object value) {
		this.column = Strings.requireNonBlank(column);
		this.conditionalOperator = Objects.requireNonNull(conditionalOperator);
		this.value = value;
		if (null == value) {
			this.singleValue = true;
		} else if (value.getClass().isArray()) {
			this.value = Arrays.asList((Object[]) value);
			this.listValue = true;
		} else if (value instanceof List) {
			this.listValue = true;
		} else {
			this.singleValue = true;
		}
	}

	public Condition(String column, String operator, Object value, Object secondValue) {
		this.column = Strings.requireNonBlank(column);
		this.operator = Strings.requireNonBlank(operator);
		this.value = value;
		this.secondValue = secondValue;
		this.betweenValue = true;
	}

	/**
	 * @since 2.0
	 */
	public Condition(String column, ConditionalOperator conditionalOperator, Object value, Object secondValue) {
		this.column = Strings.requireNonBlank(column);
		this.conditionalOperator = Objects.requireNonNull(conditionalOperator);
		this.value = value;
		this.secondValue = secondValue;
		this.betweenValue = true;
	}

	public String getColumn() {
		return column;
	}

	public String getOperator() {
		if (this.operator != null) {
			return operator;
		}
		throw new UnsupportedOperationException(
				"不支持直接获取运算符，该条件使用 ConditionalOperator 对象，应该使用 getConditionalOperator() 或者 getOperator(ConditionalOperatorResolver)");
	}

	public ConditionalOperator getConditionalOperator() {
		if (null != this.conditionalOperator) {
			return conditionalOperator;
		}
		throw new UnsupportedOperationException(
				"不支持直接获取条件运算符，该条件使用 operator 字符串，应该使用 getOperator() 或者 getOperator(ConditionalOperatorResolver)");
	}

	/**
	 * 获取运算符<br/>
	 * 
	 * 如果使用字符串当作运算符直接返回字符串<br/>
	 * 如果使用 {@link ConditionalOperator}，则通过解析器解析此运算符并返回
	 * 
	 * 
	 * @param conditionalOperatorResolver 条件运算符解析器
	 * @return 条件运算符
	 */
	public String getOperator(ConditionalOperatorResolver conditionalOperatorResolver) {
		if (this.operator != null) {
			return operator;
		}
		return conditionalOperatorResolver.resolve(conditionalOperator);
	}

	public Object getValue() {
		return value;
	}

	public Object getSecondValue() {
		return secondValue;
	}

	public boolean isNoValue() {
		return noValue;
	}

	public boolean isSingleValue() {
		return singleValue;
	}

	public boolean isBetweenValue() {
		return betweenValue;
	}

	public boolean isListValue() {
		return listValue;
	}

	public void setOperator(String operator) {
		this.operator = Strings.requireNonBlank(operator);
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public void setColumn(String column) {
		this.column = Strings.requireNonBlank(column);
	}

	public String getGroupName() {
		return groupName;
	}

	public Condition setGroupName(String groupName) {
		this.groupName = groupName;
		return this;
	}

	public ConditionConnectWay getConnectWay() {
		return connectWay;
	}

	public Condition setConnectWay(ConditionConnectWay connectWay) {
		this.connectWay = connectWay;
		return this;
	}

	@Override
	public String toString() {
		return "Condition [column=" + column + ", operator=" + operator + ", conditionalOperator=" + conditionalOperator
				+ ", value=" + value + ", secondValue=" + secondValue + ", noValue=" + noValue + ", singleValue="
				+ singleValue + ", betweenValue=" + betweenValue + ", listValue=" + listValue + ", groupName="
				+ groupName + ", connectWay=" + connectWay + "]";
	}

}

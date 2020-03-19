/**
 * 
 */
package org.yelong.core.jdbc.sql.condition.support;

import java.util.Arrays;
import java.util.List;

import org.yelong.core.jdbc.sql.condition.ConditionConnectWay;

/**
 * @author PengFei
 * @date 2020年2月24日上午8:54:07
 * @since 1.0
 */
public class Condition {

	//列名
	private String column;
	
	//运算符
	private String operator;
	
	//列值。
	private Object value;
	
	private Object secondValue;

	private boolean noValue;

	private boolean singleValue;

	private boolean betweenValue;

	private boolean listValue;
	
	//分组名称。在一组Condition中。groupName相同的视为一组条件 用 ()将其包起来，且组第一个connectOperator是该组条件与之前条件的连接符
	private String groupName;
	
	//此条件与前一条条件的 连接符 一般为 AND、OR
	private ConditionConnectWay connectWay = ConditionConnectWay.AND;
	
	public Condition(String column, String operator) {
		this.column = column;
		this.operator = operator;
		this.noValue = true;
	}
	
	public Condition(String column, String operator, Object value) {
		this.column = column;
		this.operator = operator;
		this.value = value;
		if( value.getClass().isArray() ) {
			this.value = Arrays.asList(value);
			this.listValue = true;
		} else if(value instanceof List) {
			this.listValue = true;
		} else {
			this.singleValue = true;
		}
	}
	
	public Condition(String column, String operator, Object value , Object secondValue) {
		this.column = column;
		this.operator = operator;
		this.value = value;
		this.secondValue = secondValue;
		this.betweenValue = true;
	}

	public String getColumn() {
		return column;
	}

	public String getOperator() {
		return operator;
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
		this.operator = operator;
	}
	
	public void setValue(Object value) {
		this.value = value;
	}
	
	public void setColumn(String column) {
		this.column = column;
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
		return "Condition [column=" + column + ", operator=" + operator + ", value=" + value + ", secondValue="
				+ secondValue + ", noValue=" + noValue + ", singleValue=" + singleValue + ", betweenValue="
				+ betweenValue + ", listValue=" + listValue + ", groupName=" + groupName + ", connectWay=" + connectWay
				+ "]";
	}
	
}

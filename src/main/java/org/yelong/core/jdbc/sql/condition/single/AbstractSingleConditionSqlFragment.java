/**
 * 
 */
package org.yelong.core.jdbc.sql.condition.single;

import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.yelong.core.jdbc.sql.SpliceSqlUtils;
import org.yelong.core.jdbc.sql.condition.AbstractConditionSqlFragment;

/**
 * 一个条件语句的抽象实现
 * @author 彭飞
 * @date 2019年8月22日下午6:04:52
 * @version 1.2
 */
public abstract class AbstractSingleConditionSqlFragment extends AbstractConditionSqlFragment implements SingleConditionSqlFragment{

	private String fieldName;

	private String condition;

	private Object value;

	private Object secondValue;

	private boolean noValue;

	private boolean singleValue;

	private boolean betweenValue;

	private boolean listValue;

	/**
	 * @param fieldName 字段名称
	 * @param condition 条件
	 */
	public AbstractSingleConditionSqlFragment(String fieldName,String condition) {
		this.fieldName = fieldName;
		this.condition = condition;
		this.noValue = true;
	}

	/**
	 * @param fieldName 字段名称
	 * @param condition 条件
	 * @param value 值。如果value为List类型则表示条件为不定值类型的参数
	 */
	public AbstractSingleConditionSqlFragment(String fieldName,String condition,Object value) {
		this.fieldName = fieldName;
		this.condition = condition;
		this.value = value;
		if (value instanceof List<?>) {
			this.listValue = true;
		} else {
			this.singleValue = true;
		}
	}

	/**
	 * 这是一个between条件
	 * @param fieldName 字段名称
	 * @param condition 条件
	 * @param value 第一个值
	 * @param secondValue 第二个值
	 */
	public AbstractSingleConditionSqlFragment(String fieldName,String condition,Object value,Object secondValue) {
		this.fieldName = fieldName;
		this.condition = condition;
		this.value = value;
		this.secondValue = secondValue;
		this.betweenValue = true;
	}

	
	@Override
	public String getFieldName() {
		return fieldName;
	}

	@Override
	public String getConditionOperator() {
		return condition;
	}

	@Override
	public Object getValue() {
		return value;
	}

	@Override
	public Object getSecondValue() {
		return secondValue;
	}

	@Override
	public boolean isNoValue() {
		return noValue;
	}

	@Override
	public boolean isSingleValue() {
		return singleValue;
	}

	@Override
	public boolean isBetweenValue() {
		return betweenValue;
	}

	@Override
	public boolean isListValue() {
		return listValue;
	}
	

	@Override
	public Object[] getParams() {
		Object [] conditionParams = null;
		if( isNoValue() ) {
			conditionParams = ArrayUtils.EMPTY_OBJECT_ARRAY;
		} else if(isSingleValue()) {
			conditionParams = new Object [] {value};
		} else if(isBetweenValue()) {
			conditionParams = new Object [] {value,secondValue};
		} else if(isListValue()) {
			conditionParams = ((List<?>)value).toArray();
		}
		return conditionParams;
	}
	
	@Override
	public String toString() {
		String str = SpliceSqlUtils.spliceSqlFragment(getFieldName(),getConditionOperator());
		if( isSingleValue() || isListValue()) {
			str = SpliceSqlUtils.spliceSqlFragment(str,getValue().toString());
		} else if( isBetweenValue() ) {
			str = SpliceSqlUtils.spliceSqlFragment(str,getValue().toString(),getSecondValue().toString());
		} 
		return str.toString();
	}
	
}

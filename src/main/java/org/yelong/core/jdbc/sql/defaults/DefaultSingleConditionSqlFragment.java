/**
 * 
 */
package org.yelong.core.jdbc.sql.defaults;

import static org.yelong.core.jdbc.sql.SpliceSqlUtils.spliceSqlFragment;

import java.util.List;

import org.yelong.core.jdbc.SqlKeyword;
import org.yelong.core.jdbc.sql.condition.single.AbstractSingleConditionSqlFragment;

/**
 * 条件
 * @author 彭飞
 * @date 2019年7月26日下午1:58:06
 */
public class DefaultSingleConditionSqlFragment extends AbstractSingleConditionSqlFragment{

	//默认参数占位符
	private static final String DEFAULT_PARAM_PLACEHOLDER = "?";

	private static final String LEFT_BRACKET = "(";

	private static final String RIGHT_BRACKET = ")";
	
	private static final String COMMA = ",";
	
	private static final String AND = SqlKeyword.AND.getKeyword();

	/**
	 * @param fieldName 字段名称
	 * @param condition 条件
	 */
	public DefaultSingleConditionSqlFragment(String fieldName,String condition) {
		super(fieldName, condition);
	}

	/**
	 * @param fieldName 字段名称
	 * @param condition 条件
	 * @param value 值。如果value为List类型则表示条件为不定值类型的参数
	 */
	public DefaultSingleConditionSqlFragment(String fieldName,String condition,Object value) {
		super(fieldName, condition, value);
	}

	/**
	 * 这是一个between条件
	 * @param fieldName 字段名称
	 * @param condition 条件
	 * @param value 第一个值
	 * @param secondValue 第二个值
	 */
	public DefaultSingleConditionSqlFragment(String fieldName,String condition,Object value,Object secondValue) {
		super(fieldName, condition, value, secondValue);
	}

	@SuppressWarnings("unchecked")
	@Override
	public String getSqlFragment() {
		if ( isNoValue() ) {
			return spliceSqlFragment(getFieldName(),getConditionOperator());
		} else if( isSingleValue() ) {
			return spliceSqlFragment(getFieldName(),getConditionOperator(),DEFAULT_PARAM_PLACEHOLDER);
		} else if( isBetweenValue() ) {
			return spliceSqlFragment(getFieldName(),getConditionOperator(),DEFAULT_PARAM_PLACEHOLDER,AND,DEFAULT_PARAM_PLACEHOLDER);
		} else if( isListValue() ) {
			List<Object> conditionValue =  (List<Object>) getValue();
			int length = conditionValue.size();
			StringBuilder placeholder = new StringBuilder();
			String [] placeholders = new String [length*2-1];
			for (int i = 0; i < length; i++) {
				placeholders[i*2] = DEFAULT_PARAM_PLACEHOLDER;
				if( i != length-1 ) {
					placeholders[i*2+1] = COMMA;
				}
			}
			placeholder.append(spliceSqlFragment(placeholders));
			return where(spliceSqlFragment(getFieldName(),getConditionOperator(),LEFT_BRACKET,placeholder.toString(),RIGHT_BRACKET));
		}
		throw new RuntimeException("未知的condition类型！");
	}

}

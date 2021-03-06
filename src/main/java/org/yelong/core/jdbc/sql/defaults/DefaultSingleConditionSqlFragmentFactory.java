/**
 * 
 */
package org.yelong.core.jdbc.sql.defaults;

import java.util.List;

import org.yelong.core.jdbc.dialect.Dialect;
import org.yelong.core.jdbc.sql.condition.single.SingleConditionSqlFragment;
import org.yelong.core.jdbc.sql.condition.single.SingleConditionSqlFragmentFactory;
import org.yelong.core.jdbc.sql.exception.InvalidConditionException;

/**
 * 默认的单一条件工厂实现
 */
public class DefaultSingleConditionSqlFragmentFactory implements SingleConditionSqlFragmentFactory {

	private final Dialect dialect;

	public DefaultSingleConditionSqlFragmentFactory(Dialect dialect) {
		this.dialect = dialect;
	}

	@Override
	public SingleConditionSqlFragment create(String fieldName, String condition) throws InvalidConditionException {
		validConditionByNeedParamNum(condition, 0);
		return new DefaultSingleConditionSqlFragment(dialect, fieldName, condition);
	}

	@Override
	@SuppressWarnings("unchecked")
	public SingleConditionSqlFragment create(String fieldName, String condition, Object value)
			throws InvalidConditionException {
		if (value instanceof List) {
			return create(fieldName, condition, ((List<Object>) value));
		}
		validConditionByNeedParamNum(condition, 1);
		return new DefaultSingleConditionSqlFragment(dialect, fieldName, condition, value);
	}

	@Override
	public SingleConditionSqlFragment create(String fieldName, String condition, List<Object> value)
			throws InvalidConditionException {
		validConditionByNeedParamNum(condition, value.size());
		return new DefaultSingleConditionSqlFragment(dialect, fieldName, condition, value);
	}

	@Override
	public SingleConditionSqlFragment create(String fieldName, String condition, Object value1, Object value2)
			throws InvalidConditionException {
		validConditionByNeedParamNum(condition, 2);
		return new DefaultSingleConditionSqlFragment(dialect, fieldName, condition, value1, value2);
	}

	/**
	 * 根据条件需要的参数个数来验证条件
	 * 
	 * @param condition 条件
	 * @param valueNum  值数量
	 * @throws InvalidConditionException 条件异常
	 */
	protected void validConditionByNeedParamNum(String condition, Integer valueNum) throws InvalidConditionException {
		ConditionKeyword conditionKeyword = ConditionKeyword.valueOfByKeyword(condition);
		if (null == conditionKeyword) {
			throw new InvalidConditionException("未知的操作符：" + condition);
		}
		if (valueNum < conditionKeyword.getNeedLeastParamNumber()) {
			throw new InvalidConditionException("条件：" + condition + "需要至少：" + conditionKeyword.getNeedLeastParamNumber()
					+ "个参数值，但是现在只存在:" + valueNum + "个参数。");
		} else if (valueNum > conditionKeyword.getNeedMostParamNumber()) {
			throw new InvalidConditionException("条件：" + condition + "需要最多：" + conditionKeyword.getNeedMostParamNumber()
					+ "个参数值，但是现在存在:" + valueNum + "个参数。");
		}
	}

	@Override
	public Dialect getDialect() {
		return dialect;
	}

	/**
	 * 条件关键字<br/>
	 * 包含条件极其该条件所需要的参数个数<br/>
	 * 如果参数个数不限量则为 -1
	 */
	public static enum ConditionKeyword {

		LIKE("LIKE", 1, 1), NOT_LIKE("NOT LIKE", 1, 1), EQ("=", 1, 1), NOT_EQ("<>", 1, 1),
		IN("IN", 1, Integer.MAX_VALUE), NOT_IN("NOT IN", 1, Integer.MAX_VALUE), BETWEEN("BETWEEN", 2, 2),
		NOT_BETWEEN("NOT BETWEEN", 2, 2), IS_NULL("IS NULL", 0, 0), IS_NOT_NULL("IS NOT NULL", 0, 0), GT(">", 1, 1),
		GE(">=", 1, 1), LT("<", 1, 1), LE("<=", 1, 1);

		private final String keyword;

		private final Integer needLeastParamNumber;

		private final Integer needMostParamNumber;

		/**
		 * @param keyword              条件关键字
		 * @param needLeastParamNumber 条件需要的最少参数数量
		 * @param needMostParamNumber  条件需要的最多参数数量
		 */
		private ConditionKeyword(String keyword, Integer needLeastParamNumber, Integer needMostParamNumber) {
			this.keyword = keyword;
			this.needLeastParamNumber = needLeastParamNumber;
			this.needMostParamNumber = needMostParamNumber;
		}

		/**
		 * 根据条件关键字转换为ConditionKeyword对象
		 * 
		 * @param keyword 条件关键字
		 * @return 与之对应的ConditionKeyword对象。如果没有则返回null
		 */
		public static ConditionKeyword valueOfByKeyword(String keyword) {
			for (ConditionKeyword conditionKeyword : ConditionKeyword.values()) {
				if (conditionKeyword.getKeyword().equals(keyword.trim().toUpperCase())) {
					return conditionKeyword;
				}
			}
			return null;
		}

		public String getKeyword() {
			return keyword;
		}

		/**
		 * @return 条件需要的最少参数
		 */
		public Integer getNeedLeastParamNumber() {
			return needLeastParamNumber;
		}

		/**
		 * @return 条件需要的最多的参数个数
		 */
		public Integer getNeedMostParamNumber() {
			return needMostParamNumber;
		}

	}

}

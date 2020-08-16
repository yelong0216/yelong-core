/**
 * 
 */
package org.yelong.core.jdbc.sql.condition.combination;

import static org.yelong.core.jdbc.sql.SpliceSqlUtils.spliceSqlFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.ArrayUtils;
import org.yelong.core.jdbc.sql.condition.AbstractConditionSqlFragment;
import org.yelong.core.jdbc.sql.condition.ConditionConnectWay;
import org.yelong.core.jdbc.sql.condition.ConditionSqlFragment;
import org.yelong.core.jdbc.sql.condition.single.SingleConditionSqlFragmentFactory;
import org.yelong.core.jdbc.sql.exception.InvalidConditionException;

/**
 * 抽象组合条件片段实现
 */
public abstract class AbstractCombinationConditionSqlFragment extends AbstractConditionSqlFragment
		implements CombinationConditionSqlFragment {

	private final SingleConditionSqlFragmentFactory singleConditionSqlFragmentFactory;

	private final List<ConditionSqlFragmentWrapper> conditionSqlFragmentList = new ArrayList<ConditionSqlFragmentWrapper>();

	public AbstractCombinationConditionSqlFragment(
			SingleConditionSqlFragmentFactory singleConditionSqlFragmentFactory) {
		super(Objects.requireNonNull(singleConditionSqlFragmentFactory).getDialect());
		this.singleConditionSqlFragmentFactory = singleConditionSqlFragmentFactory;
	}

	@Override
	public CombinationConditionSqlFragment and(String column, String conditionOperator) throws InvalidConditionException {
		return addCondition(ConditionConnectWay.AND, singleConditionSqlFragmentFactory.create(column, conditionOperator));
	}

	@Override
	public CombinationConditionSqlFragment and(String column, String conditionOperator, Object value)
			throws InvalidConditionException {
		return addCondition(ConditionConnectWay.AND,
				singleConditionSqlFragmentFactory.create(column, conditionOperator, value));
	}

	@Override
	public CombinationConditionSqlFragment and(String column, String conditionOperator, List<Object> value)
			throws InvalidConditionException {
		return addCondition(ConditionConnectWay.AND,
				singleConditionSqlFragmentFactory.create(column, conditionOperator, value));
	}

	@Override
	public CombinationConditionSqlFragment and(String column, String conditionOperator, Object value1, Object value2)
			throws InvalidConditionException {
		return addCondition(ConditionConnectWay.AND,
				singleConditionSqlFragmentFactory.create(column, conditionOperator, value1, value2));
	}

	@Override
	public CombinationConditionSqlFragment and(ConditionSqlFragment conditionSqlFragment) {
		return addCondition(ConditionConnectWay.AND, conditionSqlFragment);
	}

	@Override
	public CombinationConditionSqlFragment or(String column, String conditionOperator) throws InvalidConditionException {
		return addCondition(ConditionConnectWay.OR, singleConditionSqlFragmentFactory.create(column, conditionOperator));
	}

	@Override
	public CombinationConditionSqlFragment or(String column, String conditionOperator, Object value)
			throws InvalidConditionException {
		return addCondition(ConditionConnectWay.OR, singleConditionSqlFragmentFactory.create(column, conditionOperator, value));
	}

	@Override
	public CombinationConditionSqlFragment or(String column, String conditionOperator, List<Object> value)
			throws InvalidConditionException {
		return addCondition(ConditionConnectWay.OR, singleConditionSqlFragmentFactory.create(column, conditionOperator, value));
	}

	@Override
	public CombinationConditionSqlFragment or(String column, String conditionOperator, Object value1, Object value2)
			throws InvalidConditionException {
		return addCondition(ConditionConnectWay.OR,
				singleConditionSqlFragmentFactory.create(column, conditionOperator, value1, value2));
	}

	@Override
	public CombinationConditionSqlFragment or(ConditionSqlFragment conditionSqlFragment) {
		return addCondition(ConditionConnectWay.OR, conditionSqlFragment);
	}

	/**
	 * 添加条件
	 * 
	 * @param conditionSpliceWay   条件拼接方式
	 * @param conditionSqlFragment 条件sql片段
	 */
	private CombinationConditionSqlFragment addCondition(ConditionConnectWay conditionSpliceWay,
			ConditionSqlFragment conditionSqlFragment) {
		// 默认子条件不会拼接 where
		conditionSqlFragment.setWhere(false);
		ConditionSqlFragmentWrapper ConditionSqlFragmentWrapper = beforeAddCondition(conditionSpliceWay,
				conditionSqlFragment);
		this.conditionSqlFragmentList.add(ConditionSqlFragmentWrapper);
		afterAddCondition(ConditionSqlFragmentWrapper);
		return this;
	}

	/**
	 * 在添加条件之前<br/>
	 * 重写此方法可定制条件语句及条件
	 * 
	 * @param conditionSpliceWay   条件拼接方式
	 * @param conditionSqlFragment 条件语句
	 * @return 条件语句包装器
	 */
	protected ConditionSqlFragmentWrapper beforeAddCondition(ConditionConnectWay conditionSpliceWay,
			ConditionSqlFragment conditionSqlFragment) {
		return new ConditionSqlFragmentWrapper(conditionSpliceWay, conditionSqlFragment);
	}

	/**
	 * 添加条件之后
	 * 
	 * @param ConditionSqlFragmentWrapper 条件语句包装器
	 */
	protected void afterAddCondition(ConditionSqlFragmentWrapper ConditionSqlFragmentWrapper) {

	}

	@Override
	public boolean isEmpty() {
		return this.conditionSqlFragmentList.isEmpty();
	}

	/**
	 * 获取条件语句<br/>
	 * 可以重写此方法定制自定义规则
	 * 
	 * @param conditionSqlFragmentList
	 * @return
	 */
	protected String generateConditionSqlFragment(List<ConditionSqlFragmentWrapper> conditionSqlFragmentList) {
		List<String> sqlFragment = new ArrayList<String>(conditionSqlFragmentList.size() * 2 + 2);
		conditionSqlFragmentList.forEach(x -> {
			ConditionSqlFragment conditionSqlFragment = x.getConditionSqlFragment();
			sqlFragment.add(x.getConditionSpliceWay().getKeyword());
			sqlFragment.add(conditionSqlFragment.getSqlFragment());
		});
		// 移除第一个and或者or
		sqlFragment.remove(sqlFragment.indexOf(conditionSqlFragmentList.get(0).getConditionSpliceWay().getKeyword()));
		return spliceSqlFragment(sqlFragment.toArray(ArrayUtils.EMPTY_STRING_ARRAY));
	}

	@Override
	public String getSqlFragment() {
		if (isEmpty()) {
			throw new UnsupportedOperationException("没有条件！");
		}
		return where(generateConditionSqlFragment(conditionSqlFragmentList));
	}

	@Override
	public Object[] getParams() {
		Object[] conditionParams = new Object[0];
		for (ConditionSqlFragmentWrapper ConditionSqlFragmentWrapper : conditionSqlFragmentList) {
			conditionParams = ArrayUtils.addAll(conditionParams,
					ConditionSqlFragmentWrapper.getConditionSqlFragment().getParams());
			// conditionParams = ArrayUtils.concatAll(conditionParams,
			// ConditionSqlFragmentWrapper.getconditionSqlFragment().getParams());
		}
		return conditionParams;
	}

	protected SingleConditionSqlFragmentFactory getSingleconditionSqlFragmentFactory() {
		return singleConditionSqlFragmentFactory;
	}

	@Override
	public String toString() {
		return getSqlFragment();
	}

	/**
	 * 添加语句包装器
	 */
	protected class ConditionSqlFragmentWrapper {

		private final ConditionConnectWay conditionSpliceWay;

		private final ConditionSqlFragment conditionSqlFragment;

		public ConditionSqlFragmentWrapper(ConditionConnectWay conditionSpliceWay,
				ConditionSqlFragment conditionSqlFragment) {
			this.conditionSpliceWay = conditionSpliceWay;
			this.conditionSqlFragment = conditionSqlFragment;
		}

		public ConditionConnectWay getConditionSpliceWay() {
			return conditionSpliceWay;
		}

		public ConditionSqlFragment getConditionSqlFragment() {
			return conditionSqlFragment;
		}

	}

}

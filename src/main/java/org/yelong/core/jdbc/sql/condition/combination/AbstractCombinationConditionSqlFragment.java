/**
 * 
 */
package org.yelong.core.jdbc.sql.condition.combination;

import static org.yelong.core.jdbc.sql.SpliceSqlUtils.spliceSqlFragment;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.yelong.core.jdbc.sql.condition.AbstractConditionSqlFragment;
import org.yelong.core.jdbc.sql.condition.ConditionConnectWay;
import org.yelong.core.jdbc.sql.condition.ConditionSqlFragment;
import org.yelong.core.jdbc.sql.condition.single.SingleConditionSqlFragmentFactory;
import org.yelong.core.jdbc.sql.exception.InvalidConditionException;

/**
 *	抽象组合条件片段
 * @author 彭飞
 * @date 2019年8月21日下午4:16:04
 * @version 1.2
 */
public abstract class AbstractCombinationConditionSqlFragment extends AbstractConditionSqlFragment implements CombinationConditionSqlFragment { 

	private final SingleConditionSqlFragmentFactory singleConditionFragmentFactory;

	private final List<ConditionFragmentWrapper> conditionFragmentList = new ArrayList<ConditionFragmentWrapper>();

	public AbstractCombinationConditionSqlFragment(SingleConditionSqlFragmentFactory singleConditionFragmentFactory) {
		this.singleConditionFragmentFactory = singleConditionFragmentFactory;
	}

	@Override
	public CombinationConditionSqlFragment and(String fieldName, String condition) throws InvalidConditionException {
		return addCondition(ConditionConnectWay.AND, singleConditionFragmentFactory.create(fieldName, condition));
	}

	@Override
	public CombinationConditionSqlFragment and(String fieldName, String condition, Object value) throws InvalidConditionException {
		return addCondition(ConditionConnectWay.AND, singleConditionFragmentFactory.create(fieldName, condition, value));
	}

	@Override
	public CombinationConditionSqlFragment and(String fieldName, String condition, List<Object> value) throws InvalidConditionException {
		return addCondition(ConditionConnectWay.AND, singleConditionFragmentFactory.create(fieldName, condition, value));
	}

	@Override
	public CombinationConditionSqlFragment and(String fieldName, String condition, Object value1, Object value2) throws InvalidConditionException {
		return addCondition(ConditionConnectWay.AND, singleConditionFragmentFactory.create(fieldName, condition, value1, value2));
	}

	@Override
	public CombinationConditionSqlFragment and(ConditionSqlFragment conditionFragment) {
		return addCondition(ConditionConnectWay.AND,conditionFragment);
	}

	@Override
	public CombinationConditionSqlFragment or(String fieldName, String condition) throws InvalidConditionException {
		return addCondition(ConditionConnectWay.OR, singleConditionFragmentFactory.create(fieldName, condition));
	}

	@Override
	public CombinationConditionSqlFragment or(String fieldName, String condition, Object value) throws InvalidConditionException {
		return addCondition(ConditionConnectWay.OR, singleConditionFragmentFactory.create(fieldName, condition, value));
	}

	@Override
	public CombinationConditionSqlFragment or(String fieldName, String condition, List<Object> value) throws InvalidConditionException {
		return addCondition(ConditionConnectWay.OR, singleConditionFragmentFactory.create(fieldName, condition, value));
	}

	@Override
	public CombinationConditionSqlFragment or(String fieldName, String condition, Object value1, Object value2) throws InvalidConditionException {
		return addCondition(ConditionConnectWay.OR, singleConditionFragmentFactory.create(fieldName, condition, value1, value2));
	}

	@Override
	public CombinationConditionSqlFragment or(ConditionSqlFragment conditionFragment) {
		return addCondition(ConditionConnectWay.OR, conditionFragment);
	}

	/**
	 * tian jia tian jian 
	 * @author 彭飞
	 * @date 2019年8月21日下午5:36:33
	 * @version 1.2
	 * @param conditionSpliceWay
	 * @param conditionFragment
	 */
	private CombinationConditionSqlFragment addCondition(ConditionConnectWay conditionSpliceWay , ConditionSqlFragment conditionFragment) {
		//默认子条件不会拼接 where
		conditionFragment.setWhere(false);
		ConditionFragmentWrapper conditionFragmentWrapper = beforeAddCondition(conditionSpliceWay, conditionFragment);
		this.conditionFragmentList.add(conditionFragmentWrapper);
		afterAddCondition(conditionFragmentWrapper);
		return this;
	}

	/**
	 * 在添加条件之前<br/>
	 * 重写此方法可定制条件语句及条件
	 * @author 彭飞
	 * @date 2019年8月28日上午8:44:39
	 * @version 1.2
	 * @param conditionSpliceWay 条件拼接方式
	 * @param conditionFragment 条件语句
	 * @return 条件语句包装器
	 */
	protected ConditionFragmentWrapper beforeAddCondition(ConditionConnectWay conditionSpliceWay , ConditionSqlFragment conditionFragment) {
		return new ConditionFragmentWrapper(conditionSpliceWay, conditionFragment);
	}

	/**
	 * 添加条件之后
	 * @author 彭飞
	 * @date 2019年8月28日上午8:49:25
	 * @version 1.2
	 * @param conditionFragmentWrapper 条件语句包装器
	 */
	protected void afterAddCondition(ConditionFragmentWrapper conditionFragmentWrapper) {

	}

	@Override
	public boolean isEmpty() {
		return this.conditionFragmentList.isEmpty();
	}

	/**
	 * 获取条件语句<br/>
	 * 可以重写此方法定制自定义规则
	 * @author 彭飞
	 * @date 2019年8月22日下午6:19:43
	 * @version 1.2
	 * @param conditionFragmentList
	 * @return
	 */
	protected String generateConditionFragment(List<ConditionFragmentWrapper> conditionFragmentList) {
		List<String> sqlFragment = new ArrayList<String>(conditionFragmentList.size()*2+2);
		conditionFragmentList.forEach(x->{
			ConditionSqlFragment conditionFragment = x.getConditionFragment();
			sqlFragment.add(x.getConditionSpliceWay().getKeyword());
			sqlFragment.add(conditionFragment.getSqlFragment());
		});
		//移除第一个and或者or
		sqlFragment.remove(sqlFragment.indexOf(conditionFragmentList.get(0).getConditionSpliceWay().getKeyword()));
		return spliceSqlFragment(sqlFragment.toArray(ArrayUtils.EMPTY_STRING_ARRAY));
	}

	@Override
	public String getSqlFragment() {
		if( isEmpty() ) {
			throw new UnsupportedOperationException("没有条件！");
		}
		return where(generateConditionFragment(conditionFragmentList));
	}

	@Override
	public Object[] getParams() {
		Object [] conditionParams = new Object[0];
		for (ConditionFragmentWrapper conditionFragmentWrapper : conditionFragmentList) {
			conditionParams = ArrayUtils.addAll(conditionParams, conditionFragmentWrapper.getConditionFragment().getParams());
			//conditionParams = ArrayUtils.concatAll(conditionParams, conditionFragmentWrapper.getConditionFragment().getParams());
		}
		return conditionParams;
	}

	protected SingleConditionSqlFragmentFactory getSingleConditionFragmentFactory() {
		return singleConditionFragmentFactory;
	}

	
	@Override
	public String toString() {
		return getSqlFragment();
	}

	/**
	 * 添加语句包装器
	 * @author 彭飞
	 * @date 2019年8月21日下午4:49:37
	 * @version 1.2
	 */
	protected class ConditionFragmentWrapper{

		private final ConditionConnectWay conditionSpliceWay;

		private final ConditionSqlFragment conditionFragment;

		public ConditionFragmentWrapper(ConditionConnectWay conditionSpliceWay, ConditionSqlFragment conditionFragment) {
			this.conditionSpliceWay = conditionSpliceWay;
			this.conditionFragment = conditionFragment;
		}

		public ConditionConnectWay getConditionSpliceWay() {
			return conditionSpliceWay;
		}

		public ConditionSqlFragment getConditionFragment() {
			return conditionFragment;
		}

	}



}

/**
 * 
 */
package org.yelong.core.jdbc.sql.defaults;

import static org.yelong.core.jdbc.sql.SpliceSqlUtils.spliceSqlFragment;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.yelong.core.jdbc.sql.condition.ConditionSqlFragment;
import org.yelong.core.jdbc.sql.condition.combination.AbstractCombinationConditionSqlFragment;
import org.yelong.core.jdbc.sql.condition.combination.CombinationConditionSqlFragment;
import org.yelong.core.jdbc.sql.condition.single.SingleConditionSqlFragmentFactory;

/**
 * 默认的组合条件实现
 * @author PengFei
 */
public class DefaultCombinationConditionSqlFragment extends AbstractCombinationConditionSqlFragment{

	public DefaultCombinationConditionSqlFragment() {
		this(new DefaultSingleConditionSqlFragmentFactory());
	}
	
	public DefaultCombinationConditionSqlFragment(SingleConditionSqlFragmentFactory singleConditionSqlFragmentFactory) {
		super(singleConditionSqlFragmentFactory);
	}

	/**
	 * 获取条件语句<br/>
	 * 可以重写此方法定制自定义规则
	 */
	@Override
	protected String generateConditionSqlFragment(List<ConditionSqlFragmentWrapper> conditionFragmentList) {
		List<String> sqlFragment = new ArrayList<String>(conditionFragmentList.size()*2+2);
		conditionFragmentList.forEach(x->{
			ConditionSqlFragment conditionFragment = x.getConditionSqlFragment();
			if( conditionFragment instanceof CombinationConditionSqlFragment ) {
				sqlFragment.add(x.getConditionSpliceWay().getKeyword());
				sqlFragment.add("(");
				sqlFragment.add(conditionFragment.getSqlFragment());
				sqlFragment.add(")");
			} else {
				sqlFragment.add(x.getConditionSpliceWay().getKeyword());
				sqlFragment.add(conditionFragment.getSqlFragment());
			}
		});
		//移除第一个and或者or
		sqlFragment.remove(sqlFragment.indexOf(conditionFragmentList.get(0).getConditionSpliceWay().getKeyword()));
		return spliceSqlFragment(sqlFragment.toArray(ArrayUtils.EMPTY_STRING_ARRAY));
	}
	
	
}

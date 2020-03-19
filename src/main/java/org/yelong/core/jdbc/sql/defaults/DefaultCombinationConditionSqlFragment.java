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

/**
 * @author PengFei
 * @date 2020年1月20日上午11:34:16
 */
public class DefaultCombinationConditionSqlFragment extends AbstractCombinationConditionSqlFragment{

	public DefaultCombinationConditionSqlFragment() {
		super(new DefaultSingleConditionSqlFragmentFactory());
	}

	/**
	 * 获取条件语句<br/>
	 * 可以重写此方法定制自定义规则
	 */
	protected String generateConditionFragment(List<ConditionFragmentWrapper> conditionFragmentList) {
		List<String> sqlFragment = new ArrayList<String>(conditionFragmentList.size()*2+2);
		conditionFragmentList.forEach(x->{
			ConditionSqlFragment conditionFragment = x.getConditionFragment();
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

/**
 * 
 */
package org.yelong.core.jdbc.sql.condition.support;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.yelong.core.jdbc.sql.condition.ConditionConnectWay;
import org.yelong.core.jdbc.sql.condition.combination.CombinationConditionSqlFragment;
import org.yelong.core.jdbc.sql.condition.single.SingleConditionSqlFragment;
import org.yelong.core.jdbc.sql.condition.single.SingleConditionSqlFragmentFactory;
import org.yelong.core.jdbc.sql.factory.SqlFragmentFactory;

/**
 * @author PengFei
 * @date 2020年2月25日上午11:47:27
 * @since 1.0
 */
public class DefaultConditionResolver extends AbstractConditionResolver{

	public DefaultConditionResolver(SqlFragmentFactory sqlFragmentFactory) {
		super(sqlFragmentFactory);
	}

	@Override
	public CombinationConditionSqlFragment resolve(List<Condition> conditionList) {
		CombinationConditionSqlFragment combinationConditionSqlFragment = getSqlFragmentFactory().createCombinationConditionSqlFragment();
		if( conditionList.size() == 0 ) {
			throw new ConditionResolverException("解析条件组时，条件数量不能为0");
		}
		final String EMPTY = "";
		//空的设置为空字符
		conditionList.forEach(x-> {
			if(StringUtils.isEmpty(x.getGroupName()))
				x.setGroupName(EMPTY);
		});
		Map<String, List<Condition>> groupCondition = conditionList.stream().collect(Collectors.groupingBy(Condition::getGroupName,LinkedHashMap::new,Collectors.toList()));
		//单条件集合
		List<Condition> emptyGroupConditionList = groupCondition.remove(EMPTY);
		if( null != emptyGroupConditionList ) {
			for (Condition condition : emptyGroupConditionList) {
				SingleConditionSqlFragment singleConditionSqlFragment = resolve(condition);
				switch(condition.getConnectWay()) {
				case AND:
					combinationConditionSqlFragment.and(singleConditionSqlFragment);
					break;
				case OR:
					combinationConditionSqlFragment.or(singleConditionSqlFragment);
					break;
				}
			}
		}
		for (Entry<String, List<Condition>> entry : groupCondition.entrySet()) {
			List<Condition> value = entry.getValue();
			CombinationConditionSqlFragment ccsf = getSqlFragmentFactory().createCombinationConditionSqlFragment();
			ConditionConnectWay conditionConnectWay = value.get(0).getConnectWay();
			for (Condition condition : value) {
				 SingleConditionSqlFragment singleConditionSqlFragment = resolve(condition);
				switch(condition.getConnectWay()) {
				case AND:
					ccsf.and(singleConditionSqlFragment);
					break;
				case OR:
					ccsf.or(singleConditionSqlFragment);
					break;
				}
			}
			switch(conditionConnectWay) {
			case AND:
				combinationConditionSqlFragment.and(ccsf);
				break;
			case OR:
				combinationConditionSqlFragment.or(ccsf);
				break;
			}
		}
		return combinationConditionSqlFragment;
	}

	@Override
	public SingleConditionSqlFragment resolve(Condition condition) {
		String column = condition.getColumn();
		String operator = condition.getOperator();
		SingleConditionSqlFragmentFactory f = getSqlFragmentFactory().getSingleConditionSqlFragmentFactory();
		if(condition.isNoValue()) {
			return f.create(column, operator);
		} else if(condition.isSingleValue()) {
			return f.create(column, operator, condition.getValue());
		} else if(condition.isBetweenValue()) {
			return f.create(column, operator, condition.getValue(), condition.getSecondValue());
		} else if(condition.isListValue()) {
			return f.create(column, operator, condition.getValue());
		} else {
			throw new ConditionResolverException("未知的条件类型："+condition);
		}
	}

	
}

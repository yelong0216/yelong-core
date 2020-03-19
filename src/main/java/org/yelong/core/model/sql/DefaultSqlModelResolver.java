/**
 * 
 */
package org.yelong.core.model.sql;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.yelong.commons.beans.BeanUtils;
import org.yelong.core.jdbc.sql.condition.ConditionSqlFragment;
import org.yelong.core.jdbc.sql.condition.support.Condition;
import org.yelong.core.jdbc.sql.condition.support.ConditionResolver;
import org.yelong.core.jdbc.sql.factory.SqlFragmentFactory;
import org.yelong.core.jdbc.sql.sort.SortSqlFragment;
import org.yelong.core.model.resolve.ModelAndTable;
import org.yelong.core.model.resolve.ModelAndTableManager;

/**
 * @author PengFei
 * @date 2020年3月2日下午3:25:32
 * @since 1.0
 */
public class DefaultSqlModelResolver implements SqlModelResolver{

	private static final String DEFAULT_OPERATOR = "=";
	
	private final ModelAndTableManager modelAndTableManager;

	private final ConditionResolver conditionResolver;

	private final SqlFragmentFactory sqlFragmentFactory;

	public DefaultSqlModelResolver(ModelAndTableManager modelAndTableManager, ConditionResolver conditionResolver) {
		this(modelAndTableManager,conditionResolver,conditionResolver.getSqlFragmentFactory());
	}
	
	public DefaultSqlModelResolver(ModelAndTableManager modelAndTableManager, ConditionResolver conditionResolver,
			SqlFragmentFactory sqlFragmentFactory) {
		this.modelAndTableManager = modelAndTableManager;
		this.conditionResolver = conditionResolver;
		this.sqlFragmentFactory = sqlFragmentFactory;
	}

	@Override
	public ConditionSqlFragment resolveToCondition(SqlModel sqlModel, boolean isTableAlias) {
		ModelAndTable modelTable = modelAndTableManager.getModelAndTable(sqlModel.getModelClass());
		String tableAlias = modelTable.getTableAlias();
		//字段的条件符
		Map<String, String> conditionOperatorMap = sqlModel.getConditionOperatorMap();
		if(isTableAlias) {
			final Map<String,String> newconditionOperatorMap = new HashMap<String, String>(conditionOperatorMap.size());
			conditionOperatorMap.entrySet().forEach(x->{
				String key = x.getKey();
				if(!key.contains(".")) {
					key = tableAlias + "." + key;
				}
				newconditionOperatorMap.put(key, x.getValue());
			});
			conditionOperatorMap = newconditionOperatorMap;
		}
		//所有的条件（拓展属性与model所有非空属性）
		Map<String,Object> attributeMap = new HashMap<String, Object>();
		if( sqlModel.getClass() != SqlModel.class ) {//当sqlModel对象实例为SqlModel时，不进行属性获取和映射
			//model 中映射的字段
			List<String> mappingFieldName = modelTable.getFieldNames();
			// model 中非空字段条件
			for (String fieldName : mappingFieldName) {
				Object value = getBeanProperty(sqlModel, fieldName);
				if( null == value || (value instanceof String && StringUtils.isBlank((String)value))) {
					continue;
				}
				if (isTableAlias) {
					if(!fieldName.contains(".")) {
						attributeMap.put(tableAlias+"."+fieldName, value);
					}
				} else {
					attributeMap.put(fieldName, value);
				}
			}
		}
		//拓展字段条件。如果拓展字段存在于源model相同的条件则会覆盖
		Map<String, Object> extendAttributesMap = sqlModel.getExtendAttributesMap();
		for (Entry<String,Object> entry : extendAttributesMap.entrySet()) {
			String column = entry.getKey();
			Object value = entry.getValue();
			if (isTableAlias) {
				if(!column.contains(".")) {
					attributeMap.put(tableAlias+"."+column, value);
				}
			} else {
				attributeMap.put(column, value);
			}
		}
		List<Condition> conditions = new ArrayList<Condition>();
		//model的属性和拓展字段
		for (Entry<String, Object> entry : attributeMap.entrySet()) {
			String fieldName = entry.getKey();
			Object value = entry.getValue();
			//没有设置操作符默认为 = 
			String operator = conditionOperatorMap.containsKey(fieldName) ? conditionOperatorMap.get(fieldName) : DEFAULT_OPERATOR;
			conditions.add(new Condition(fieldName, operator, value));
		}
		//直接设置的Condition
		for (Condition condition : sqlModel.getConditions()) {
			String column = condition.getColumn();
			if(!column.contains(".")) {
				column = tableAlias + "." + column;
			}
			condition.setColumn(column);
			conditions.add(condition);
		}
		if( conditions.isEmpty() ) {
			return null;
		}
		return conditionResolver.resolve(conditions);
	}

	@Override
	public SortSqlFragment resolveToSort(SqlModel sqlModel, boolean tableAlias) {
		Map<String, String> sortFieldMap = sqlModel.getSortFieldMap();
		if(sortFieldMap.isEmpty()) {
			return null;
		}
		ModelAndTable modelAndTable = modelAndTableManager.getModelAndTable(sqlModel.getModelClass());
		SortSqlFragment sort = sqlFragmentFactory.createSortSqlFragment();
		String ta = modelAndTable.getTableAlias();
		for (Entry<String, String> entry : sortFieldMap.entrySet()) {
			String fieldName = entry.getKey();
			if(tableAlias) {
				if(!fieldName.contains(".")) {
					fieldName = ta+"."+fieldName;
				}
			}
			sort.addSort(fieldName, entry.getValue());
		}
		return sort;
	}

	protected Object getBeanProperty(Object bean , String fieldName) {
		try {
			return BeanUtils.getProperty(bean, fieldName);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public ModelAndTableManager getModelAndTableManager() {
		return this.modelAndTableManager;
	}
	
	@Override
	public SqlFragmentFactory getSqlFragmentFactory() {
		return this.sqlFragmentFactory;
	}
	
}

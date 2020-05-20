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
import org.yelong.core.jdbc.sql.condition.ConditionSqlFragment;
import org.yelong.core.jdbc.sql.condition.support.Condition;
import org.yelong.core.jdbc.sql.condition.support.ConditionResolver;
import org.yelong.core.jdbc.sql.factory.SqlFragmentFactory;
import org.yelong.core.jdbc.sql.sort.SortSqlFragment;
import org.yelong.core.model.Modelable;
import org.yelong.core.model.property.DefaultModelProperty;
import org.yelong.core.model.property.ModelProperty;
import org.yelong.core.model.resolve.ModelAndTable;
import org.yelong.core.model.resolve.ModelAndTableManager;

/**
 * 默认的 sql model 解析器
 * @author PengFei
 */
public class DefaultSqlModelResolver implements SqlModelResolver{

	private static final String DEFAULT_OPERATOR = "=";
	
	private final ModelAndTableManager modelAndTableManager;

	private final ConditionResolver conditionResolver;

	private final SqlFragmentFactory sqlFragmentFactory;
	
	private ModelProperty modelProperty = DefaultModelProperty.INSTANCE;

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
		Class<? extends Modelable> modelClass = sqlModel.getModelClass();
		Modelable model = sqlModel.getModel();
		boolean isSqlModel = modelClass == SqlModel.class;
		isTableAlias = isSqlModel ? false : isTableAlias;//如果时sqlModel，则不支持使用别名
		ModelAndTable modelAndTable = isSqlModel ? null : modelAndTableManager.getModelAndTable(modelClass);
		String tableAlias = isSqlModel ? null : modelAndTable.getTableAlias();
		//字段的条件符
		Map<String, String> conditionOperatorMap = sqlModel.getConditionOperators();
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
		if( model.getClass() != SqlModel.class ) {//当sqlModel对象实例为SqlModel时，不进行属性获取和映射
			//model 中映射的字段
			List<String> mappingFieldName = modelAndTable.getFieldNames();
			// model 中非空字段条件
			for (String fieldName : mappingFieldName) {
				Object value = getBeanProperty(model, fieldName);
				if( null == value || (value instanceof String && StringUtils.isBlank((String)value))) {
					continue;
				}
				if (isTableAlias) {
					if(!fieldName.contains(".")) {
						attributeMap.put(tableAlias+"."+fieldName, value);
					} else {
						attributeMap.put(fieldName, value);
					}
				} else {
					attributeMap.put(fieldName, value);
				}
			}
		}
		//拓展字段条件。如果拓展字段存在于源model相同的条件则会覆盖
		Map<String, Object> extendAttributesMap = sqlModel.getExtendAttributes();
		for (Entry<String,Object> entry : extendAttributesMap.entrySet()) {
			String column = entry.getKey();
			Object value = entry.getValue();
			if (isTableAlias) {
				if(!column.contains(".")) {
					attributeMap.put(tableAlias+"."+column, value);
				} else {
					attributeMap.put(column, value);
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
			if(isTableAlias) {
				if(!column.contains(".")) {
					column = tableAlias + "." + column;
				}
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
	public SortSqlFragment resolveToSort(SqlModel sqlModel, boolean isTableAlias) {
		Class<? extends Modelable> modelClass = sqlModel.getModelClass();
		boolean isSqlModel = modelClass == SqlModel.class;
		isTableAlias = isSqlModel ? false : isTableAlias;//如果时sqlModel，则不支持使用别名
		ModelAndTable modelAndTable = isSqlModel ? null : modelAndTableManager.getModelAndTable(modelClass);
		String tableAlias = isSqlModel ? null : modelAndTable.getTableAlias();
		Map<String, String> sortFieldMap = sqlModel.getSortFields();
		if(sortFieldMap.isEmpty()) {
			return null;
		}
		SortSqlFragment sort = sqlFragmentFactory.createSortSqlFragment();
		for (Entry<String, String> entry : sortFieldMap.entrySet()) {
			String fieldName = entry.getKey();
			if(isTableAlias) {
				if(!fieldName.contains(".")) {
					fieldName = tableAlias+"."+fieldName;
				}
			}
			sort.addSort(fieldName, entry.getValue());
		}
		return sort;
	}

	protected Object getBeanProperty(Object bean , String fieldName) {
		return modelProperty.get(bean, fieldName);
	}
	
	@Override
	public ModelAndTableManager getModelAndTableManager() {
		return this.modelAndTableManager;
	}
	
	@Override
	public SqlFragmentFactory getSqlFragmentFactory() {
		return this.sqlFragmentFactory;
	}
	
	@Override
	public ModelProperty getModelProperty() {
		return this.modelProperty;
	}
	
	public void setModelProperty(ModelProperty modelProperty) {
		this.modelProperty = modelProperty;
	}
}

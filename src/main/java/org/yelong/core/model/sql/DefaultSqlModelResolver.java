/**
 * 
 */
package org.yelong.core.model.sql;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.yelong.core.annotation.Nullable;
import org.yelong.core.jdbc.sql.condition.ConditionSqlFragment;
import org.yelong.core.jdbc.sql.condition.support.Condition;
import org.yelong.core.jdbc.sql.condition.support.ConditionResolver;
import org.yelong.core.jdbc.sql.factory.SqlFragmentFactory;
import org.yelong.core.jdbc.sql.sort.SortSqlFragment;
import org.yelong.core.model.Modelable;
import org.yelong.core.model.property.DefaultModelProperty;
import org.yelong.core.model.property.ModelProperty;
import org.yelong.core.model.resolve.ExtendColumnAttribute;
import org.yelong.core.model.resolve.FieldAndColumn;
import org.yelong.core.model.resolve.ModelAndTable;
import org.yelong.core.model.resolve.ModelAndTableManager;

/**
 * 默认的 sql model 解析器
 * 
 * @author PengFei
 */
public class DefaultSqlModelResolver implements SqlModelResolver {

	/**
	 * 默认的条件运算符
	 */
	public static final String DEFAULT_OPERATOR = "=";

	/**
	 * 表别名与列名的连接符
	 */
	public static final String DOT = ".";

	private final ModelAndTableManager modelAndTableManager;

	private final ConditionResolver conditionResolver;

	private final SqlFragmentFactory sqlFragmentFactory;

	private ModelProperty modelProperty = DefaultModelProperty.INSTANCE;

	public DefaultSqlModelResolver(ModelAndTableManager modelAndTableManager, ConditionResolver conditionResolver) {
		this(modelAndTableManager, conditionResolver, conditionResolver.getSqlFragmentFactory());
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
		isTableAlias = isSqlModel ? false : isTableAlias;// 如果时sqlModel，则不支持使用别名
		ModelAndTable modelAndTable = isSqlModel ? null : modelAndTableManager.getModelAndTable(modelClass);
		String tableAlias = isSqlModel ? null : modelAndTable.getTableAlias();
		// 字段的条件符
		Map<String, String> conditionOperatorMap = sqlModel.getConditionOperators();
		if (isTableAlias) {
			conditionOperatorMap = conditionOperatorAddAlias(conditionOperatorMap, tableAlias);
		}
		// 所有的条件（拓展属性与model所有非空属性）
		Map<String, Object> attributeMap = new HashMap<String, Object>();

		if (model.getClass() != SqlModel.class) {// 当sqlModel对象实例为SqlModel时，不进行属性获取和映射
			attributeMap.putAll(getModelNonNullAttributeConditionMap(model, modelClass, isTableAlias));
		}
		// 拓展字段条件。如果拓展字段存在于源model相同的条件则会覆盖

		Map<String, Object> extendAttributeConditionMap = getExtendAttributeConditionMap(sqlModel.getExtendAttributes(),
				isTableAlias ? tableAlias : null);
		attributeMap.putAll(extendAttributeConditionMap);

		List<Condition> conditions = new ArrayList<Condition>();
		conditions.addAll(toConditions(attributeMap, conditionOperatorMap));
		// 直接设置的Condition
		for (Condition condition : sqlModel.getConditions()) {
			String column = columnAddTableAlias(condition.getColumn(), isTableAlias ? tableAlias : null);
			condition.setColumn(column);
			conditions.add(condition);
		}
		conditions = afterResolveToCondition(sqlModel, isTableAlias, conditions);
		if (conditions.isEmpty()) {
			return null;
		}
		return conditionResolver.resolve(conditions);
	}

	/**
	 * 列与运算符中的列添加别名。 1、添加别名仅针对与列名中不存在“.”字符的。
	 * 
	 * @param conditionOperatorMap 条件运算符映射
	 * @param tableAlias           表别名
	 * @return 列添加表别名后的条件运算符映射。这是一个新的map
	 */
	protected Map<String, String> conditionOperatorAddAlias(Map<String, String> conditionOperatorMap,
			String tableAlias) {
		if (MapUtils.isEmpty(conditionOperatorMap)) {
			return Collections.emptyMap();
		}
		final Map<String, String> map = new HashMap<>(conditionOperatorMap.size());
		conditionOperatorMap.entrySet().forEach(x -> {
			map.put(columnAddTableAlias(x.getKey(), tableAlias), x.getValue());
		});
		return map;
	}

	/**
	 * 获取modle 所有非空的条件映射
	 * 
	 * @param model      获取属性与值映射的model
	 * @param modelClass 指定的数据库操作的model（条件对象model与查询的model可能不是一个model），主要负责提供表别名
	 * @return 列（列名添加了别名）与值映射
	 */
	protected Map<String, Object> getModelNonNullAttributeConditionMap(Modelable model,
			Class<? extends Modelable> modelClass, boolean isTableAlias) {
		Map<String, Object> attributeMap = new HashMap<String, Object>();
		ModelAndTable modelAndTable = modelAndTableManager.getModelAndTable(modelClass);
		List<FieldAndColumn> fieldAndColumns = modelAndTable.getFieldAndColumns();
		for (FieldAndColumn fieldAndColumn : fieldAndColumns) {
			String fieldName = fieldAndColumn.getFieldName();
			Object value = getBeanProperty(model, fieldName);
			if (null == value || (value instanceof String && StringUtils.isBlank((String) value))) {
				continue;
			}
			if (isTableAlias) {
				String columnTableAlias = modelAndTable.getTableAlias();
				// 如果列是拓展列，则别名为拓展列的表名，如果这个别名为空，则不添加这个条件
				if (fieldAndColumn.isExtend()) {
					ExtendColumnAttribute extendColumnAttribute = fieldAndColumn.getExtendColumnAttribute();
					columnTableAlias = extendColumnAttribute.getOfTableAlias();
					if (StringUtils.isBlank(columnTableAlias)) {
						continue;
					}
				}
				attributeMap.put(columnAddTableAlias(fieldName, columnTableAlias), value);
			} else {
				attributeMap.put(fieldName, value);
			}
		}
		return attributeMap;
	}

	/**
	 * 获取拓展属性的条件映射
	 * 
	 * @param extendAttribute 拓展属性map
	 * @param tableAlias      表别名，如果为空则列名不拼接表别名
	 * @return 列（列名添加了别名）与值映射
	 */
	protected Map<String, Object> getExtendAttributeConditionMap(Map<String, Object> extendAttributes,
			@Nullable String tableAlias) {
		if (MapUtils.isEmpty(extendAttributes)) {
			return Collections.emptyMap();
		}
		Map<String, Object> attributeMap = new HashMap<String, Object>();
		for (Entry<String, Object> entry : extendAttributes.entrySet()) {
			String column = entry.getKey();
			Object value = entry.getValue();
			attributeMap.put(columnAddTableAlias(column, tableAlias), value);
		}
		return attributeMap;
	}

	/**
	 * 将映射的属性和条件运算符结合转换为{@link Condition} 条件 默认的操作符为 {@link #DEFAULT_OPERATOR}
	 * 
	 * @param attributeMap         属性映射
	 * @param conditionOperatorMap 列与操作符映射
	 * @return 条件集合
	 */
	protected List<Condition> toConditions(Map<String, Object> attributeMap, Map<String, String> conditionOperatorMap) {
		if (MapUtils.isEmpty(attributeMap)) {
			return Collections.emptyList();
		}
		List<Condition> conditions = new ArrayList<Condition>();
		// model的属性和拓展字段
		for (Entry<String, Object> entry : attributeMap.entrySet()) {
			String fieldName = entry.getKey();
			Object value = entry.getValue();
			// 没有设置操作符默认为 =
			String operator = conditionOperatorMap.containsKey(fieldName) ? conditionOperatorMap.get(fieldName)
					: DEFAULT_OPERATOR;
			conditions.add(new Condition(fieldName, operator, value));
		}
		return conditions;
	}

	/**
	 * 在执行完 resolveToCondition 方法后调用，可重写此方法对条件进行修改（如：如果条件为 like 则添加模糊查询等）
	 * 
	 * @param sqlModel     被解析的SqlModel
	 * @param isTableAlias 是否需要添加别名
	 * @param conditions   解析后的条件集合
	 * @return 解析后的条件集合
	 */
	protected List<Condition> afterResolveToCondition(SqlModel sqlModel, boolean isTableAlias,
			List<Condition> conditions) {
		return conditions;
	}

	@Override
	public SortSqlFragment resolveToSort(SqlModel sqlModel, boolean isTableAlias) {
		Class<? extends Modelable> modelClass = sqlModel.getModelClass();
		boolean isSqlModel = modelClass == SqlModel.class;
		isTableAlias = isSqlModel ? false : isTableAlias;// 如果时sqlModel，则不支持使用别名
		ModelAndTable modelAndTable = isSqlModel ? null : modelAndTableManager.getModelAndTable(modelClass);
		String tableAlias = isSqlModel ? null : modelAndTable.getTableAlias();
		Map<String, String> sortFieldMap = sqlModel.getSortFields();
		if (sortFieldMap.isEmpty()) {
			return null;
		}
		SortSqlFragment sort = sqlFragmentFactory.createSortSqlFragment();
		for (Entry<String, String> entry : sortFieldMap.entrySet()) {
			String fieldName = columnAddTableAlias(entry.getKey(), isTableAlias ? tableAlias : null);
			sort.addSort(fieldName, entry.getValue());
		}
		return sort;
	}

	/**
	 * 列添加表别名
	 * 
	 * @param column     列名
	 * @param tableAlias 表别名 如果别名为null 直接返回列名
	 * @return 如果列存在“.”字符则之间返回，否则返回 tableAlias.column
	 */
	protected String columnAddTableAlias(String column, @Nullable String tableAlias) {
		if (StringUtils.isBlank(tableAlias)) {
			return column;
		}
		if (column.contains(DOT)) {
			return column;
		}
		return tableAlias + DOT + column;
	}

	protected Object getBeanProperty(Object bean, String fieldName) {
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

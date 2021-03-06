/**
 * 
 */
package org.yelong.core.model.sql;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.yelong.core.annotation.Nullable;
import org.yelong.core.jdbc.sql.SqlFragment;
import org.yelong.core.jdbc.sql.condition.support.Condition;
import org.yelong.core.jdbc.sql.sort.support.Sort;
import org.yelong.core.model.Modelable;

/**
 * 可以转换为 {@link SqlFragment}的Bean，这不是一个模型。
 * 
 * @see SqlModelResolver
 * @since 1.0
 */
public class SqlModel<M extends Modelable> {

	private final Map<String, String> conditionOperators = new HashMap<>();

	private final Map<String, Object> extendAttributes = new LinkedHashMap<>();

	private final Map<String, String> sortFields = new LinkedHashMap<>();

	private final List<String> groupColumns = new ArrayList<String>();

	private final List<Condition> conditions = new ArrayList<>();

	private final Class<M> modelClass;

	private final M model;

	/**
	 * 指定sql model为其本身
	 */
	public SqlModel() {
		this.modelClass = null;
		this.model = null;
	}

	/**
	 * 指定该sql model属于那个model。
	 * 
	 * @param modelClass
	 */
	public SqlModel(final Class<M> modelClass) {
		this.modelClass = modelClass;
		this.model = null;
	}

	/**
	 * @param model 指定model实体
	 */
	@SuppressWarnings("unchecked")
	public SqlModel(final M model) {
		this.modelClass = (Class<M>) model.getClass();
		this.model = model;
	}

	/**
	 * @param model 指定model实体
	 */
	public SqlModel(final M model, final Class<M> modelClass) {
		this.modelClass = modelClass;
		this.model = model;
	}

	/**
	 * 添加查询的条件操作符 这个查询条件映射的值为拓展属性或者其model对象中的属性值
	 * 
	 * @param column   列名称。如果不带表别名，且解析时需要别名，则别名默认为{@link #modelClass}的别名
	 * @param operator 条件运算符（LIKE,=等）
	 * @return this
	 */
	public SqlModel<M> addConditionOperator(String column, String operator) {
		this.conditionOperators.put(column, operator);
		return this;
	}

	/**
	 * @return 所有条件操作符
	 * @since 1.0.5
	 */
	public Map<String, String> getConditionOperators() {
		return conditionOperators;
	}

	/**
	 * 添加一个拓展属性 如果拓展属性已经存在则不会被替换，将会把拓展属性值转换为集合进行存储。 如果attrValue为数组将默认转换为list
	 * 
	 * @param attrName  属性名称
	 * @param attrValue 属性值 该值为数组时默认转换为集合
	 * @return this
	 */
	@SuppressWarnings("unchecked")
	public SqlModel<M> addExtendAttribute(String attrName, Object attrValue) {
		if (this.extendAttributes.containsKey(attrName)) {
			Object value = this.extendAttributes.get(attrName);
			List<Object> valueList = null;
			// 获取原值。如果原值不是List则改变原值为list
			if ((value instanceof List)) {
				valueList = (List<Object>) value;
			} else {
				valueList = new ArrayList<>();
				valueList.add(value);
			}

			if (attrValue instanceof Collection) {
				valueList.addAll((Collection<Object>) attrValue);
			} else if (attrValue.getClass().isArray()) {
				valueList.addAll(Arrays.asList(attrValue));
			} else {
				valueList.add(attrValue);
			}
			this.extendAttributes.put(attrName, valueList);
		} else {
			if (attrValue.getClass().isArray()) {
				List<Object> newValue = new ArrayList<>();
				newValue.addAll(Arrays.asList((Object[]) attrValue));
				attrValue = newValue;
			}
			this.extendAttributes.put(attrName, attrValue);
		}
		return this;
	}

	/**
	 * 添加拓展属性 如果存在拓展属性则会覆盖这个属性
	 * 
	 * @param attrName  属性名称
	 * @param attrValue 属性值
	 * @return this
	 * @since 1.0.5
	 */
	public SqlModel<M> addExtendAttributeOverride(String attrName, Object attrValue) {
		this.extendAttributes.put(attrName, attrValue);
		return this;
	}

	/**
	 * 移除一个拓展属性
	 * 
	 * @param attrName 属性 name
	 * @return this
	 */
	public SqlModel<M> removeExtendAttribute(String attrName) {
		this.extendAttributes.remove(attrName);
		return this;
	}

	/**
	 * 获取拓展属性
	 * 
	 * @param attrName 属性名称
	 * @return this
	 */
	@Nullable
	public Object getExtendAttribute(String attrName) {
		return this.extendAttributes.get(attrName);
	}

	/**
	 * 判断拓展属性中是否存在attrName
	 * 
	 * @param attrName 属性 name
	 * @return <tt>true</tt> attrName属性存在
	 * @since 1.0.5
	 */
	public boolean containsExtendAttribute(String attrName) {
		return this.extendAttributes.containsKey(attrName);
	}

	/**
	 * 是否存在拓展属性
	 * 
	 * @return <tt>true</tt> 存在拓展属性
	 * @since 1.0.5
	 */
	public boolean existExtendAttribute() {
		return !this.extendAttributes.isEmpty();
	}

	/**
	 * @return 取所有拓展属性
	 * @since 1.0.5
	 */
	public Map<String, Object> getExtendAttributes() {
		return extendAttributes;
	}

	/**
	 * 添加排序字段
	 * 
	 * @param sortField 字段
	 * @param sortOrder 排序方向
	 * @return this
	 */
	public SqlModel<M> addSortField(String sortField, String sortOrder) {
		this.sortFields.put(sortField, sortOrder);
		return this;
	}

	/**
	 * 添加排序字段
	 * 
	 * @param sortField 字段
	 * @param sortOrder 排序方向
	 * @return this
	 */
	public SqlModel<M> addSortFields(Map<String, String> sortFields) {
		this.sortFields.putAll(sortFields);
		return this;
	}

	/**
	 * 添加排序
	 * 
	 * @param sort 排序
	 * @return this
	 * @since 1.3.0
	 */
	public SqlModel<M> addSort(Sort sort) {
		addSortField(sort.getColumn(), sort.getDirection());
		return this;
	}

	/**
	 * 添加一组排序
	 * 
	 * @param sorts 排序信息集合
	 * @return this
	 * @since 1.3.0
	 */
	public SqlModel<M> addSorts(List<Sort> sorts) {
		for (Sort sort : sorts) {
			addSort(sort);
		}
		return this;
	}

	/**
	 * 移除一个排序字段
	 * 
	 * @param sortField 字段
	 * @return this
	 * @since 1.0.5
	 */
	public SqlModel<M> removeSortField(String sortField) {
		this.sortFields.remove(sortField);
		return this;
	}

	/**
	 * 排序中是否包含sortField字段
	 * 
	 * @param sortField 字段
	 * @return <tt>true</tt> 包含
	 * @since 1.0.5
	 */
	public boolean containsSortField(String sortField) {
		return this.sortFields.containsKey(sortField);
	}

	/**
	 * 是否存在排序
	 * 
	 * @return <tt>true</tt> 存在排序
	 * @since 1.0.5
	 */
	public boolean existSortField() {
		return !this.sortFields.isEmpty();
	}

	/**
	 * @return 所有的排序字段
	 * @since 1.0.5
	 */
	public Map<String, String> getSortFields() {
		return sortFields;
	}

	/**
	 * 添加一个条件
	 * 
	 * @param condition 条件
	 * @return this
	 */
	public SqlModel<M> addCondition(Condition condition) {
		this.conditions.add(condition);
		return this;
	}

	/**
	 * 添加一个条件
	 * 
	 * @param column   列
	 * @param operator 运算符
	 * @return this
	 * @see Condition#Condition(String, String)
	 * @since 1.2.0
	 */
	public SqlModel<M> addCondition(String column, String operator) {
		this.conditions.add(new Condition(column, operator));
		return this;
	}

	/**
	 * 添加一个条件
	 * 
	 * @param column   列
	 * @param operator 运算符
	 * @param value    值
	 * @return this
	 * @see Condition#Condition(String, String, Object)
	 * @since 1.2.0
	 */
	public SqlModel<M> addCondition(String column, String operator, Object value) {
		this.conditions.add(new Condition(column, operator, value));
		return this;
	}

	/**
	 * 添加一个条件
	 * 
	 * @param column      列
	 * @param operator    运算符
	 * @param value       值
	 * @param secondValue 第二个值
	 * @return this
	 * @see Condition#Condition(String, String, Object, Object)
	 * @since 1.2.0
	 */
	public SqlModel<M> addCondition(String column, String operator, Object value, Object secondValue) {
		this.conditions.add(new Condition(column, operator, value, secondValue));
		return this;
	}

	/**
	 * 添加一组条件
	 * 
	 * @param conditions 条件集合
	 * @return this
	 */
	public SqlModel<M> addConditions(Collection<Condition> conditions) {
		this.conditions.addAll(conditions);
		return this;
	}

	/**
	 * 是否存在条件
	 * 
	 * @return <tt>true</tt> 存在条件
	 * @since 1.0.5
	 */
	public boolean existCondition() {
		return !this.conditions.isEmpty();
	}

	/**
	 * @return 所有条件
	 */
	public List<Condition> getConditions() {
		return conditions;
	}

	/**
	 * 是否包含指定的分组列
	 * 
	 * @param column 是否包含的列
	 * @return <code>true</code> 包含
	 * @since 2.1
	 */
	public boolean containsGroupColumn(String column) {
		return this.groupColumns.contains(column);
	}

	/**
	 * 移除分组的列
	 * 
	 * @param columns 被移除的分组的列
	 * @return this
	 * @since 2.1
	 */
	public SqlModel<M> removeGroupColumn(String... columns) {
		if (ArrayUtils.isNotEmpty(columns)) {
			for (String column : columns) {
				groupColumns.remove(column);
			}
		}
		return this;
	}

	/**
	 * 是否存在分组列
	 * 
	 * @return <code>true</code> 存在分组列
	 * @since 2.1
	 */
	public boolean existGroupColumn() {
		return !this.groupColumns.isEmpty();
	}

	/**
	 * 添加分组列
	 * 
	 * @param columns 需要分组的列数组
	 * @return this
	 * @since 2.1
	 */
	public SqlModel<M> addGroupColumns(String... columns) {
		if (ArrayUtils.isNotEmpty(columns)) {
			for (String column : columns) {
				groupColumns.add(column);
			}
		}
		return this;
	}

	/**
	 * @return 所有的排序列
	 * @since 2.1
	 */
	public List<String> getGroupColumns() {
		return groupColumns;
	}

	/**
	 * 获取sqlModel对应的class 如果{@link #modelClass} != null 则返回{@link #modelClass}
	 * ，否则返回{@link #getClass()}
	 * 
	 * @return modelClass
	 */
	public Class<? extends Modelable> getModelClass() {
		return modelClass;
	}

	/**
	 * 获取model实体。如果没有设置model实体，则返回本身
	 * 
	 * @return model实体
	 * @since 1.0.5
	 */
	public M getModel() {
		return model;
	}

}

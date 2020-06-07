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

import org.yelong.core.annotation.Nullable;
import org.yelong.core.jdbc.sql.condition.support.Condition;
import org.yelong.core.model.Model;
import org.yelong.core.model.Modelable;

/**
 * 支持sql化的模型
 * 
 * @author PengFei
 * @see SqlModelResolver
 */
public class SqlModel extends Model{

	private static final long serialVersionUID = -3986147639250539909L;

	private final Map<String, String> conditionOperators = new HashMap<>();

	private final Map<String, Object> extendAttributes = new LinkedHashMap<>();

	private final Map<String, String> sortFields = new LinkedHashMap<>();

	private final List<Condition> conditions = new ArrayList<>();

	private final Class<? extends Modelable> modelClass;

	private final Modelable model;

	/**
	 * 指定sql model为其本身
	 */
	public SqlModel(){
		this.modelClass = null;
		this.model = null;
	}

	/**
	 * 指定该sql model属于那个model。
	 * 
	 * @param modelClass
	 */
	public SqlModel(Class<? extends Modelable> modelClass) {
		this.modelClass = modelClass;
		this.model = null;
	}

	/**
	 * @param model 指定model实体
	 */
	public SqlModel( Modelable model ) {
		if( model.getClass() == SqlModel.class ) {
			throw new UnsupportedOperationException("指定的model不能是SqlModel！");
		}
		this.modelClass = model.getClass();
		this.model = model;
	}
	
	/**
	 * @param model 指定model实体
	 */
	public SqlModel( Modelable model ,Class<? extends Modelable> modelClass) {
		if( model.getClass() == SqlModel.class ) {
			throw new UnsupportedOperationException("指定的model不能是SqlModel！");
		}
		this.modelClass = modelClass;
		this.model = model;
	}

	/**
	 * 添加查询的条件操作符
	 * 这个查询条件映射的值为拓展属性或者其model对象中的属性值
	 * 
	 * @param conditionName 条件字段名称。
	 * @param condition 条件
	 * @return this
	 */
	public SqlModel addConditionOperator(String column, String operator){
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
	 * 添加一个拓展属性
	 * 如果拓展属性已经存在则不会被替换，将会把拓展属性值转换为集合进行存储。
	 * 如果attrValue为数组将默认转换为list
	 * 
	 * @param attrName 属性名称
	 * @param attrValue 属性值 该值为数组时默认转换为集合
	 * @return this
	 */
	@SuppressWarnings("unchecked")
	public SqlModel addExtendAttribute(String attrName, Object attrValue) {
		if (this.extendAttributes.containsKey(attrName)) {
			Object value = this.extendAttributes.get(attrName);
			List<Object> valueList = null;
			//获取原值。如果原值不是List则改变原值为list
			if ((value instanceof List)) {
				valueList = (List<Object>)value;
			} else {
				valueList = new ArrayList<>();
				valueList.add(value);
			}

			if( attrValue instanceof Collection ) {
				valueList.addAll((Collection<Object>)attrValue);
			} else if( attrValue.getClass().isArray() ) {
				valueList.addAll(Arrays.asList(attrValue));
			} else {
				valueList.add(attrValue);
			}
			this.extendAttributes.put(attrName, valueList);
		} else {
			if(attrValue.getClass().isArray()) {
				List<Object> newValue = new ArrayList<>();
				newValue.addAll(Arrays.asList((Object [])attrValue));
				attrValue = newValue;
			}
			this.extendAttributes.put(attrName, attrValue);
		}
		return this;
	}

	/**
	 * 添加拓展属性
	 * 如果存在拓展属性则会覆盖这个属性
	 * 
	 * @param attrName 属性名称
	 * @param attrValue 属性值
	 * @return this
	 * @since 1.0.5
	 */
	public SqlModel addExtendAttributeOverride(String attrName, Object attrValue) {
		this.extendAttributes.put(attrName, attrValue);
		return this;
	}

	/**
	 * 移除一个拓展属性
	 * 
	 * @param attrName 属性 name
	 * @return this
	 */
	public SqlModel removeExtendAttribute(String attrName){
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
	public Object getExtendAttribute(String attrName){
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
	public SqlModel addSortField(String sortField, String sortOrder) {
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
	public SqlModel addSortFields(Map<String,String> sortFields) {
		this.sortFields.putAll(sortFields);
		return this;
	}

	/**
	 * 移除一个排序字段
	 * 
	 * @param sortField 字段
	 * @return this
	 * @since 1.0.5
	 */
	public SqlModel removeSortField(String sortField) {
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
		return ! this.sortFields.isEmpty();
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
	public SqlModel addCondition(Condition condition) {
		this.conditions.add(condition);
		return this;
	}

	/**
	 * 添加一个条件
	 * 
	 * @param column 列
	 * @param operator 运算符
	 * @return this
	 * @see Condition#Condition(String, String)
	 * @since 1.2.0
	 */
	public SqlModel addCondition(String column , String operator) {
		this.conditions.add(new Condition(column, operator));
		return this;
	}

	/**
	 * 添加一个条件
	 * 
	 * @param column 列
	 * @param operator 运算符
	 * @param value 值
	 * @return this
	 * @see Condition#Condition(String, String, Object)
	 * @since 1.2.0
	 */
	public SqlModel addCondition(String column , String operator , Object value) {
		this.conditions.add(new Condition(column, operator,value));
		return this;
	}
	
	/**
	 * 添加一个条件
	 * 
	 * @param column 列
	 * @param operator 运算符
	 * @param value 值
	 * @param secondValue 第二个值
	 * @return this
	 * @see Condition#Condition(String, String, Object, Object)
	 * @since 1.2.0
	 */
	public SqlModel addCondition(String column , String operator , Object value , Object secondValue) {
		this.conditions.add(new Condition(column, operator,value,secondValue));
		return this;
	}
	
	/**
	 * 添加一组条件
	 * 
	 * @param conditions 条件集合
	 * @return this
	 */
	public SqlModel addConditions(Collection<Condition> conditions) {
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
	 * 获取sqlModel对应的class
	 * 如果{@link #modelClass} != null 则返回{@link #modelClass} ，否则返回{@link #getClass()}
	 * 
	 * @return modelClass
	 */
	public Class<? extends Modelable> getModelClass() {
		return modelClass != null ? modelClass : getClass();
	}

	/**
	 * 获取model实体。如果没有设置model实体，则返回本身
	 * 
	 * @return model实体
	 * @since 1.0.5
	 */
	public Modelable getModel() {
		return model != null ? model : this;
	}

}

/**
 * 
 */
package org.yelong.core.model.resolve;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.yelong.core.model.Model;

/**
 * @author PengFei
 * @date 2019年12月27日下午7:04:50
 */
public abstract class AbstractModelAndTable implements ModelAndTable{

	private Class<?> modelClass;

	private String tableName;

	private List<FieldAndColumn> primaryKeys;

	private List<FieldAndColumn> fieldAndColumns;

	private Map<String,FieldAndColumn> fieldAndColumnMap;

	/**
	 * @param modelClass 模型类型
	 * @param tableName 映射的表名
	 * @param fieldAndColumns 所有映射的字段与列
	 */
	public AbstractModelAndTable(Class<?> modelClass, String tableName, List<FieldAndColumn> fieldAndColumns) {
		this.modelClass = modelClass;
		this.tableName = tableName;
		this.fieldAndColumns = Collections.unmodifiableList(fieldAndColumns);
		this.primaryKeys = fieldAndColumns.parallelStream().filter(FieldAndColumn::isPrimaryKey).collect(Collectors.toList());
		Map<String,FieldAndColumn> fieldAndColumnMap = new HashMap<String, FieldAndColumn>();
		fieldAndColumns.forEach(x->fieldAndColumnMap.put(x.getFieldName(), x));
		this.fieldAndColumnMap = Collections.unmodifiableMap(fieldAndColumnMap);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <M extends Model> Class<M> getModelClass() {
		return (Class<M>) modelClass;
	}

	@Override
	public String getTableName() {
		return this.tableName;
	}

	@Override
	public List<FieldAndColumn> getPrimaryKey() {
		return this.primaryKeys;
	}

	@Override
	public boolean existPrimaryKey() {
		return !this.primaryKeys.isEmpty();
	}

	@Override
	public List<FieldAndColumn> getFieldAndColumns() {
		return this.fieldAndColumns;
	}

	@Override
	public FieldAndColumn getFieldAndColumn(String fieldName) {
		return fieldAndColumnMap.get(fieldName);
	}

	@Override
	public List<Field> getFields() {
		return this.fieldAndColumns.parallelStream().map(FieldAndColumn::getField).collect(Collectors.toList());
	}

	@Override
	public List<String> getFieldNames() {
		return this.fieldAndColumns.parallelStream().map(FieldAndColumn::getFieldName).collect(Collectors.toList());
	}

	@Override
	public String toString() {
		return "modelClass:"+modelClass.getClass()+"\ntableName:"+tableName;
	}
	
}

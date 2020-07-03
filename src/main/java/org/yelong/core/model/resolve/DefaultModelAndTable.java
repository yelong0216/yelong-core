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

/**
 * 默认的模型和表实现
 * 
 * @author PengFei
 */
public class DefaultModelAndTable extends AbstractModelAndTable {

	private static final long serialVersionUID = 4106081559643247044L;

	private List<FieldAndColumn> primaryKeys;

	private List<FieldAndColumn> fieldAndColumns;

	private Map<String, FieldAndColumn> fieldAndColumnMap;

	public DefaultModelAndTable(Class<?> modelClass, String tableName) {
		super(modelClass, tableName);
	}

	public DefaultModelAndTable(Class<?> modelClass, String tableName, List<FieldAndColumn> fieldAndColumns) {
		super(modelClass, tableName);
		setFieldAndColumns(fieldAndColumns);
	}

	/**
	 * 设置字段和列
	 * 
	 * @param fieldAndColumns 字段和列的集合
	 */
	public void setFieldAndColumns(List<FieldAndColumn> fieldAndColumns) {
		this.fieldAndColumns = Collections.unmodifiableList(fieldAndColumns);
		this.primaryKeys = fieldAndColumns.parallelStream().filter(FieldAndColumn::isPrimaryKey)
				.collect(Collectors.toList());
		Map<String, FieldAndColumn> fieldAndColumnMap = new HashMap<String, FieldAndColumn>();
		fieldAndColumns.forEach(x -> fieldAndColumnMap.put(x.getFieldName(), x));
		this.fieldAndColumnMap = Collections.unmodifiableMap(fieldAndColumnMap);
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

}

/**
 * 
 */
package org.yelong.core.model.map;

import java.util.List;
import java.util.stream.Collectors;

import org.yelong.core.model.resolve.AbstractModelAndTable;
import org.yelong.core.model.resolve.FieldAndColumn;

/**
 * @author PengFei
 */
public class DefaultMapModelAndTable extends AbstractModelAndTable implements MapModelAndTable {

	private static final long serialVersionUID = -657510993050342757L;

	private final MapModelFieldAndColumnGetStrategy mapModelFieldAndColumnGetStrategy;

	public DefaultMapModelAndTable(Class<?> modelClass, String tableName,
			MapModelFieldAndColumnGetStrategy mapModelFieldAndColumnGetStrategy) {
		super(modelClass, tableName);
		this.mapModelFieldAndColumnGetStrategy = mapModelFieldAndColumnGetStrategy;
	}

	@Override
	public List<FieldAndColumn> getPrimaryKey() {
		return getFieldAndColumns().stream().filter(FieldAndColumn::isPrimaryKey).collect(Collectors.toList());
	}

	@Override
	public boolean existPrimaryKey() {
		return !getPrimaryKey().isEmpty();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<FieldAndColumn> getFieldAndColumns() {
		return (List) mapModelFieldAndColumnGetStrategy.get(this);
	}

	@Override
	public FieldAndColumn getFieldAndColumn(String fieldName) {
		return getFieldAndColumns().stream().filter(x -> {
			return fieldName.equals(x.getFieldName());
		}).findFirst().orElse(null);
	}

	@Override
	public List<String> getFieldNames() {
		return getFieldAndColumns().stream().map(FieldAndColumn::getFieldName).collect(Collectors.toList());
	}

	@Override
	public MapModelFieldAndColumnGetStrategy getMapModelFieldAndColumnGetStrategy() {
		return this.mapModelFieldAndColumnGetStrategy;
	}

}

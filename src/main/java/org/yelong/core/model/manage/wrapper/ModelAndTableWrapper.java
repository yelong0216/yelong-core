package org.yelong.core.model.manage.wrapper;

import java.util.List;

import org.yelong.core.model.Modelable;
import org.yelong.core.model.manage.FieldAndColumn;
import org.yelong.core.model.manage.FieldAndColumnType;
import org.yelong.core.model.manage.ModelAndTable;
import org.yelong.core.model.manage.exception.ModelAndTableException;
import org.yelong.core.model.sql.SelectSqlColumnMode;

/**
 * 模型表包装器
 * 
 * @since 2.0
 */
public class ModelAndTableWrapper implements ModelAndTable {

	private final ModelAndTable modelAndTable;

	public ModelAndTableWrapper(ModelAndTable modelAndTable) {
		this.modelAndTable = modelAndTable;
	}

	@Override
	public Class<? extends Modelable> getModelClass() {
		return getModelAndTable().getModelClass();
	}

	@Override
	public String getModelName() {
		return getModelAndTable().getModelName();
	}

	@Override
	public boolean isView() {
		return getModelAndTable().isView();
	}

	@Override
	public String getTableName() {
		return getModelAndTable().getTableName();
	}

	@Override
	public String getTableAlias() {
		return getModelAndTable().getTableAlias();
	}

	@Override
	public String getTableDesc() {
		return getModelAndTable().getTableDesc();
	}

	@Override
	public String getSelectSql() {
		return getModelAndTable().getSelectSql();
	}

	@Override
	public String getDeleteSql() {
		return getModelAndTable().getDeleteSql();
	}

	@Override
	public String getCountSql() {
		return getModelAndTable().getCountSql();
	}

	@Override
	public List<FieldAndColumn> getPrimaryKeys() {
		return getModelAndTable().getPrimaryKeys();
	}

	@Override
	public boolean existPrimaryKey() {
		return getModelAndTable().existPrimaryKey();
	}

	@Override
	public List<FieldAndColumn> getFieldAndColumns(FieldAndColumnType... fieldAndColumnTypes) {
		return getModelAndTable().getFieldAndColumns(fieldAndColumnTypes);
	}

	@Override
	public FieldAndColumn getFieldAndColumn(String fieldName) {
		return getModelAndTable().getFieldAndColumn(fieldName);
	}

	@Override
	public boolean equals(ModelAndTable modelAndTable) {
		return getModelAndTable().equals(modelAndTable);
	}

	@Override
	public void initPossessFieldAndColumns(List<FieldAndColumn> fieldAndColumns) throws ModelAndTableException {
		getModelAndTable().initPossessFieldAndColumns(fieldAndColumns);
	}

	@Override
	public SelectSqlColumnMode getSelectSqlColumnMode() {
		return getModelAndTable().getSelectSqlColumnMode();
	}

	@Override
	public String toString() {
		return getModelAndTable().toString();
	}

	@Override
	public boolean equals(Object obj) {
		return getModelAndTable().equals(obj);
	}

	public ModelAndTable getModelAndTable() {
		return modelAndTable;
	}

}

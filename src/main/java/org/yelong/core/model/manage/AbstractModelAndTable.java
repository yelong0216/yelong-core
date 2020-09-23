/**
 * 
 */
package org.yelong.core.model.manage;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ArrayUtils;
import org.yelong.core.model.manage.exception.ModelAndTableException;
import org.yelong.core.model.sql.SelectSqlColumnMode;

/**
 * 模型表的抽象实现
 * 
 * @since 1.0
 */
public abstract class AbstractModelAndTable implements ModelAndTable {

	private String tableName;

	private String tableAlias;

	private String tableDesc;

	private SelectSqlColumnMode selectSqlColumnMode;

	/**
	 * @since 2.1.4
	 */
	private String selectSql;

	/**
	 * @since 2.1.4
	 */
	private String deleteSql;

	/**
	 * @since 2.1.4
	 */
	private String countSql;

	/**
	 * 所有字段名称与字段列的映射
	 */
	private List<FieldAndColumn> fieldAndColumns;

	private boolean initPossessFieldAndColumns = false;

	private boolean view = false;

	@Override
	public String getTableName() {
		return this.tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public void setTableAlias(String tableAlias) {
		this.tableAlias = tableAlias;
	}

	public void setTableDesc(String tableDesc) {
		this.tableDesc = tableDesc;
	}

	@Override
	public String getTableAlias() {
		return tableAlias;
	}

	@Override
	public String getTableDesc() {
		return tableDesc;
	}

	public SelectSqlColumnMode getSelectSqlColumnMode() {
		return selectSqlColumnMode;
	}

	public void setSelectSqlColumnMode(SelectSqlColumnMode selectSqlColumnMode) {
		this.selectSqlColumnMode = selectSqlColumnMode;
	}

	@Override
	public boolean isView() {
		return view;
	}

	public void setView(boolean view) {
		this.view = view;
	}

	@Override
	public String getSelectSql() {
		return selectSql;
	}

	public void setSelectSql(String selectSql) {
		this.selectSql = selectSql;
	}

	@Override
	public String getDeleteSql() {
		return deleteSql;
	}

	public void setDeleteSql(String deleteSql) {
		this.deleteSql = deleteSql;
	}

	@Override
	public String getCountSql() {
		return countSql;
	}

	public void setCountSql(String countSql) {
		this.countSql = countSql;
	}

	@Override
	public List<FieldAndColumn> getFieldAndColumns(FieldAndColumnType... fieldAndColumnTypes) {
		if (ArrayUtils.isEmpty(fieldAndColumnTypes)) {
			return getFieldAndColumns();
		}
		return getFieldAndColumns().parallelStream()
				.filter(x -> ArrayUtils.contains(fieldAndColumnTypes, x.getFieldAndColumnType()))
				.collect(Collectors.toList());
	}

	/**
	 * 可以重写此方法来包装字段列
	 * 
	 * @return 模型表所持有的字段列
	 */
	protected List<FieldAndColumn> getFieldAndColumns() {
		return this.fieldAndColumns;
	}

	@Override
	public void initPossessFieldAndColumns(List<FieldAndColumn> fieldAndColumns) {
		if (initPossessFieldAndColumns) {
			throw new ModelAndTableException(this, "初始化方法只能被调用一次！");
		}
		fieldAndColumns.forEach(x -> x.initBelongModelAndTable(this));
		this.fieldAndColumns = Collections.unmodifiableList(fieldAndColumns);
		initPossessFieldAndColumns = true;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof ModelAndTable)) {
			return false;
		}
		return equals((ModelAndTable) obj);
	}

	@Override
	public boolean equals(ModelAndTable modelAndTable) {
		return modelAndTable.getModelClass().equals(getModelClass());
	}

	@Override
	public String toString() {
		return getModelName() + ":" + getTableName();
	}

}

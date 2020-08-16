/**
 * 
 */
package org.yelong.core.model.manage;

import org.yelong.core.model.manage.exception.FieldAndColumnException;

/**
 * 字段列的抽象实现
 * 
 * @since 1.0
 */
public abstract class AbstractFieldAndColumn implements FieldAndColumn {

	private String selectColumn;

	private Long minLength;

	private Long maxLength;

	private boolean allowBlank;

	private boolean allowNull;

	private boolean extend;

	// 是否是临时
	private boolean tran = false;

	private ExtendColumnAttribute extendColumnAttribute;

	private boolean primaryKey;

	private boolean select;

	private SelectColumnCondition selectColumnCondition;

	private String columnName;

	private String desc;

	private String jdbcType;

	private ModelAndTable modelAndTable;

	private boolean initBelongModelAndTable = false;

	public String getSelectColumn() {
		return selectColumn;
	}

	public void setSelectColumn(String selectColumn) {
		this.selectColumn = selectColumn;
	}

	public Long getMinLength() {
		return minLength;
	}

	public void setMinLength(Long minLength) {
		this.minLength = minLength;
	}

	public Long getMaxLength() {
		return maxLength;
	}

	public void setMaxLength(Long maxLength) {
		this.maxLength = maxLength;
	}

	public boolean isAllowBlank() {
		return allowBlank;
	}

	public void setAllowBlank(boolean allowBlank) {
		this.allowBlank = allowBlank;
	}

	public boolean isAllowNull() {
		return allowNull;
	}

	public void setAllowNull(boolean allowNull) {
		this.allowNull = allowNull;
	}

	public boolean isExtend() {
		return extend;
	}

	public void setExtend(boolean extend) {
		this.extend = extend;
	}

	public boolean isPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(boolean primaryKey) {
		this.primaryKey = primaryKey;
	}

	public SelectColumnCondition getSelectColumnCondition() {
		return selectColumnCondition;
	}

	public void setSelectColumnCondition(SelectColumnCondition selectColumnCondition) {
		this.selectColumnCondition = selectColumnCondition;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getJdbcType() {
		return jdbcType;
	}

	public void setJdbcType(String jdbcType) {
		this.jdbcType = jdbcType;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public boolean isSelect() {
		return select;
	}

	public void setSelect(boolean select) {
		this.select = select;
	}

	public ExtendColumnAttribute getExtendColumnAttribute() {
		if (!isExtend()) {
			return null;
		}
		return extendColumnAttribute;
	}

	public void setExtendColumnAttribute(ExtendColumnAttribute extendColumnAttribute) {
		this.extendColumnAttribute = extendColumnAttribute;
	}

	@Override
	public boolean isTransient() {
		return this.tran;
	}

	public void setTransient(boolean tran) {
		this.tran = tran;
	}

	@Override
	public ModelAndTable getModelAndTable() {
		return modelAndTable;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof FieldAndColumn)) {
			return false;
		}
		return equals((FieldAndColumn) obj);
	}

	@Override
	public boolean equals(FieldAndColumn fieldAndColumn) {
		return fieldAndColumn.getField().equals(this.getField());
	}

	@Override
	public void initBelongModelAndTable(ModelAndTable modelAndTable) {
		if (initBelongModelAndTable) {
			throw new FieldAndColumnException(this, "初始化方法仅能被调用一次");
		}
		this.modelAndTable = modelAndTable;
		this.initBelongModelAndTable = true;
	}

	@Override
	public String toString() {
		return getFieldName() + ":" + getColumn();
	}

}

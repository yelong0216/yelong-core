/**
 * 
 */
package org.yelong.core.model.resolve;

import org.yelong.core.model.exception.NotExtendColumnException;

/**
 * 抽象的字段和列
 * 
 * @author PengFei
 * @since 1.1.0
 */
public abstract class AbstractFieldAndColumn implements FieldAndColumn {

	private static final long serialVersionUID = 4684917589973267365L;

	private String selectColumn;

	private Long minLength;

	private Long maxLength;

	private boolean allowBlank;

	private boolean allowNull;

	private boolean extend;

	private ExtendColumnAttribute extendColumnAttribute;

	private boolean primaryKey;

	private boolean selectMapping;

	private SelectColumnCondition selectColumnCondition;

	private String columnName;

	private String desc;

	private String jdbcType;

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

	public boolean isSelectMapping() {
		return selectMapping;
	}

	public void setSelectMapping(boolean selectMapping) {
		this.selectMapping = selectMapping;
	}

	public ExtendColumnAttribute getExtendColumnAttribute() {
		if (!isExtend()) {
			throw new NotExtendColumnException(this);
		}
		return extendColumnAttribute;
	}

	public void setExtendColumnAttribute(ExtendColumnAttribute extendColumnAttribute) {
		this.extendColumnAttribute = extendColumnAttribute;
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

}

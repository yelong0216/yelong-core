/**
 * 
 */
package org.yelong.core.model.resolve;

import java.lang.reflect.Field;

/**
 * @author PengFei
 * @date 2019年12月29日下午7:31:34
 * @since 1.0
 */
public class DefaultFieldAndColumn extends AbstractFieldAndColumn{
	
	private String selectColumn;
	
	private Long minLength;
	
	private Long maxLength;
	
	private boolean allowBlank;
	
	private boolean allowNull;
	
	private boolean extend;
	
	private boolean primaryKey;
	
	private SelectColumnCondition selectColumnCondition;
	
	private String desc;
	
	private String jdbcType;
	
	public DefaultFieldAndColumn(Field field, String column) {
		super(field, column);
	}

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

}

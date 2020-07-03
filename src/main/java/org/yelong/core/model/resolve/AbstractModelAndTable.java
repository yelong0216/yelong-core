/**
 * 
 */
package org.yelong.core.model.resolve;

import org.yelong.core.model.Modelable;

/**
 * 抽象的模型和表
 * 
 * @author PengFei
 */
public abstract class AbstractModelAndTable implements ModelAndTable {

	private static final long serialVersionUID = -1474553367962118950L;

	private Class<?> modelClass;

	private String tableName;

	private String tableAlias;

	private String tableDesc;

	/**
	 * @param modelClass 模型类型
	 * @param tableName  映射的表名
	 */
	public AbstractModelAndTable(Class<?> modelClass, String tableName) {
		this.modelClass = modelClass;
		this.tableName = tableName;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <M extends Modelable> Class<M> getModelClass() {
		return (Class<M>) modelClass;
	}

	@Override
	public String getTableName() {
		return this.tableName;
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
		return "modelClass:" + modelClass + "\ttableName:" + tableName;
	}

}

/**
 * 
 */
package org.yelong.core.model.resolve;

import java.util.List;

/**
 * 默认的模型和表实现
 * @author PengFei
 */
public class DefaultModelAndTable extends AbstractModelAndTable{

	private String tableAlias;
	
	private String tableDesc;
	
	public DefaultModelAndTable(Class<?> modelClass, String tableName, List<FieldAndColumn> fieldAndColumns) {
		super(modelClass, tableName, fieldAndColumns);
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

}

/**
 * 
 */
package org.yelong.core.model.resolve;

import java.util.List;

/**
 * @author PengFei
 * @date 2019年12月29日下午7:23:25
 * @since 1.0
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

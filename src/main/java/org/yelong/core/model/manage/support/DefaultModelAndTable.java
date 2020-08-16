/**
 * 
 */
package org.yelong.core.model.manage.support;

import org.yelong.core.model.Modelable;
import org.yelong.core.model.manage.AbstractModelAndTable;

/**
 * 模型表的默认实现
 * 
 * @since 2.0
 */
public class DefaultModelAndTable extends AbstractModelAndTable {

	private Class<? extends Modelable> modelClass;

	/**
	 * @param modelClass 模型类型
	 * @param tableName  映射的表名
	 */
	public DefaultModelAndTable(Class<? extends Modelable> modelClass) {
		this.modelClass = modelClass;
	}

	@Override
	public Class<? extends Modelable> getModelClass() {
		return modelClass;
	}

}

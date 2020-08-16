/**
 * 
 */
package org.yelong.core.model.map.support;

import org.yelong.core.model.Modelable;
import org.yelong.core.model.manage.support.DefaultModelAndTable;
import org.yelong.core.model.map.MapModelAndTable;

/**
 * Map模型表的默认实现
 * 
 * @since 1.1
 */
public class DefaultMapModelAndTable extends DefaultModelAndTable implements MapModelAndTable {

	public DefaultMapModelAndTable(Class<? extends Modelable> modelClass) {
		super(modelClass);
	}

}

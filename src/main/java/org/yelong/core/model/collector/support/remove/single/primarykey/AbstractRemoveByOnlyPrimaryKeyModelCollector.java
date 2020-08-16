/**
 * 
 */
package org.yelong.core.model.collector.support.remove.single.primarykey;

import org.yelong.core.model.Modelable;
import org.yelong.core.model.collector.support.remove.impl.AbstractRemoveModelCollector;
import org.yelong.core.model.manage.ModelAndTable;
import org.yelong.core.model.manage.ModelManager;
import org.yelong.core.model.service.SqlModelService;

/**
 * 抽象的根据唯一主键删除的模型收集器
 * 
 * @since 2.0
 */
public abstract class AbstractRemoveByOnlyPrimaryKeyModelCollector<M extends Modelable>
		extends AbstractRemoveModelCollector<M> implements RemoveByOnlyPrimaryKeyModelCollector<M> {

	public AbstractRemoveByOnlyPrimaryKeyModelCollector(Class<M> modelClass) {
		super(modelClass);
	}

	/**
	 * 获取模型的唯一主键列的列名称
	 * 
	 * @param modelService modelService
	 * @return 模型的唯一主键列名称
	 */
	protected String getOnlyPrimaryKeyColumn(SqlModelService modelService) {
		ModelManager modelManager = modelService.getModelConfiguration().getModelManager();
		ModelAndTable modelAndTable = modelManager.getModelAndTable(modelClass);
		return modelAndTable.getOnlyPrimaryKey().getColumn();
	}

}

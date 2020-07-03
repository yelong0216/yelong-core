/**
 * 
 */
package org.yelong.core.model.collector.support.remove.single.condition;

import org.yelong.core.jdbc.sql.condition.single.SingleConditionSqlFragment;
import org.yelong.core.jdbc.sql.condition.support.Condition;
import org.yelong.core.model.Modelable;
import org.yelong.core.model.collector.support.ProcessConfig;
import org.yelong.core.model.service.SqlModelService;

/**
 * 根据单条件删除模型收集器实现
 * 
 * @author PengFei
 *
 * @param <M> model type
 * @since 1.3.0
 */
public class RemoveBySingleConditionModelCollectorImpl<M extends Modelable>
		extends AbstractRemoveBySingleConditionModelCollector<M> {

	private final Condition condition;

	public RemoveBySingleConditionModelCollectorImpl(Class<M> modelClass, final Condition condition) {
		super(modelClass);
		this.condition = condition;
	}

	@Override
	protected Integer doCollect(SqlModelService modelService) {
		ProcessConfig processConfig = new ProcessConfig(modelService, modelClass);
		if (null != preProcess) {
			preProcess.process(processConfig, condition);
			;
		}
		Integer removeNum;
		if (null != removeModelExecutor) {
			removeNum = removeModelExecutor.remove(processConfig, condition);
		} else {
			SingleConditionSqlFragment conditionSqlFragment = modelService.getModelConfiguration()
					.getConditionResolver().resolve(condition);
			removeNum = modelService.removeByCondition(modelClass, conditionSqlFragment);
		}
		if (null != postProcess) {
			postProcess.process(processConfig, condition);
		}
		return removeNum;
	}

}

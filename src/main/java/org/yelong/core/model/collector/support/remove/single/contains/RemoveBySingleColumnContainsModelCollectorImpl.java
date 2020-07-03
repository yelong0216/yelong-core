/**
 * 
 */
package org.yelong.core.model.collector.support.remove.single.contains;

import org.yelong.core.jdbc.sql.condition.single.SingleConditionSqlFragment;
import org.yelong.core.jdbc.sql.condition.support.Condition;
import org.yelong.core.model.Modelable;
import org.yelong.core.model.collector.support.ProcessConfig;
import org.yelong.core.model.service.SqlModelService;

/**
 * 根据单列包含某些值进行删除的模型收集器的默认实现
 * 
 * @author PengFei
 *
 * @param <M> model type
 * @since 1.3.0
 */
public class RemoveBySingleColumnContainsModelCollectorImpl<M extends Modelable>
		extends AbstractRemoveBySingleColumnContainsModelCollector<M> {

	private final String conditionColumn;

	private final Object[] conditionColumnValues;

	public RemoveBySingleColumnContainsModelCollectorImpl(Class<M> modelClass, String conditionColumn,
			Object[] conditionColumnValues) {
		super(modelClass);
		this.conditionColumn = conditionColumn;
		this.conditionColumnValues = conditionColumnValues;
	}

	@Override
	protected Integer doCollect(SqlModelService modelService) {
		ProcessConfig processConfig = new ProcessConfig(modelService, modelClass);
		if (null != preProcess) {
			preProcess.process(processConfig, conditionColumn, conditionColumnValues);
		}
		Integer removeNum;
		if (null != removeModelExecutor) {
			removeNum = removeModelExecutor.remove(processConfig, conditionColumn, conditionColumnValues);
		} else {
			SingleConditionSqlFragment conditionSqlFragment = modelService.getModelConfiguration()
					.getConditionResolver().resolve(new Condition(conditionColumn, "IN", conditionColumnValues));
			removeNum = modelService.removeByCondition(modelClass, conditionSqlFragment);
		}
		if (null != postProcess) {
			postProcess.process(processConfig, conditionColumn, conditionColumnValues);
		}
		return removeNum;
	}

	@Override
	public String getConditionColumn() {
		return conditionColumn;
	}

	@Override
	public Object[] getConditionColumnValues() {
		return conditionColumnValues;
	}

}

/**
 * 
 */
package org.yelong.core.model.collector.support.remove.single.condition;

import org.yelong.core.jdbc.sql.condition.support.Condition;
import org.yelong.core.model.collector.support.ProcessConfig;

/**
 * 根据条件删除模型执行器
 * 
 * @author PengFei
 * @since 1.3.0
 */
@FunctionalInterface
public interface RemoveModelBySingleConditionExecutor {

	/**
	 * 删除模型
	 * 
	 * @param processConfig 处理配置
	 * @param condition     条件
	 * @return 删除的记录数
	 */
	Integer remove(ProcessConfig processConfig, Condition condition);

}

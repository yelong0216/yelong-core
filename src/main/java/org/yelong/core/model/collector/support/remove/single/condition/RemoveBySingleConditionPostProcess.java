package org.yelong.core.model.collector.support.remove.single.condition;

import org.yelong.core.jdbc.sql.condition.support.Condition;
import org.yelong.core.model.collector.support.ProcessConfig;

/**
 * 根据单条件删除的后置处理
 * 
 * @since 1.3
 */
@FunctionalInterface
public interface RemoveBySingleConditionPostProcess {

	/**
	 * 后置处理操作
	 * 
	 * @param processConfig 处理配置
	 * @param condition     条件
	 */
	void process(ProcessConfig processConfig, Condition condition);

}

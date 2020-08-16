/**
 * 
 */
package org.yelong.core.model.collector.support.remove.single.contains;

import org.yelong.core.model.collector.support.ProcessConfig;

/**
 * 根据单列包含某些值进行删除的执行器
 * 
 * @since 1.3
 */
@FunctionalInterface
public interface RemoveModelBySingleColumnContainsExecutor {

	/**
	 * 执行删除
	 * 
	 * @param processConfig         处理配置
	 * @param conditionColumn       条件列
	 * @param conditionColumnValues 条件值数组
	 * @return 删除的记录数
	 */
	Integer remove(ProcessConfig processConfig, String conditionColumn, Object[] conditionColumnValues);

}

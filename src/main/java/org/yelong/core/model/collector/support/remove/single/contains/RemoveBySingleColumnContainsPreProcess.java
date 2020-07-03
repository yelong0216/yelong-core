package org.yelong.core.model.collector.support.remove.single.contains;

import org.yelong.core.model.collector.support.ProcessConfig;

/**
 * 根据单列包含某些值进行删除的前置处理
 * 
 * @author PengFei
 * @since 1.3.0
 */
@FunctionalInterface
public interface RemoveBySingleColumnContainsPreProcess {

	/**
	 * 前置处理
	 * 
	 * @param processConfig         处理配置
	 * @param conditionColumn       条件列
	 * @param conditionColumnValues 条件值数组
	 */
	void process(ProcessConfig processConfig, String conditionColumn, Object[] conditionColumnValues);

}

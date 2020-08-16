/**
 * 
 */
package org.yelong.core.model.collector.support;

import org.yelong.core.model.Modelable;
import org.yelong.core.model.service.SqlModelService;

/**
 * 处理配置
 * 
 * 在各个处理器（拦截器）中使用
 * 
 * @since 1.3
 */
public class ProcessConfig {

	public final SqlModelService modelService;

	public final Class<? extends Modelable> modelClass;

	public ProcessConfig(SqlModelService modelService, Class<? extends Modelable> modelClass) {
		this.modelService = modelService;
		this.modelClass = modelClass;
	}

}

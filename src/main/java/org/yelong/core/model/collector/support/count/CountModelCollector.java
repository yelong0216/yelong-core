/**
 * 
 */
package org.yelong.core.model.collector.support.count;

import org.yelong.core.model.Modelable;
import org.yelong.core.model.collector.ModelCollector;

/**
 * 查询记录数模型收集器
 * 
 * @param <M> model type
 * @since 2.0
 */
public interface CountModelCollector<M extends Modelable> extends ModelCollector<M, Long, CountModelCollector<M>>{

}

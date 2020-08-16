/**
 * 
 */
package org.yelong.core.model.collector.support.get;

import org.yelong.core.model.Modelable;
import org.yelong.core.model.collector.ModelCollector;

/**
 * 获取模型收集器
 * 
 * @param <M> model type
 * @since 2.0
 */
public interface GetModelCollector<M extends Modelable> extends ModelCollector<M, M, GetModelCollector<M>> {

}

/**
 * 
 */
package org.yelong.core.model.collector.support.exist;

import org.yelong.core.model.Modelable;
import org.yelong.core.model.collector.ModelCollector;

/**
 * 查询模型是否存在
 * 
 * @param <M> model type
 * @since 2.0
 */
public interface ExistModelCollector<M extends Modelable> extends ModelCollector<M, Boolean, ExistModelCollector<M>>{

}

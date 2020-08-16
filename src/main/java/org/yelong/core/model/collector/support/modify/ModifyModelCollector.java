/**
 * 
 */
package org.yelong.core.model.collector.support.modify;

import org.yelong.core.model.Modelable;
import org.yelong.core.model.collector.ModelCollector;

/**
 * 修改模型收集器
 * 
 * @param <M> model type
 * @since 1.3
 */
public interface ModifyModelCollector<M extends Modelable> extends ModelCollector<M, Integer, ModifyModelCollector<M>> {

}

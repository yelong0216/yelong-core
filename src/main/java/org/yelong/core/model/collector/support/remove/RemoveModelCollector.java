/**
 * 
 */
package org.yelong.core.model.collector.support.remove;

import org.yelong.core.model.Modelable;
import org.yelong.core.model.collector.ModelCollector;

/**
 * 删除模型收集器
 * 
 * @author PengFei
 *
 * @param <M> model type
 * @since 1.3.0
 */
public interface RemoveModelCollector<M extends Modelable> extends ModelCollector<M, Integer, RemoveModelCollector<M>> {

}

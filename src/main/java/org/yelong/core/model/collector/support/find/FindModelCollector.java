/**
 * 
 */
package org.yelong.core.model.collector.support.find;

import java.util.List;

import org.yelong.core.model.Modelable;
import org.yelong.core.model.collector.ModelCollector;

/**
 * 查询模型收集器
 * 
 * 查询并返回模型的集合
 * 
 * @param <M> model type
 * @since 2.0
 */
public interface FindModelCollector<M extends Modelable> extends ModelCollector<M, List<M>, FindModelCollector<M>> {

}

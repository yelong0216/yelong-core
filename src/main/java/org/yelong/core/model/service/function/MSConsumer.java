/**
 * 
 */
package org.yelong.core.model.service.function;

import java.util.function.Consumer;

import org.yelong.core.model.service.SqlModelService;

/**
 * 可以在Controller层执行多条数据库操作并都携带事务
 * 
 * <pre>
 * modelService.doConsumer((s) -> {
 * 	s.collect(ModelCollectors.modifySingleColumnByOnlyPrimaryKey(User.class, "username", "彭飞", "12456"));
 * 	s.saveSelective(model);
 * });
 * </pre>
 * 
 * @since 1.3
 */
@FunctionalInterface
public interface MSConsumer extends Consumer<SqlModelService> {

}

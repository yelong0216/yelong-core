/**
 * 
 */
package org.yelong.core.model.service.function;

import org.yelong.core.model.service.SqlModelService;

/**
 * 可以在Controller层执行多条数据库操作并都携带事务
 * 
 * <pre>
 * modelService.doFunction((s) -> {
 * 	// 修改委托状态
 * 	s.collect(ModelCollectors.modifySingleColumnByOnlyPrimaryKey(User.class, "username", "彭飞", "12456"));
 * 	return s.saveSelective(model);
 * });
 * </pre>
 * 
 * @param <R> result type
 * @since 1.3
 */
public interface MSFunction<R> {

	R apply(SqlModelService modelService) throws Exception;

}

/**
 * 
 */
package org.yelong.core.model.service.function;

import org.yelong.core.model.service.SqlModelService;

/**
 * 可以在Controller层执行多条数据库操作并都携带事务
 * 
 * <pre>
 * Boolean saveResult = modelService.doSupplier(() -> {
 * 	// 修改委托状态
 * 	modelService.collect(ModelCollectors.modifySingleColumnByOnlyPrimaryKey(User.class, "username", "彭飞", "12456"));
 * 	return modelService.saveSelective(model);
 * });
 * </pre>
 * 
 * @param <R> return type
 * @see SqlModelService#doSupplier(MSSupplier)
 * @since 1.3
 */
@FunctionalInterface
public interface MSSupplier<R> {

	R get() throws Exception;

}

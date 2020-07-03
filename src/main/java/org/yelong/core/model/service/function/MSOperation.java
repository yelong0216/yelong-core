/**
 * 
 */
package org.yelong.core.model.service.function;

/**
 * 可以在Controller层执行多条数据库操作并都携带事务
 * 
 * <pre>
 * modelService.doOperation(() -> {
 * 	// 修改委托状态
 * 	modelService.collect(ModelCollectors.modifySingleColumnByOnlyPrimaryKey(User.class, "username", "彭飞", "12456"));
 * 	modelService.saveSelective(model);
 * });
 * </pre>
 * 
 * @author PengFei
 * @since 1.3.0
 */
@FunctionalInterface
public interface MSOperation {

	/**
	 * 执行操作
	 */
	void process();

}

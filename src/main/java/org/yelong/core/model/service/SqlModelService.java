/**
 * 
 */
package org.yelong.core.model.service;

import java.util.List;

import org.yelong.core.annotation.Nullable;
import org.yelong.core.model.ModelNullProperty;
import org.yelong.core.model.Modelable;
import org.yelong.core.model.collector.ModelCollector;
import org.yelong.core.model.collector.ModelCollectors;
import org.yelong.core.model.service.function.MSConsumer;
import org.yelong.core.model.service.function.MSFunction;
import org.yelong.core.model.service.function.MSOperation;
import org.yelong.core.model.service.function.MSSupplier;
import org.yelong.core.model.sql.SqlModel;

/**
 * SQL模型业务处理
 * 
 * @since 1.0
 */
public interface SqlModelService extends ModelService {

	// ==================================================remove==================================================

	/**
	 * 根据 sqlModel 当作条件删除 modelClass 的记录
	 * 
	 * @param modelClass 要删除的 model
	 * @param sqlModel   SqlModel取条件部分
	 * @return 删除的记录数
	 */
	Integer removeBySqlModel(Class<? extends Modelable> modelClass, SqlModel<? extends Modelable> sqlModel);

	// ==================================================modify==================================================

	/**
	 * 根据指定SQL条件修改model
	 * 
	 * @param model    被修改的model及数据
	 * @param sqlModel SqlModel取条件部分
	 * @return 修改的记录数
	 */
	Integer modifyBySqlModel(Modelable model, SqlModel<? extends Modelable> sqlModel);

	/**
	 * 可选择性的修改model<br/>
	 * 
	 * 选择性：<br/>
	 * 1、属性为null的不会进行添加<br/>
	 * 2、需要设置为null的属性应设置为{@link ModelNullProperty}中对应的属性值
	 * 
	 * @param model    被修改的model及数据
	 * @param sqlModel SqlModel取条件部分
	 * @return 修改的记录数
	 */
	Integer modifySelectiveBySqlModel(Modelable model, SqlModel<? extends Modelable> sqlModel);

	/**
	 * 根据指定的修改SQL、参数、SqlModel的条件部分进行修改数据
	 * 
	 * @param updateSql       修改SQL
	 * @param updateSqlParams 修改SQL的参数
	 * @param sqlModel        SqlModel取条件部分
	 * @return 修改的记录数
	 */
	Integer modifyBySqlModel(String updateSql, Object[] updateSqlParams, SqlModel<? extends Modelable> sqlModel);

	// ==================================================count==================================================

	/**
	 * 根据SqlModel的条件查询model的记录数
	 * 
	 * @param modelClass 需要查询记录数的model
	 * @param sqlModel   SqlModel取条件部分
	 * @return 符合条件的记录数
	 */
	Long countBySqlModel(Class<? extends Modelable> modelClass, SqlModel<? extends Modelable> sqlModel);

	/**
	 * 根据指定的查询记录数SQL、参数、SqlModel的条件部分查询记录数
	 * 
	 * @param countSql       查询记录数SQL
	 * @param countSqlParams 查询记录数SQL参数
	 * @param sqlModel       SqlModel取条件部分
	 * @return 查询的记录数
	 */
	Long countBySqlModel(String countSql, Object[] countSqlParams, SqlModel<? extends Modelable> sqlModel);

	// ==================================================find==================================================

	/**
	 * 根据条件查询model的记录
	 * 
	 * @param <M>        model type
	 * @param modelClass 查询的model
	 * @param sqlModel   SqlModel取条件、排序部分
	 * @return 查询到的模型对象集合
	 */
	<M extends Modelable> List<M> findBySqlModel(Class<M> modelClass, SqlModel<? extends Modelable> sqlModel);

	/**
	 * 根据查询SQL、参数、SqlModel的条件部分查询模型对象
	 * 
	 * @param <M>             model type
	 * @param modelClass      model class
	 * @param selectSql       查询SQL
	 * @param selectSqlParams 查询SQL参数
	 * @param sqlModel        SqlModel取条件、排序部分
	 * @return 查询到的模型对象集合
	 */
	<M extends Modelable> List<M> findBySqlModel(Class<M> modelClass, String selectSql, Object[] selectSqlParams,
			SqlModel<? extends Modelable> sqlModel);

	/**
	 * 根据条件查询model的第一条记录
	 * 
	 * @param <M>        model type
	 * @param modelClass 查询的model
	 * @param sqlModel   SqlModel取条件、排序部分
	 * @return 符合条件的第一个model 。如果不存在则返回null
	 */
	@Nullable
	<M extends Modelable> M findFirstBySqlModel(Class<M> modelClass, SqlModel<? extends Modelable> sqlModel);

	/**
	 * 根据查询SQL、参数、SqlModel的条件部分查询第一条模型对象
	 * 
	 * @param <M>             model type
	 * @param modelClass      model class
	 * @param selectSql       查询SQL
	 * @param selectSqlParams 查询SQL参数
	 * @param sqlModel        SqlModel取条件、排序部分
	 * @return 查询到的第一个模型对象。如果不存在则返回null
	 */
	@Nullable
	<M extends Modelable> M findFirstBySqlModel(Class<M> modelClass, String selectSql, Object[] selectSqlParams,
			SqlModel<? extends Modelable> sqlModel);

	// ==================================================findSingleColumn==================================================

	/**
	 * 查询一列数据
	 * 
	 * @param <T>          result type
	 * @param modelClass   model class
	 * @param selectColumn 查询的列名
	 * @param sqlModel     SqlModel 取条件、排序部分
	 * @return 查询的列集合
	 */
	<T> List<T> findSingleColumnBySqlModel(Class<? extends Modelable> modelClass, String selectColumn,
			SqlModel<? extends Modelable> sqlModel);

	/**
	 * 查询一列的第一条数据
	 * 
	 * @param <T>          result type
	 * @param modelClass   model class
	 * @param selectColumn 查询的列名
	 * @param sqlModel     SqlModel 取条件、排序部分
	 * @return 查询的列的第一条数据
	 */
	<T> T findFirstSingleColumnBySqlModel(Class<? extends Modelable> modelClass, String selectColumn,
			SqlModel<? extends Modelable> sqlModel);

	/**
	 * 根据指定的查询SQL、参数、SqlModel的条件、排序部分查询第一列数据
	 * 
	 * @param <T>             result type
	 * @param selectSql       查询SQL
	 * @param selectSqlParams 查询SQL参数
	 * @param sqlModel        SqlModel 取条件、排序部分
	 * @return 查询的第一列数据集合
	 */
	<T> List<T> findSingleColumnBySqlModel(String selectSql, Object[] selectSqlParams,
			SqlModel<? extends Modelable> sqlModel);

	/**
	 * 根据指定的查询SQL、参数、SqlModel的条件、排序部分查询第一列第一条数据
	 * 
	 * @param <T>             result type
	 * @param selectSql       查询SQL
	 * @param selectSqlParams 查询SQL参数
	 * @param sqlModel        SqlModel 取条件、排序部分
	 * @return 查询的第一列第一条数据
	 */
	<T> T findFirstSingleColumnBySqlModel(String selectSql, Object[] selectSqlParams,
			SqlModel<? extends Modelable> sqlModel);

	// ==================================================findPage==================================================

	/**
	 * 根据SqlModel的条件、排序部分进行分页查询
	 * 
	 * @param <M>        model type
	 * @param modelClass model class
	 * @param sqlModel   SqlModel取条件、排序部分
	 * @param pageNum    页码
	 * @param pageSize   页面大小
	 * @return 模型对象集合
	 */
	<M extends Modelable> List<M> findPageBySqlModel(Class<M> modelClass, SqlModel<? extends Modelable> sqlModel,
			int pageNum, int pageSize);

	/**
	 * 根据指定的查询SQL、参数、SqlModel的条件、排序部分进行分页查询
	 * 
	 * @param <M>             model type
	 * @param modelClass      model class
	 * @param selectSql       查询SQL
	 * @param selectSqlParams 查询SQL参数
	 * @param sqlModel        SqlModel取条件、排序部分
	 * @param sqlModel        SqlModel取条件、排序部分
	 * @param pageNum         页码
	 * @param pageSize        页面大小
	 * @return 模型对象集合
	 */
	<M extends Modelable> List<M> findPageBySqlModel(Class<M> modelClass, String selectSql, Object[] selectSqlParams,
			SqlModel<? extends Modelable> sqlModel, int pageNum, int pageSize);

	// ==================================================collect==================================================

	/**
	 * 模型收集 通过不同的收集器实现不同的功能
	 * 
	 * @apiNote
	 * 
	 *          <pre>
	 * 保存
	 *  modelService.collect(ModelCollectors.save(User.class, users)
	 *			.beforeSave((x,user)->{
	 *				user.setCreator("system");
	 *			}));
	 *			
	 * 删除
	 *  modelService.collect(ModelCollectors.removeBySingleColumnEQ(User.class, "username", "夜龙"));
	 *
	 * 
	 * 复制
	 * 	String userId = "123456";
	 *	//如果复制的对象不存在则返回null
	 *	User user = modelService.collect(ModelCollectors.copySingleByOnlyPrimaryKey(User.class, userId)
	 *			.beforeSave((x,model)->{
	 *				model.setUserNo("123456");//设置某些属性
	 *			}));
	 *	Objects.requireNonNull(user,"不存在的用户："+userId);
	 * 
	 * 查询
	 *  List users = modelService.collect(ModelCollectors.findBySingleColumnEQ(User.class, "age", 18));
	 * 
	 * 获取
	 *  User user = modelService.collect(ModelCollectors.getSingleBySingleColumnEQ(User.class, "username", "夜龙"));
	 *          </pre>
	 * 
	 * @param <M>            model type
	 * @param <R>            return type
	 * @param <T>            modelCollector type
	 * @param modelCollector 模型收集器
	 * @return R return type
	 * @see ModelCollectors
	 * @since 1.3.0
	 */
	default <M extends Modelable, R, T extends ModelCollector<M, R, T>> R collect(
			ModelCollector<M, R, T> modelCollector) {
		return modelCollector.collect(this);
	}

	// ==================================================function==================================================

	/**
	 * 执行方法，并返回函数方法的返回值。
	 * 
	 * 可以在Controller层执行多条数据库操作并都携带事务
	 * 
	 * <pre>
	 * modelService.doFunction((s) -> {
	 * 	s.collect(ModelCollectors.modifySingleColumnByOnlyPrimaryKeyEQ(User.class, "username", "彭飞", "12456"));
	 * 	return s.saveSelective(model);
	 * });
	 * </pre>
	 * 
	 * @param <R>      return type
	 * @param function function
	 * @return a result
	 * @see MSFunction
	 * @since 1.3.0
	 */
	default <R> R doFunction(MSFunction<R> function) {
		return function.apply(this);
	}

	/**
	 * 执行消费者函数
	 * 
	 * 可以在Controller层执行多条数据库操作并都携带事务
	 * 
	 * <pre>
	 * modelService.doConsumer((s) -> {
	 * 	s.collect(ModelCollectors.modifySingleColumnByOnlyPrimaryKeyEQ(User.class, "username", "彭飞", "12456"));
	 * 	s.saveSelective(model);
	 * });
	 * </pre>
	 * 
	 * @param consumer consumer
	 * @see MSConsumer
	 * @since 1.3.0
	 */
	default void doConsumer(MSConsumer consumer) {
		consumer.accept(this);
	}

	/**
	 * 执行操作
	 * 
	 * 可以在Controller层执行多条数据库操作并都携带事务
	 * 
	 * <pre>
	 * modelService.doOperation(() -> {
	 * 	modelService.collect(
	 * 			ModelCollectors.modifySingleColumnByOnlyPrimaryKeyEQ(User.class, "username", "彭飞", "12456"));
	 * 	modelService.saveSelective(model);
	 * });
	 * </pre>
	 * 
	 * @param operation operation
	 * @see MSOperation
	 * @since 1.3.0
	 */
	default void doOperation(MSOperation operation) {
		operation.process();
	}

	/**
	 * 执行供应商函数
	 * 
	 * 可以在Controller层执行多条数据库操作并都携带事务
	 * 
	 * <pre>
	 * Boolean saveResult = modelService.doSupplier(() -> {
	 * 	modelService.collect(
	 * 			ModelCollectors.modifySingleColumnByOnlyPrimaryKeyEQ(User.class, "username", "彭飞", "12456"));
	 * 	return modelService.saveSelective(model);
	 * });
	 * </pre>
	 * 
	 * @param <R>      return type
	 * @param supplier supplier
	 * @return a result
	 * @throws Exception 操作异常
	 * @see MSSupplier
	 * @since 1.3.0
	 */
	default <R> R doSupplier(MSSupplier<R> supplier) {
		return supplier.get();
	}

}

/**
 * 
 */
package org.yelong.core.model.service;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
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
 * sql model service
 * 
 * @author PengFei
 */
public interface SqlModelService extends ModelService {

	// ==================================================remove==================================================

	/**
	 * 根据 sqlModel 当作条件删除 modelClass 的记录
	 * 
	 * @param <M>        model type
	 * @param <S>        sqlModel type
	 * @param modelClass 要删除的 model
	 * @param sqlModel   sql model 这只会取 sqlModel的条件部分
	 * @return 删除的记录数
	 */
	<M extends Modelable, S extends SqlModel> Integer removeBySqlModel(Class<M> modelClass, S sqlModel);

	/**
	 * 根据指定的sql和条件sql删除记录
	 * 
	 * @param <M>      model type
	 * @param <S>      sqlModel type
	 * @param sql      删除的sql语句 注意：这不应该包含where
	 * @param sqlModel sql model 这只会取 sqlModel的条件部分
	 * @return 删除的记录数
	 */
	<M extends Modelable, S extends SqlModel> Integer removeBySqlModel(String sql, S sqlModel);

	// ==================================================modify==================================================

	/**
	 * 根据指定sql条件修改model
	 * 
	 * @param <M>      model type
	 * @param <S>      sqlModel type
	 * @param model    被修改的model及数据
	 * @param sqlModel sql model 这只会取 sqlModel的条件部分
	 * @return 修改的记录数
	 */
	<M extends Modelable, S extends SqlModel> Integer modifyBySqlModel(M model, S sqlModel);

	/**
	 * 可选择性的修改model<br/>
	 * 
	 * 选择性：<br/>
	 * 1、属性为null的不会进行添加<br/>
	 * 2、需要设置为null的属性应设置为{@link ModelNullProperty}中对应的属性值
	 * 
	 * @param <M>      model type
	 * @param <S>      sqlModel type
	 * @param model    被修改的model及数据
	 * @param sqlModel sql model 这只会取 sqlModel的条件部分
	 * @return 修改的记录数
	 */
	<M extends Modelable, S extends SqlModel> Integer modifySelectiveBySqlModel(M model, S sqlModel);

	/**
	 * 根据指定的sql、参数和条件修改model
	 * 
	 * @param <M>      model type
	 * @param <S>      sqlModel type
	 * @param sql      sql语句。注意：这不应该包含where条件
	 * @param params   sql语句所需要的参数
	 * @param sqlModel 条件model
	 * @return 修改的记录数
	 */
	<M extends Modelable, S extends SqlModel> Integer modifyBySqlModel(String sql, Object[] params, S sqlModel);

	// ==================================================count==================================================

	/**
	 * 根据sql model 的条件查询model的记录数
	 * 
	 * @param <M>        model type
	 * @param <S>        sqlModel type
	 * @param modelClass 需要查询记录数的model
	 * @param sqlModel   sql model 这只会取 sqlModel的条件部分
	 * @return 符合条件的记录数
	 */
	<M extends Modelable, S extends SqlModel> Long countBySqlModel(Class<M> modelClass, S sqlModel);

	/**
	 * 根据指定的sql和条件查询model的记录数
	 * 
	 * @param <M>      model type
	 * @param <S>      sqlModel type
	 * @param sql      sql语句。注意：这不应该包含where条件
	 * @param sqlModel sql model 这只会取 sqlModel的条件部分
	 * @return 符合条件的记录数
	 */
	<M extends Modelable, S extends SqlModel> Long countBySqlModel(String sql, S sqlModel);

	// ==================================================exist==================================================

	/**
	 * 根据条件查询是否有符合该条件的记录
	 * 
	 * @param <M>        model type
	 * @param <S>        sqlModel type
	 * @param modelClass 查询的model
	 * @param sqlModel   sql model 这只会取 sqlModel的条件部分
	 * @return <tt>true</tt> 存在
	 */
	default <M extends Modelable, S extends SqlModel> boolean existBySqlModel(Class<M> modelClass, S sqlModel) {
		return countBySqlModel(modelClass, sqlModel) > 0;
	}

	/**
	 * 根据指定的sql和条件查询是否有符合该条件的记录
	 * 
	 * @param <M>      model type
	 * @param <S>      sqlModel type
	 * @param sql      sql语句。注意：这不应该包含where条件
	 * @param sqlModel sql model 这只会取 sqlModel的条件部分
	 * @return <tt>true</tt> 存在
	 */
	default <M extends Modelable, S extends SqlModel> boolean existBySqlModel(String sql, S sqlModel) {
		return countBySqlModel(sql, sqlModel) > 0;
	}

	// ==================================================find==================================================

	/**
	 * 根据条件查询model的记录
	 * 
	 * @param <M>        model type
	 * @param <S>        sqlModel type
	 * @param modelClass 查询的model
	 * @param sqlModel   sql model 这会取 sqlModel的条件和排序部分
	 * @return model list
	 */
	<M extends Modelable, S extends SqlModel> List<M> findBySqlModel(Class<M> modelClass, S sqlModel);

	/**
	 * 根据条件查询model的第一条记录
	 * 
	 * @param <M>        model type
	 * @param <S>        sqlModel type
	 * @param modelClass 查询的model
	 * @param sqlModel   sql model 这会取 sqlModel的条件和排序部分
	 * @return 符合条件的第一个model 。如果不存在则返回null
	 */
	default <M extends Modelable, S extends SqlModel> M findFirstBySqlModel(Class<M> modelClass, S sqlModel) {
		List<M> modelList = findBySqlModel(modelClass, sqlModel);
		if (CollectionUtils.isEmpty(modelList)) {
			return null;
		}
		return modelList.get(0);
	}

	/**
	 * 根据指定的sql和条件查询model的记录
	 * 
	 * @param <M>        model type
	 * @param <S>        sqlModel type
	 * @param modelClass 查询的model
	 * @param sql        sql语句。注意：这不应该包含where条件
	 * @param sqlModel   sql model 这会取 sqlModel的条件和排序部分
	 * @return model list
	 */
	<M extends Modelable, S extends SqlModel> List<M> findBySqlModel(Class<M> modelClass, String sql, S sqlModel);

	/**
	 * 根据指定的sql和条件查询model的第一条记录
	 * 
	 * @param <M>        model type
	 * @param <S>        sqlModel type
	 * @param modelClass 查询的model
	 * @param sql        sql语句。注意：这不应该包含where条件
	 * @param sqlModel   sql model 这会取 sqlModel的条件和排序部分
	 * @return model
	 */
	default <M extends Modelable, S extends SqlModel> M findFirstBySqlModel(Class<M> modelClass, String sql,
			S sqlModel) {
		List<M> modelList = findBySqlModel(modelClass, sql, sqlModel);
		if (CollectionUtils.isEmpty(modelList)) {
			return null;
		}
		return modelList.get(0);
	}

	/**
	 * 查询一列数据
	 * 
	 * @param <M>          model type
	 * @param <T>          result type
	 * @param modelClass   model class
	 * @param selectColumn 查询的列名
	 * @param condition    条件
	 * @param sort         排序
	 * @return 查询的列集合
	 */
	<M extends Modelable, S extends SqlModel, T> List<T> findSingleColumnBySqlModel(Class<M> modelClass,
			String selectColumn, S sqlModel);

	/**
	 * 查询一列的第一条数据
	 * 
	 * @param <M>          model type
	 * @param <T>          result type
	 * @param modelClass   model class
	 * @param selectColumn 查询的列名
	 * @param condition    条件
	 * @param sort         排序
	 * @return 查询的列的第一条数据
	 */
	default <M extends Modelable, S extends SqlModel, T> T findFirstSingleColumnBySqlModel(Class<M> modelClass,
			String selectColumn, S sqlModel) {
		List<T> results = findSingleColumnBySqlModel(modelClass, selectColumn, sqlModel);
		if (CollectionUtils.isEmpty(results)) {
			return null;
		} else {
			return results.get(0);
		}
	}

	// ==================================================findPage==================================================

	/**
	 * 根据指定的sql和条件分页查询model的记录
	 * 
	 * @param <M>        model type
	 * @param <S>        sqlModel type
	 * @param modelClass 查询的model
	 * @param sqlModel   sql model 这会取 sqlModel的条件和排序部分
	 * @param pageNum    页码
	 * @param pageSize   页面大小
	 * @return model list
	 */
	<M extends Modelable, S extends SqlModel> List<M> findPageBySqlModel(Class<M> modelClass, S sqlModel, int pageNum,
			int pageSize);

	/**
	 * 根据指定的sql和条件分页查询model的记录
	 * 
	 * @param <M>        model type
	 * @param <S>        sqlModel type
	 * @param modelClass 查询的model
	 * @param sql        sql语句。注意：这不应该包含where条件
	 * @param sqlModel   sql model 这会取 sqlModel的条件和排序部分
	 * @param pageNum    页码
	 * @param pageSize   页面大小
	 * @return model list
	 */
	<M extends Modelable, S extends SqlModel> List<M> findPageBySqlModel(Class<M> modelClass, String sql, S sqlModel,
			int pageNum, int pageSize);

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
	 * 	s.collect(ModelCollectors.modifySingleColumnByOnlyPrimaryKey(User.class, "username", "彭飞", "12456"));
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
	 * 	// 修改委托状态
	 * 	s.collect(ModelCollectors.modifySingleColumnByOnlyPrimaryKey(User.class, "username", "彭飞", "12456"));
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
	 * 	modelService.collect(ModelCollectors.modifySingleColumnByOnlyPrimaryKey(User.class, "username", "彭飞", "12456"));
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
	 * 	// 修改委托状态
	 * 	modelService.collect(ModelCollectors.modifySingleColumnByOnlyPrimaryKey(User.class, "username", "彭飞", "12456"));
	 * 	return modelService.saveSelective(model);
	 * });
	 * </pre>
	 * 
	 * @param <R>      return type
	 * @param supplier supplier
	 * @return a result
	 * @see MSSupplier
	 * @since 1.3.0
	 */
	default <R> R doSupplier(MSSupplier<R> supplier) {
		return supplier.get();
	}

}

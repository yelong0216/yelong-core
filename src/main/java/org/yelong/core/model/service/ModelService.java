/**
 * 
 */
package org.yelong.core.model.service;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.yelong.core.annotation.Nullable;
import org.yelong.core.jdbc.BaseDataBaseOperation;
import org.yelong.core.jdbc.sql.condition.ConditionSqlFragment;
import org.yelong.core.jdbc.sql.sort.SortSqlFragment;
import org.yelong.core.model.ModelConfiguration;
import org.yelong.core.model.ModelNullProperty;
import org.yelong.core.model.Modelable;
import org.yelong.core.model.exception.PrimaryKeyException;

/**
 * 模型业务处理
 * 
 * @author PengFei
 */
public interface ModelService extends ModelSqlFragmentExecutor {

	// ==================================================save==================================================

	/**
	 * 保存数据
	 * 
	 * 注：该model所有映射的字段均会进行保存
	 * 
	 * @param <M>   model type
	 * @param model model
	 * @return <tt>true</tt>保存成功
	 */
	<M extends Modelable> boolean save(M model);

	/**
	 * 保存数据<br/>
	 * 
	 * 选择性：<br/>
	 * 1、属性为null的不会进行添加<br/>
	 * 2、需要设置为null的属性应设置为{@link ModelNullProperty}中对应的属性值
	 * 
	 * @param <M>   model type
	 * @param model model
	 * @return <tt>true</tt>保存成功
	 */
	<M extends Modelable> boolean saveSelective(M model);

	// ==================================================remove==================================================

	/**
	 * 删除model所有的记录
	 * 
	 * @param <M>        model type
	 * @param modelClass model class
	 * @return 删除的记录数
	 */
	<M extends Modelable> Integer removeAll(Class<M> modelClass);

	/**
	 * 根据主键删除记录
	 * 
	 * 推荐使用 {@link #removeByOnlyPrimaryKey(Class, Object)}，此方法不是一个标准命名的方法。
	 * 
	 * @param <M>        model type
	 * @param modelClass model class
	 * @param id         主键值
	 * @return <tt>true</tt> 删除记录数>0
	 * @throws PrimaryKeyException modelClass存在的主键数量不等于1
	 */
	<M extends Modelable> boolean removeById(Class<M> modelClass, Object id);

	/**
	 * 根据唯一主键删除记录
	 * 
	 * @param <M>             model type
	 * @param modelClass      model class
	 * @param primaryKeyValue 主键值
	 * @return <tt>true</tt> 删除记录数>0
	 * @throws PrimaryKeyException modelClass存在的主键数量不等于1
	 */
	default <M extends Modelable> boolean removeByOnlyPrimaryKey(Class<M> modelClass, Object primaryKeyValue) {
		return removeById(modelClass, primaryKeyValue);
	}

	/**
	 * 根据主键删除多条记录
	 * 
	 * 推荐使用 {@link #removeByOnlyPrimaryKey(Class, Object[])}，此方法不是一个标准命名的方法。
	 * 
	 * @param <M>        model type
	 * @param modelClass model class
	 * @param ids        主键值数组
	 * @return 删除的记录数
	 * @throws PrimaryKeyException modelClass存在的主键数量不等于1
	 */
	<M extends Modelable> Integer removeByIds(Class<M> modelClass, Object[] ids);

	/**
	 * 根据主键删除多条记录
	 * 
	 * @param <M>              model type
	 * @param modelClass       model class
	 * @param primaryKeyValues 主键值数组
	 * @return 删除的记录数
	 * @throws PrimaryKeyException modelClass存在的主键数量不等于1
	 */
	default <M extends Modelable> Integer removeByOnlyPrimaryKey(Class<M> modelClass, Object[] primaryKeyValues) {
		return removeByIds(modelClass, primaryKeyValues);
	}

	/**
	 * 根据条件删除记录
	 * 
	 * @param <M>        model type
	 * @param modelClass model class
	 * @param condition  条件
	 * @return 删除的记录数
	 */
	<M extends Modelable> Integer removeByCondition(Class<M> modelClass, ConditionSqlFragment condition);

	// ==================================================modify==================================================

	/**
	 * 根据id进行修改数据
	 * 
	 * 推荐使用 {@link #modifyByOnlyPrimaryKey(Modelable)}，此方法不是一个标准命名的方法。<br/>
	 * 
	 * 注：该model所有映射的字段均会进行修改<br/>
	 * 
	 * @param <M>   model type
	 * @param model model
	 * @return <tt>true</tt>修改记录数大于0
	 * @throws PrimaryKeyException modelClass存在的主键数量不等于1
	 */
	<M extends Modelable> boolean modifyById(M model);

	/**
	 * 根据唯一的主键值修改数据。这个主键值从model的实例的属性中取。<br/>
	 * 
	 * 注：该model所有映射的字段均会进行修改<br/>
	 * 
	 * @param <M>   model type
	 * @param model model
	 * @return <tt>true</tt>修改记录数大于0
	 * @throws PrimaryKeyException modelClass存在的主键数量不等于1
	 */
	default <M extends Modelable> boolean modifyByOnlyPrimaryKey(M model) {
		return modifyById(model);
	}

	/**
	 * 根据id进行修改数据<br/>
	 * 
	 * 选择性：<br/>
	 * 1、属性为null的不会进行添加<br/>
	 * 2、需要设置为null的属性应设置为{@link ModelNullProperty}中对应的属性值<br/>
	 * 
	 * 推荐使用 {@link #modifySelectiveByOnlyPrimaryKey(Modelable)}，此方法不是一个标准命名的方法。
	 * 
	 * @param <M>   model type
	 * @param model 修改的模型对象
	 * @return <tt>true</tt>修改记录数大于0
	 * @throws PrimaryKeyException modelClass存在的主键数量不等于1
	 */
	<M extends Modelable> boolean modifySelectiveById(M model);

	/**
	 * 根据唯一的主键值修改数据。这个主键值从model的实例的属性中取。<br/>
	 * 
	 * 选择性：<br/>
	 * 1、属性为null的不会进行添加<br/>
	 * 2、需要设置为null的属性应设置为{@link ModelNullProperty}中对应的属性值<br/>
	 * 
	 * @param <M>   model type
	 * @param model 修改的模型对象
	 * @return <tt>true</tt>修改记录数大于0
	 * @throws PrimaryKeyException modelClass存在的主键数量不等于1
	 */
	default <M extends Modelable> boolean modifySelectiveByOnlyPrimaryKey(M model) {
		return modifySelectiveById(model);
	}

	/**
	 * 根据条件修改数据<br/>
	 * 
	 * 这不会修改id<br/>
	 * 
	 * 注：该model所有映射的字段均会进行修改<br/>
	 * 
	 * @param <M>       model type
	 * @param model     model
	 * @param condition 条件
	 * @return 修改的记录数
	 */
	<M extends Modelable> Integer modifyByCondition(M model, ConditionSqlFragment condition);

	/**
	 * 根据条件修改数据<br/>
	 * 
	 * 这不会修改主键<br/>
	 * 
	 * 选择性：<br/>
	 * 1、属性为null的不会进行添加<br/>
	 * 2、需要设置为null的属性应设置为{@link ModelNullProperty}中对应的属性值<br/>
	 * 
	 * @param <M>       model type
	 * @param model     model
	 * @param condition 条件
	 * @return 修改的记录数
	 */
	<M extends Modelable> Integer modifySelectiveByCondition(M model, ConditionSqlFragment condition);

	// ==================================================count==================================================

	/**
	 * 根据id查询记录数
	 * 
	 * @param <M>        model type
	 * @param modelClass model class
	 * @return 所有的记录数
	 */
	<M extends Modelable> Long countAll(Class<M> modelClass);

	/**
	 * 根据id查询记录数<br/>
	 * 推荐使用 {@link #countByOnlyPrimaryKey(Class, Object)}，此方法不是一个标准命名的方法。<br/>
	 * 
	 * @param <M>        model type
	 * @param modelClass model class
	 * @param id         主键值
	 * @return 符合条件的记录数
	 * @throws PrimaryKeyException modelClass存在的主键数量不等于1
	 */
	<M extends Modelable> Long countById(Class<M> modelClass, Object id);

	/**
	 * 根据唯一主键查询记录数
	 * 
	 * @param <M>             model type
	 * @param modelClass      model class
	 * @param primaryKeyValue 主键值
	 * @return 符合条件的记录数
	 * @throws PrimaryKeyException modelClass存在的主键数量不等于1
	 */
	default <M extends Modelable> Long countByOnlyPrimaryKey(Class<M> modelClass, Object primaryKeyValue) {
		return countById(modelClass, primaryKeyValue);
	}

	/**
	 * 根据多个id查询记录数<br/>
	 * 推荐使用 {@link #countByOnlyPrimaryKey(Class, Object[])}，此方法不是一个标准命名的方法。<br/>
	 * 
	 * @param <M>        model type
	 * @param modelClass model class
	 * @param ids        主键值数组
	 * @return 符合条件的记录数
	 * @throws PrimaryKeyException modelClass存在的主键数量不等于1
	 */
	<M extends Modelable> Long countByIds(Class<M> modelClass, Object[] ids);

	/**
	 * 根据多个主键值查询记录数
	 * 
	 * @param <M>              model type
	 * @param modelClass       model class
	 * @param primaryKeyValues 主键值数组
	 * @return 符合条件的记录数
	 * @throws PrimaryKeyException modelClass存在的主键数量不等于1
	 */
	default <M extends Modelable> Long countByOnlyPrimaryKey(Class<M> modelClass, Object[] primaryKeyValues) {
		return countByIds(modelClass, primaryKeyValues);
	}

	/**
	 * 根据条件查询记录数
	 * 
	 * @param <M>        model type
	 * @param modelClass model class
	 * @param condition  条件
	 * @return 符合条件的记录数
	 */
	<M extends Modelable> Long countByCondition(Class<M> modelClass, ConditionSqlFragment condition);

	// ==================================================exist==================================================

	/**
	 * 根据主键查询该记录是否存在<br/>
	 * 
	 * 推荐使用 {@link #existByOnlyPrimaryKey(Class, Object)}，此方法不是一个标准命名的方法。
	 * 
	 * @param <M>        model type
	 * @param modelClass model class
	 * @param id         主键值
	 * @return <tt>true</tt> 符合条件的记录存在
	 * @throws PrimaryKeyException modelClass存在的主键数量不等于1
	 */
	default <M extends Modelable> boolean existById(Class<M> modelClass, Object id) {
		return countById(modelClass, id) > 0;
	}

	/**
	 * 根据id查询该记录是否存在
	 * 
	 * @param <M>             model type
	 * @param modelClass      model class
	 * @param primaryKeyValue 主键值
	 * @return <tt>true</tt> 符合条件的记录存在
	 * @throws PrimaryKeyException modelClass存在的主键数量不等于1
	 */
	default <M extends Modelable> boolean existByOnlyPrimaryKey(Class<M> modelClass, Object primaryKeyValue) {
		return existById(modelClass, primaryKeyValue);
	}

	/**
	 * 根据主键数组查询这些记录是否都存在<br/>
	 * 
	 * 推荐使用 {@link #existByOnlyPrimaryKey(Class, Object[])}，此方法不是一个标准命名的方法。<br/>
	 * 
	 * @param <M>        model type
	 * @param modelClass model class
	 * @param id         主键值
	 * @return <tt>true</tt> 符合条件的记录都存在
	 * @throws PrimaryKeyException modelClass存在的主键数量不等于1
	 */
	default <M extends Modelable> boolean existByIds(Class<M> modelClass, Object[] ids) {
		return countByIds(modelClass, ids) == ids.length;
	}

	/**
	 * 根据主键数组查询这些记录是否都存在
	 * 
	 * @param <M>              model type
	 * @param modelClass       model class
	 * @param primaryKeyValues 主键值
	 * @return <tt>true</tt> 符合条件的记录都存在
	 * @throws PrimaryKeyException modelClass存在的主键数量不等于1
	 */
	default <M extends Modelable> boolean existByOnlyPrimaryKey(Class<M> modelClass, Object[] primaryKeyValues) {
		return existByIds(modelClass, primaryKeyValues);
	}

	/**
	 * 根据条件查询记录是否存在
	 * 
	 * @param <M>        model type
	 * @param modelClass model class
	 * @param condition  条件
	 * @return <tt>true</tt> 符合条件的记录存在
	 */
	default <M extends Modelable> boolean existByCondition(Class<M> modelClass, ConditionSqlFragment condition) {
		return countByCondition(modelClass, condition) > 0;
	}

	// ==================================================find==================================================

	/**
	 * 查询所有记录
	 * 
	 * @param <M>        model type
	 * @param modelClass model class
	 * @return 模型对象集合
	 */
	<M extends Modelable> List<M> findAll(Class<M> modelClass);

	/**
	 * 根据主键查询模型对象<br/>
	 * 
	 * 推荐使用 {@link #findByOnlyPrimaryKey(Class, Object)}，此方法不是一个标准命名的方法。<br/>
	 * 
	 * @param <M>        model type
	 * @param modelClass model class
	 * @param id         主键值
	 * @return 模型对象
	 * @throws PrimaryKeyException modelClass存在的主键数量不等于1
	 */
	@Nullable
	<M extends Modelable> M findById(Class<M> modelClass, Object id);

	/**
	 * 根据主键查询模型对象
	 * 
	 * @param <M>             model type
	 * @param modelClass      model class
	 * @param primaryKeyValue 主键值
	 * @return 模型对象
	 * @throws PrimaryKeyException modelClass存在的主键数量不等于1
	 */
	@Nullable
	default <M extends Modelable> M findByOnlyPrimaryKey(Class<M> modelClass, Object primaryKeyValue) {
		return findById(modelClass, primaryKeyValue);
	}

	/**
	 * 根据条件查询模型对象
	 * 
	 * @param <M>        model type
	 * @param modelClass model class
	 * @param condition  条件
	 * @return 模型对象集合
	 */
	<M extends Modelable> List<M> findByCondition(Class<M> modelClass, ConditionSqlFragment condition);

	/**
	 * 根据条件查询第一个模型对象
	 * 
	 * @param <M>        model type
	 * @param modelClass model class
	 * @param condition  条件
	 * @return 模型对象
	 */
	@Nullable
	default <M extends Modelable> M findFirstByCondition(Class<M> modelClass, ConditionSqlFragment condition) {
		List<M> modelList = findByCondition(modelClass, condition);
		if (CollectionUtils.isEmpty(modelList)) {
			return null;
		} else {
			return modelList.get(0);
		}
	}

	/**
	 * 根据条件查询模型对象
	 * 
	 * @param <M>        model type
	 * @param modelClass model class
	 * @param sort       排序
	 * @return 模型对象集合
	 */
	<M extends Modelable> List<M> findBySort(Class<M> modelClass, SortSqlFragment sort);

	/**
	 * 根据条件查询模型对象
	 * 
	 * @param <M>        model type
	 * @param modelClass model class
	 * @param condition  条件
	 * @param sort       排序条件
	 * @return 模型对象集合
	 */
	<M extends Modelable> List<M> findByConditionSort(Class<M> modelClass, ConditionSqlFragment condition,
			SortSqlFragment sort);

	/**
	 * 根据指定的sql和参数查询model
	 * 
	 * @param <M>        model type
	 * @param modelClass model class
	 * @param sql        sql
	 * @param params     参数
	 * @return 模型对象集合
	 */
	<M extends Modelable> List<M> findBySQL(Class<M> modelClass, String sql, Object[] params);

	/**
	 * 根据指定的sql和参数查询第一个记录的model
	 * 
	 * @param <M>        model type
	 * @param modelClass model class
	 * @param sql        sql
	 * @param params     参数
	 * @return 模型对象
	 */
	default <M extends Modelable> M findFirstBySQL(Class<M> modelClass, String sql, Object[] params) {
		List<M> modelList = findBySQL(modelClass, sql, params);
		if (CollectionUtils.isEmpty(modelList)) {
			return null;
		} else {
			return modelList.get(0);
		}
	}

	// ==================================================findSingleColumn==================================================

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
	<M extends Modelable, T> List<T> findSingleColumn(Class<M> modelClass, String selectColumn,
			@Nullable ConditionSqlFragment condition, @Nullable SortSqlFragment sort);

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
	<M extends Modelable, T> List<T> findSingleColumnByOnlyPrimaryKey(Class<M> modelClass, String selectColumn,
			Object primaryKeyValue);

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
	default <M extends Modelable, T> T findFirstSingleColumn(Class<M> modelClass, String selectColumn,
			@Nullable ConditionSqlFragment condition, @Nullable SortSqlFragment sort) {
		List<T> results = findSingleColumn(modelClass, selectColumn, condition, sort);
		if (CollectionUtils.isEmpty(results)) {
			return null;
		} else {
			return results.get(0);
		}
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
	default <M extends Modelable, T> T findFirstSingleColumnByOnlyPrimaryKey(Class<M> modelClass, String selectColumn,
			Object primaryKeyValue) {
		List<T> results = findSingleColumnByOnlyPrimaryKey(modelClass, selectColumn, primaryKeyValue);
		if (CollectionUtils.isEmpty(results)) {
			return null;
		} else {
			return results.get(0);
		}
	}

	// ==================================================findPage==================================================

	/**
	 * 分页查询
	 * 
	 * @param <M>        model type
	 * @param modelClass model class
	 * @param pageNum    页码
	 * @param pageSize   页面大小
	 * @return 模型对象集合
	 */
	<M extends Modelable> List<M> findPage(Class<M> modelClass, Integer pageNum, Integer pageSize);

	/**
	 * 分页条件查询
	 * 
	 * @param <M>        model type
	 * @param modelClass model class
	 * @param condition  条件
	 * @param pageNum    页码
	 * @param pageSize   页面大小
	 * @return 模型对象集合
	 */
	<M extends Modelable> List<M> findPageByCondition(Class<M> modelClass, ConditionSqlFragment condition,
			Integer pageNum, Integer pageSize);

	/**
	 * 分页排序查询
	 * 
	 * @param <M>        model type
	 * @param modelClass model class
	 * @param sort       排序
	 * @param pageNum    页码
	 * @param pageSize   页面大小
	 * @return 模型对象集合
	 */
	<M extends Modelable> List<M> findPageBySort(Class<M> modelClass, SortSqlFragment sort, Integer pageNum,
			Integer pageSize);

	/**
	 * 分页条件加排序查询
	 * 
	 * @param <M>        model type
	 * @param modelClass model class
	 * @param condition  条件
	 * @param sort       排序
	 * @param pageNum    页码
	 * @param pageSize   页面大小
	 * @return 模型对象集合
	 */
	<M extends Modelable> List<M> findPageByConditionSort(Class<M> modelClass, ConditionSqlFragment condition,
			SortSqlFragment sort, Integer pageNum, Integer pageSize);

	/**
	 * 根据指定的sql和参数分页查询model
	 * 
	 * @param <M>        model type
	 * @param modelClass model class
	 * @param sql        sql
	 * @param params     参数
	 * @param pageNum    页码
	 * @param pageSize   页面大小
	 * @return 模型对象集合
	 */
	<M extends Modelable> List<M> findPageBySQL(Class<M> modelClass, String sql, Object[] params, Integer pageNum,
			Integer pageSize);

	// ==================================================util==================================================

	/**
	 * @return model配置
	 */
	ModelConfiguration getModelConfiguration();

	/**
	 * @return 基础数据库操作对象
	 */
	BaseDataBaseOperation getBaseDataBaseOperation();

}

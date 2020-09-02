/**
 * 
 */
package org.yelong.core.model.service;

import java.util.List;

import org.yelong.core.annotation.Nullable;
import org.yelong.core.jdbc.sql.condition.ConditionSqlFragment;
import org.yelong.core.jdbc.sql.group.GroupSqlFragment;
import org.yelong.core.jdbc.sql.sort.SortSqlFragment;
import org.yelong.core.model.ModelConfiguration;
import org.yelong.core.model.ModelNullProperty;
import org.yelong.core.model.Modelable;

/**
 * 模型业务处理
 * 
 * @since 1.0
 */
public interface ModelService extends ModelSqlFragmentExecutor {

	// ==================================================save==================================================

	/**
	 * 保存模型记录，模型中所有映射的字段列作为一条记录插入模型映射的表中
	 * 
	 * @param model 保存的模型对象
	 * @return <code>true</code>保存成功
	 */
	boolean save(Modelable model);

	/**
	 * 保存模型记录，将模型中所有映射的字段列进行“选择性”筛选后作为一条记录插入模型映射的表中 <br/>
	 * 
	 * 选择性：<br/>
	 * 1、属性为null的不会进行添加<br/>
	 * 2、需要设置为null的属性应设置为{@link ModelNullProperty}中对应的属性值
	 * 
	 * @param model 保存的模型对象
	 * @return <tt>true</tt>保存成功
	 * @see ModelNullProperty
	 */
	boolean saveSelective(Modelable model);

	// ==================================================remove==================================================

	/**
	 * 根据条件删除记录
	 * 
	 * @param modelClass           model class
	 * @param conditionSqlFragment 条件。如果为 <code>null</code> 会删除所有记录
	 * @return 删除的记录数
	 */
	Integer removeBySqlFragment(Class<? extends Modelable> modelClass,
			@Nullable ConditionSqlFragment conditionSqlFragment);

	// ==================================================modify==================================================

	/**
	 * 根据条件修改模型映射的所有字段列数据。
	 * 
	 * @param model                修改的模型对象
	 * @param conditionSqlFragment 条件。为 <code>null</code> 时修改表中所有的数据
	 * @return 修改的记录数
	 */
	Integer modifyBySqlFragment(Modelable model, @Nullable ConditionSqlFragment conditionSqlFragment);

	/**
	 * 根据条件修改模型映射的所有字段列进行“选择性”筛选后的列数据。
	 * 
	 * 选择性：<br/>
	 * 1、属性为null的不会进行修改<br/>
	 * 2、需要设置为null的属性应设置为{@link ModelNullProperty}中对应的属性值<br/>
	 * 
	 * @param model                修改的模型对象
	 * @param conditionSqlFragment 条件。为 <code>null</code> 时修改表中所有的数据
	 * @return 修改的记录数
	 */
	Integer modifySelectiveBySqlFragment(Modelable model, @Nullable ConditionSqlFragment conditionSqlFragment);

	/**
	 * 根据指定的修改SQL、参数和条件修改数据。
	 * 
	 * @param updateSql            修改SQL
	 * @param updateSqlparams      修改SQL的参数
	 * @param conditionSqlFragment 条件。
	 * @return 修改的记录数
	 */
	Integer modifyBySqlFragment(String updateSql, @Nullable Object[] updateSqlparams,
			@Nullable ConditionSqlFragment conditionSqlFragment);

	// ==================================================count==================================================

	/**
	 * 根据条件查询记录数
	 * 
	 * @param modelClass           model class
	 * @param conditionSqlFragment 条件。为 <code>null</code> 时查询表中所有的数据
	 * @return 符合条件的记录数
	 */
	Long countBySqlFragment(Class<? extends Modelable> modelClass, @Nullable ConditionSqlFragment conditionSqlFragment);

	/**
	 * 
	 * 根据指定的查询记录数SQL、参数和条件查询记录数
	 * 
	 * @param countSql             查询记录数的SQL
	 * @param countSqlParams       查询记录数的SQL参数
	 * @param conditionSqlFragment 条件
	 * @return 查询的记录数
	 */
	Long countBySqlFragment(String countSql, Object[] countSqlParams,
			@Nullable ConditionSqlFragment conditionSqlFragment);

	// ==================================================find==================================================

	/**
	 * 根据条件、排序查询模型对象
	 * 
	 * @param <M>                  model type
	 * @param modelClass           model class
	 * @param conditionSqlFragment 条件
	 * @param sortSqlFragment      排序
	 * @return 模型对象集合
	 */
	<M extends Modelable> List<M> findBySqlFragment(Class<M> modelClass,
			@Nullable ConditionSqlFragment conditionSqlFragment, @Nullable SortSqlFragment sortSqlFragment);

	<M extends Modelable> List<M> findBySqlFragment(Class<M> modelClass,
			@Nullable ConditionSqlFragment conditionSqlFragment, @Nullable GroupSqlFragment groupSqlFragment,
			@Nullable SortSqlFragment sortSqlFragment);

	/**
	 * 根据查询SQL、参数、条件、排序查询模型对象
	 * 
	 * @param <M>                  model type
	 * @param modelClass           model class
	 * @param selectSql            查询SQL
	 * @param selectSqlParams      查询SQL的参数
	 * @param conditionSqlFragment 条件
	 * @param sortSqlFragment      排序
	 * @return 查询到的模型对象集合
	 */
	<M extends Modelable> List<M> findBySqlFragment(Class<M> modelClass, String selectSql, Object[] selectSqlParams,
			@Nullable ConditionSqlFragment conditionSqlFragment, @Nullable SortSqlFragment sortSqlFragment);

	<M extends Modelable> List<M> findBySqlFragment(Class<M> modelClass, String selectSql, Object[] selectSqlParams,
			@Nullable ConditionSqlFragment conditionSqlFragment, @Nullable GroupSqlFragment groupSqlFragment,
			@Nullable SortSqlFragment sortSqlFragment);

	/**
	 * 根据指定的SQL和参数查询model
	 * 
	 * @param <M>        model type
	 * @param modelClass model class
	 * @param selectSql  查询SQL
	 * @param params     参数
	 * @return 模型对象集合
	 */
	<M extends Modelable> List<M> findBySQL(Class<M> modelClass, String selectSql, Object[] params);

	// ==================================================findFirst==================================================

	/**
	 * 根据条件、排序查询第一个模型对象
	 * 
	 * @param <M>                  model type
	 * @param modelClass           model class
	 * @param conditionSqlFragment 条件。为 <code>null</code> 时查询表中所有的数据
	 * @param sortSqlFragment      排序
	 * @return 查询到的第一个模型对象
	 */
	@Nullable
	<M extends Modelable> M findFirstBySqlFragment(Class<M> modelClass,
			@Nullable ConditionSqlFragment conditionSqlFragment, @Nullable SortSqlFragment sortSqlFragment);

	@Nullable
	<M extends Modelable> M findFirstBySqlFragment(Class<M> modelClass,
			@Nullable ConditionSqlFragment conditionSqlFragment, @Nullable GroupSqlFragment groupSqlFragment,
			@Nullable SortSqlFragment sortSqlFragment);

	/**
	 * 根据查询SQL、参数、条件、排序查询获取第一个模型对象
	 * 
	 * @param <M>                  model type
	 * @param modelClass           model class
	 * @param selectSql            查询SQL
	 * @param selectSqlParams      查询SQL的参数
	 * @param conditionSqlFragment 条件
	 * @param sortSqlFragment      排序
	 * @return 查询到的第一个模型
	 */
	@Nullable
	<M extends Modelable> M findFirstBySqlFragment(Class<M> modelClass, String selectSql, Object[] selectSqlParams,
			@Nullable ConditionSqlFragment conditionSqlFragment, @Nullable SortSqlFragment sortSqlFragment);

	@Nullable
	<M extends Modelable> M findFirstBySqlFragment(Class<M> modelClass, String selectSql, Object[] selectSqlParams,
			@Nullable ConditionSqlFragment conditionSqlFragment, @Nullable GroupSqlFragment groupSqlFragment,
			@Nullable SortSqlFragment sortSqlFragment);

	/**
	 * 根据指定的SQL和参数查询第一个记录的model
	 * 
	 * @param <M>        model type
	 * @param modelClass model class
	 * @param selectSql  查询SQL
	 * @param params     参数
	 * @return 查询到的第一个模型对象
	 */
	<M extends Modelable> M findFirstBySQL(Class<M> modelClass, String selectSql, Object[] params);

	// ==================================================findSingleColumn==================================================

	/**
	 * 查询一列数据
	 * 
	 * @param <T>                  result type
	 * @param modelClass           model class
	 * @param selectColumn         查询的列名
	 * @param conditionSqlFragment 条件。为 <code>null</code> 时查询表中所有的数据
	 * @param sortSqlFragment      排序
	 * @return 查询的列集合
	 */
	<T> List<T> findSingleColumnBySqlFragment(Class<? extends Modelable> modelClass, String selectColumn,
			@Nullable ConditionSqlFragment conditionSqlFragment, @Nullable SortSqlFragment sortSqlFragment);

	<T> List<T> findSingleColumnBySqlFragment(Class<? extends Modelable> modelClass, String selectColumn,
			@Nullable ConditionSqlFragment conditionSqlFragment, @Nullable GroupSqlFragment groupSqlFragment,
			@Nullable SortSqlFragment sortSqlFragment);

	/**
	 * 查询一列的第一条数据
	 * 
	 * @param <T>                  result type
	 * @param modelClass           model class
	 * @param selectColumn         查询的列名
	 * @param conditionSqlFragment 条件。为 <code>null</code> 时查询表中所有的数据
	 * @param sortSqlFragment      排序
	 * @return 查询的列的第一条数据
	 */
	@Nullable
	<T> T findFirstSingleColumnBySqlFragment(Class<? extends Modelable> modelClass, String selectColumn,
			@Nullable ConditionSqlFragment conditionSqlFragment, @Nullable SortSqlFragment sortSqlFragment);

	@Nullable
	<T> T findFirstSingleColumnBySqlFragment(Class<? extends Modelable> modelClass, String selectColumn,
			@Nullable ConditionSqlFragment conditionSqlFragment, @Nullable GroupSqlFragment groupSqlFragment,
			@Nullable SortSqlFragment sortSqlFragment);

	/**
	 * 根据指定的查询SQL、参数、条件、排序查询第一列数据
	 * 
	 * @param <T>                  result type
	 * @param selectSql            查询SQL
	 * @param selectSqlParams      查询SQL参数
	 * @param conditionSqlFragment 条件
	 * @param sortSqlFragment      排序
	 * @return 查询的第一列数据集合
	 */
	<T> List<T> findSingleColumnBySqlFragment(String selectSql, Object[] selectSqlParams,
			@Nullable ConditionSqlFragment conditionSqlFragment, @Nullable SortSqlFragment sortSqlFragment);

	<T> List<T> findSingleColumnBySqlFragment(String selectSql, Object[] selectSqlParams,
			@Nullable ConditionSqlFragment conditionSqlFragment, @Nullable GroupSqlFragment groupSqlFragment,
			@Nullable SortSqlFragment sortSqlFragment);

	/**
	 * 根据指定的查询SQL、参数、条件、排序查询第一列的第一条数据
	 * 
	 * @param <T>                  result type
	 * @param selectSql            查询SQL
	 * @param selectSqlParams      查询SQL参数
	 * @param conditionSqlFragment 条件
	 * @param sortSqlFragment      排序
	 * @return 查询第一列的第一条数据
	 */
	<T> T findFirstSingleColumnBySqlFragment(String selectSql, Object[] selectSqlParams,
			@Nullable ConditionSqlFragment conditionSqlFragment, @Nullable SortSqlFragment sortSqlFragment);

	<T> T findFirstSingleColumnBySqlFragment(String selectSql, Object[] selectSqlParams,
			@Nullable ConditionSqlFragment conditionSqlFragment, @Nullable GroupSqlFragment groupSqlFragment,
			@Nullable SortSqlFragment sortSqlFragment);

	// ==================================================findPage==================================================

	/**
	 * 根据条件、排序进行分页查询
	 * 
	 * @param <M>                  model type
	 * @param modelClass           model class
	 * @param conditionSqlFragment 条件
	 * @param sortSqlFragment      排序
	 * @param pageNum              页码
	 * @param pageSize             页面大小
	 * @return 模型对象集合
	 */
	<M extends Modelable> List<M> findPageBySqlFragment(Class<M> modelClass,
			@Nullable ConditionSqlFragment conditionSqlFragment, @Nullable SortSqlFragment sortSqlFragment, int pageNum,
			int pageSize);

	<M extends Modelable> List<M> findPageBySqlFragment(Class<M> modelClass,
			@Nullable ConditionSqlFragment conditionSqlFragment, @Nullable GroupSqlFragment groupSqlFragment,
			@Nullable SortSqlFragment sortSqlFragment, int pageNum, int pageSize);

	/**
	 * 根据指定的查询SQL、参数、条件、排序分页查询
	 * 
	 * @param <M>                  model type
	 * @param modelClass           model class
	 * @param selectSql            查询SQL
	 * @param selectSqlParams      查询SQL参数
	 * @param conditionSqlFragment 条件
	 * @param sortSqlFragment      排序
	 * @param pageNum              页码
	 * @param pageSize             页面大小
	 * @return 模型对象集合
	 */
	<M extends Modelable> List<M> findPageBySqlFragment(Class<M> modelClass, String selectSql, Object[] selectSqlParams,
			@Nullable ConditionSqlFragment conditionSqlFragment, @Nullable SortSqlFragment sortSqlFragment, int pageNum,
			int pageSize);

	<M extends Modelable> List<M> findPageBySqlFragment(Class<M> modelClass, String selectSql, Object[] selectSqlParams,
			@Nullable ConditionSqlFragment conditionSqlFragment, @Nullable GroupSqlFragment groupSqlFragment,
			@Nullable SortSqlFragment sortSqlFragment, int pageNum, int pageSize);

	/**
	 * 根据指定的SQL和参数分页查询model
	 * 
	 * @param <M>        model type
	 * @param modelClass model class
	 * @param selectSql  查询SQL
	 * @param params     参数
	 * @param pageNum    页码
	 * @param pageSize   页面大小
	 * @return 模型对象集合
	 */
	<M extends Modelable> List<M> findPageBySQL(Class<M> modelClass, String selectSql, Object[] params, int pageNum,
			int pageSize);

	// ==================================================util==================================================

	/**
	 * @return model配置
	 */
	ModelConfiguration getModelConfiguration();

}

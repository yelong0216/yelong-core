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
import org.yelong.core.model.Model;
import org.yelong.core.model.ModelConfiguration;
import org.yelong.core.model.ModelNullProperty;

/**
 * 模型业务处理
 * @author 彭飞
 * @date 2019年9月12日上午11:55:39
 * @version 1.2
 */
public interface ModelService extends ModelSqlFragmentExecutor{

	/**
	 * 保存数据<br/>
	 * 注：该model所有映射的字段均会进行保存
	 * @author 彭飞
	 * @date 2019年9月12日下午2:26:25
	 * @version 1.2
	 * @param <M>
	 * @param model 实体对象
	 * @return <tt>true</tt>保存成功
	 */
	<M extends Model> boolean save(M model);

	/**
	 * 保存数据
	 * 选择性：
	 * 	1、属性为null的不会进行添加
	 *  2、需要设置为null的属性应设置为{@link ModelNullProperty}中对应的属性值
	 * @author 彭飞
	 * @date 2019年10月24日上午10:36:49
	 * @version 1.2
	 * @param <M>
	 * @param model
	 * @return <tt>true</tt>保存成功
	 */
	<M extends Model> boolean saveSelective(M model);
	
	/**
	 * 根据条件删除记录数
	 * @author 彭飞
	 * @date 2019年9月12日下午2:30:00
	 * @version 1.2
	 * @param modelClass 删除记录的模型class
	 * @return 删除的记录数
	 */
	<M extends Model> Integer removeAll( Class<M> modelClass );
	
	/**
	 * 根据主键删除记录
	 * @author 彭飞
	 * @date 2019年9月12日下午2:27:42
	 * @version 1.2
	 * @param modelClass 删除记录的模型class
	 * @param id 记录id
	 * @return <tt>true</tt> 删除记录数>0
	 */
	<M extends Model> boolean removeById(Class<M> modelClass,Object id);

	/**
	 * 根据主键删除多条记录
	 * @author 彭飞
	 * @date 2019年9月12日下午2:27:42
	 * @version 1.2
	 * @param modelClass 删除记录的模型class
	 * @param ids 记录ids
	 * @return 删除记录数
	 */
	<M extends Model> Integer removeByIds(Class<M> modelClass,Object [] ids);
	
	/**
	 * 根据条件删除记录数
	 * @author 彭飞
	 * @date 2019年9月12日下午2:30:00
	 * @version 1.2
	 * @param modelClass 删除记录的模型class
	 * @param condition 条件
	 * @return 删除的记录数
	 */
	<M extends Model> Integer removeByCondition( Class<M> modelClass , ConditionSqlFragment condition);

	/**
	 * 根据id进行修改数据<br/>
	 * 注：该model所有映射的字段均会进行修改
	 * @author 彭飞
	 * @date 2019年9月12日下午2:41:33
	 * @version 1.2
	 * @param model 修改的模型对象
	 * @return <tt>true</tt>修改记录数大于0
	 */
	<M extends Model> boolean modifyById(M model);

	/**
	 * 根据id进行修改数据<br/>
	 * 选择性：
	 * 	1、属性为null的不会进行添加
	 *  2、需要设置为null的属性应设置为{@link ModelNullProperty}中对应的属性值
	 * @author 彭飞
	 * @date 2019年10月24日上午10:43:05
	 * @version 1.2
	 * @param <M>
	 * @param model
	 * @return
	 */
	<M extends Model> boolean modifySelectiveById(M model);
	
	/**
	 * 根据条件修改数据<br/>
	 * 这不会修改id<br/>
	 * 注：该model所有映射的字段均会进行修改
	 * @author 彭飞
	 * @date 2019年9月12日下午2:44:14
	 * @version 1.2
	 * @param model 修改的模型对象
	 * @param condition 条件
	 * @return 修改的记录数
	 */
	<M extends Model> Integer modifyByCondition(M model,ConditionSqlFragment condition);

	/**
	 * 根据条件修改数据<br/>
	 * 这不会修改id<br/>
	 * 选择性：
	 * 	1、属性为null的不会进行添加
	 *  2、需要设置为null的属性应设置为{@link ModelNullProperty}中对应的属性值
	 * @author 彭飞
	 * @date 2019年9月12日下午2:44:14
	 * @version 1.2
	 * @param model 修改的模型对象
	 * @param condition 条件
	 * @return 修改的记录数
	 */
	<M extends Model> Integer modifySelectiveByCondition(M model,ConditionSqlFragment condition);
	
	/**
	 * 根据id查询记录数
	 * @author 彭飞
	 * @date 2019年9月12日下午2:45:39
	 * @version 1.2
	 * @param modelClass 查询记录数的模型类
	 * @return 所有的记录数
	 */
	<M extends Model> Long countAll(Class<M> modelClass);
	
	/**
	 * 根据id查询记录数
	 * @author 彭飞
	 * @date 2019年9月12日下午2:45:39
	 * @version 1.2
	 * @param modelClass 查询记录数的模型类
	 * @param id 主键值
	 * @return 符合条件的记录数
	 */
	<M extends Model> Long countById(Class<M> modelClass ,Object id);
	
	/**
	 * 根据多个id查询记录数
	 * @author 彭飞
	 * @date 2019年9月12日下午2:45:39
	 * @version 1.2
	 * @param modelClass 查询记录数的模型类
	 * @param ids 主键值数组
	 * @return 符合条件的记录数
	 */
	<M extends Model> Long countByIds(Class<M> modelClass ,Object [] ids);

	/**
	 * 根据条件查询记录数
	 * @author 彭飞
	 * @date 2019年9月12日下午2:46:47
	 * @version 1.2
	 * @param modelClass 查询记录数的模型类
	 * @param condition 条件
	 * @return 符合条件的记录数
	 */
	<M extends Model> Long countByCondition(Class<M> modelClass,ConditionSqlFragment condition);

	/**
	 * 根据id查询该记录是否存在
	 * @author 彭飞
	 * @date 2019年9月12日下午2:47:14
	 * @version 1.2
	 * @param modelClass 模型类
	 * @param id 主键值
	 * @return <tt>true</tt> 符合条件的记录存在
	 */
	default <M extends Model> boolean existById(Class<M> modelClass, Object id){
		return countById(modelClass, id) > 0;
	}

	/**
	 * 根据id数组查询这些记录是否都存在
	 * @author 彭飞
	 * @date 2019年9月12日下午2:47:14
	 * @version 1.2
	 * @param modelClass 模型类
	 * @param id 主键值
	 * @return <tt>true</tt> 符合条件的记录都存在
	 */
	default <M extends Model> boolean existByIds(Class<M> modelClass, Object [] ids){
		return countByIds(modelClass, ids) == ids.length;
	}
	
	/**
	 * 根据条件查询记录是否存在
	 * @author 彭飞
	 * @date 2019年9月12日下午2:47:17
	 * @version 1.2
	 * @param modelClass 模型类
	 * @param condition 条件
	 * @return <tt>true</tt> 符合条件的记录存在
	 */
	default <M extends Model> boolean existByCondition(Class<M> modelClass, ConditionSqlFragment condition){
		return countByCondition(modelClass, condition) > 0;
	}

	/**
	 * 查询所有
	 * @author 彭飞
	 * @date 2019年9月30日下午2:20:13
	 * @version 1.2
	 * @param <M>
	 * @param modelClass
	 * @return
	 */
	<M extends Model> List<M> findAll(Class<M> modelClass);
	
	/**
	 * 根据id查询模型对象
	 * @author 彭飞
	 * @date 2019年9月12日上午10:56:35
	 * @version 1.2
	 * @param <M>
	 * @param modelClass 模型类
	 * @param id 主键值
	 * @return 模型对象
	 */
	@Nullable
	<M extends Model> M findById(Class<M> modelClass,Object id);

	/**
	 * 根据条件查询模型对象
	 * @author 彭飞
	 * @date 2019年9月12日上午10:56:35
	 * @version 1.2
	 * @param <M>
	 * @param modelClass 模型类
	 * @param condition 条件
	 * @return 模型对象
	 */
	<M extends Model> List<M> findByCondition(Class<M> modelClass , ConditionSqlFragment condition);

	/**
	 * 根据条件查询第一个模型对象
	 * @author 彭飞
	 * @date 2019年9月12日上午10:56:35
	 * @version 1.3.1
	 * @param <M>
	 * @param modelClass 模型类
	 * @param condition 条件
	 * @return 模型对象
	 */
	@Nullable
	default <M extends Model> M findFirstByCondition(Class<M> modelClass , ConditionSqlFragment condition){
		List<M> modelList = findByCondition(modelClass, condition);
		if( CollectionUtils.isEmpty(modelList) ) {
			return null;
		} else {
			return modelList.get(0);
		}
	}

	
	/**
	 * 根据条件查询模型对象
	 * @author 彭飞
	 * @date 2019年9月12日上午10:56:35
	 * @version 1.2
	 * @param <M>
	 * @param modelClass 模型类
	 * @param sort 排序
	 * @return 模型对象
	 */
	<M extends Model> List<M> findBySort(Class<M> modelClass , SortSqlFragment sort);
	
	/**
	 * 根据条件查询模型对象
	 * @author 彭飞
	 * @date 2019年9月12日上午10:56:35
	 * @version 1.2
	 * @param <M>
	 * @param modelClass 模型类
	 * @param condition 条件
	 * @param sort 排序条件
	 * @return 模型对象
	 */
	<M extends Model> List<M> findByConditionSort(Class<M> modelClass , ConditionSqlFragment condition , SortSqlFragment sort);
	
	/**
	 * 分页查询
	 * @author 彭飞
	 * @date 2019年9月30日下午2:20:34
	 * @version 1.2
	 * @param <M>
	 * @param modelClass
	 * @param pageNum 页码
	 * @param pageSize 页面大小
	 * @return
	 */
	<M extends Model> List<M> findPage(Class<M> modelClass , Integer pageNum ,Integer pageSize);
	
	/**
	 * 分页条件查询
	 * @author 彭飞
	 * @date 2019年9月30日下午2:21:01
	 * @version 1.2
	 * @param <M>
	 * @param modelClass
	 * @param condition 条件
	 * @param pageNum 页码
	 * @param pageSize 页面大小
	 * @return
	 */
	<M extends Model> List<M> findPageByCondition(Class<M> modelClass , ConditionSqlFragment condition , Integer pageNum ,Integer pageSize);
	
	/**
	 * 分页排序查询
	 * @author 彭飞
	 * @date 2019年9月30日下午2:21:01
	 * @version 1.2
	 * @param <M>
	 * @param modelClass
	 * @param sort 排序
	 * @param pageNum 页码
	 * @param pageSize 页面大小
	 * @return
	 */
	<M extends Model> List<M> findPageBySort(Class<M> modelClass , SortSqlFragment sort , Integer pageNum ,Integer pageSize);
	
	
	/**
	 * 分页条件加排序查询
	 * @author 彭飞
	 * @date 2019年9月30日下午2:21:23
	 * @version 1.2
	 * @param <M>
	 * @param modelClass
	 * @param condition 条件
	 * @param sort 排序
	 * @param pageNum 页码
	 * @param pageSize 页面大小
	 * @return
	 */
	<M extends Model> List<M> findPageByConditionSort(Class<M> modelClass , ConditionSqlFragment condition ,SortSqlFragment sort, Integer pageNum ,Integer pageSize);
	
	<M extends Model> List<M> findBySQL(Class<M> modelClass , String sql , Object [] params);
	
	default <M extends Model> M findFirstBySQL(Class<M> modelClass , String sql , Object [] params) {
		List<M> modelList = findBySQL(modelClass, sql , params);
		if( CollectionUtils.isEmpty(modelList) ) {
			return null;
		} else {
			return modelList.get(0);
		}
	}
	
	<M extends Model> List<M> findPageBySQL(Class<M> modelClass , String sql , Object [] params, Integer pageNum ,Integer pageSize);
	
	
	/**
	 * 获取model配置
	 * @author 彭飞
	 * @date 2019年10月30日下午5:23:06
	 * @version 1.2
	 * @return model配置
	 */
	ModelConfiguration getModelConfiguration();

	/**
	 * 获取数据库操作对象
	 * @date 2019年11月6日上午11:11:22
	 * @version 1.2
	 * @return 基础数据库操作对象
	 */
	BaseDataBaseOperation getBaseDataBaseOperation();
	
}

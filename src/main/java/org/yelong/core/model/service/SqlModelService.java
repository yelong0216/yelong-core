/**
 * 
 */
package org.yelong.core.model.service;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.yelong.core.model.Model;
import org.yelong.core.model.ModelNullProperty;
import org.yelong.core.model.sql.SqlModel;

/**
 * @author PengFei
 * @date 2020年3月4日下午5:42:06
 * @since 1.0
 */
public interface SqlModelService extends ModelService{

	/**
	 * 根据 sqlModel 当作条件删除 modelClass 的记录
	 * @param <M>
	 * @param <S>
	 * @param modelClass 要删除的 model
	 * @param sqlModel sql model 这只会取 sqlModel的条件部分
	 * @return 删除的记录数
	 */
	<M extends Model , S extends SqlModel> Integer removeBySqlModel(Class<M> modelClass, S sqlModel);

	/**
	 * 根据指定的sql和条件sql删除记录
	 * @param <M>
	 * @param <S>
	 * @param sql 删除的sql语句 注意：这不应该包含where
	 * @param sqlModel sql model 这只会取 sqlModel的条件部分
	 * @return 删除的记录数
	 */
	<M extends Model , S extends SqlModel> Integer removeBySqlModel(String sql, S sqlModel);

	/**
	 * 根据指定sql条件修改model
	 * @param <M>
	 * @param <S>
	 * @param model 被修改的model及数据
	 * @param sqlModel sql model 这只会取 sqlModel的条件部分
	 * @return 修改的记录数
	 */
	<M extends Model , S extends SqlModel> Integer modifyBySqlModel(M model, S sqlModel);
	
	/**
	 * 可选择性的修改model
	 * 选择性：
	 * 	1、属性为null的不会进行添加
	 *  2、需要设置为null的属性应设置为{@link ModelNullProperty}中对应的属性值
	 * @param <M>
	 * @param <S>
	 * @param model 被修改的model及数据
	 * @param sqlModel sql model 这只会取 sqlModel的条件部分
	 * @return 修改的记录数
	 */
	<M extends Model , S extends SqlModel> Integer modifySelectiveBySqlModel(M model, S sqlModel);
	
	/**
	 * 根据指定的sql、参数和条件修改model
	 * @param <M>
	 * @param <S>
	 * @param sql sql语句。注意：这不应该包含where条件
	 * @param params sql语句所需要的参数
	 * @param sqlModel 条件model
	 * @return 修改的记录数
	 */
	<M extends Model , S extends SqlModel> Integer modifyBySqlModel(String sql, Object [] params , S sqlModel);
	
	/**
	 * 根据sql model 的条件查询model的记录数
	 * @param <M>
	 * @param <S>
	 * @param modelClass 需要查询记录数的model
	 * @param sqlModel sql model 这只会取 sqlModel的条件部分
	 * @return 符合条件的记录数
	 */
	<M extends Model , S extends SqlModel> Long countBySqlModel(Class<M> modelClass, S sqlModel);
	
	/**
	 * 根据指定的sql和条件查询model的记录数
	 * @param <M>
	 * @param <S>
	 * @param sql sql语句。注意：这不应该包含where条件
	 * @param sqlModel sql model 这只会取 sqlModel的条件部分
	 * @return 符合条件的记录数
	 */
	<M extends Model , S extends SqlModel> Long countBySqlModel(String sql, S sqlModel);
	
	/**
	 * 根据条件查询是否有符合该条件的记录
	 * @param <M>
	 * @param <S>
	 * @param modelClass 查询的model
	 * @param sqlModel sql model 这只会取 sqlModel的条件部分
	 * @return <tt>true</tt> 存在
	 */
	default <M extends Model , S extends SqlModel> boolean existBySqlModel(Class<M> modelClass, S sqlModel) {
		return countBySqlModel(modelClass, sqlModel) > 0;
	}
	
	/**
	 * 根据指定的sql和条件查询是否有符合该条件的记录
	 * @param <M>
	 * @param <S>
	 * @param sql sql语句。注意：这不应该包含where条件
	 * @param sqlModel sql model 这只会取 sqlModel的条件部分
	 * @return <tt>true</tt> 存在
	 */
	default <M extends Model , S extends SqlModel> boolean existBySqlModel(String sql, S sqlModel) {
		return countBySqlModel(sql, sqlModel) > 0;
	}
	
	/**
	 * 根据条件查询model的记录
	 * @param <M>
	 * @param <S>
	 * @param modelClass 查询的model
	 * @param sqlModel sql model 这会取 sqlModel的条件和排序部分
	 * @return model list
	 */
	<M extends Model , S extends SqlModel> List<M> findBySqlModel(Class<M> modelClass, S sqlModel);
	
	/**
	 * 根据条件查询model的第一条记录
	 * @param <M>
	 * @param <S>
	 * @param modelClass 查询的model
	 * @param sqlModel sql model 这会取 sqlModel的条件和排序部分
	 * @return 符合条件的第一个model 。如果不存在则返回null
	 */
	default <M extends Model , S extends SqlModel> M findFirstBySqlModel(Class<M> modelClass, S sqlModel){
		List<M> modelList = findBySqlModel(modelClass,sqlModel);
		if(CollectionUtils.isEmpty(modelList)) {
			return null;
		}
		return modelList.get(0);
	}
	
	/**
	 * 根据指定的sql和条件查询model的记录
	 * @param <M>
	 * @param <S>
	 * @param modelClass 查询的model
	 * @param sql sql语句。注意：这不应该包含where条件
	 * @param sqlModel sql model 这会取 sqlModel的条件和排序部分
	 * @return model list
	 */
	<M extends Model , S extends SqlModel> List<M> findBySqlModel(Class<M> modelClass,String sql, S sqlModel);
	
	/**
	 * 根据指定的sql和条件查询model的第一条记录
	 * @param <M>
	 * @param <S>
	 * @param modelClass 查询的model
	 * @param sql sql语句。注意：这不应该包含where条件
	 * @param sqlModel sql model 这会取 sqlModel的条件和排序部分
	 * @return model
	 */
	default <M extends Model , S extends SqlModel> M findFirstBySqlModel(Class<M> modelClass,String sql, S sqlModel){
		List<M> modelList = findBySqlModel(modelClass,sql,sqlModel);
		if(CollectionUtils.isEmpty(modelList)) {
			return null;
		}
		return modelList.get(0);
	}
	
	/**
	 * 根据指定的sql和条件分页查询model的记录
	 * @param <M>
	 * @param <S>
	 * @param modelClass 查询的model
	 * @param sqlModel sql model 这会取 sqlModel的条件和排序部分
	 * @param pageNum 页码
	 * @param pageSize 页面大小
	 * @return model list
	 */
	<M extends Model , S extends SqlModel> List<M> findPageBySqlModel(Class<M> modelClass, S sqlModel, int pageNum ,int pageSize);
	
	/**
	 * 根据指定的sql和条件分页查询model的记录
	 * @param <M>
	 * @param <S>
	 * @param modelClass 查询的model
	 * @param sql sql语句。注意：这不应该包含where条件
	 * @param sqlModel sql model 这会取 sqlModel的条件和排序部分
	 * @param pageNum 页码
	 * @param pageSize 页面大小
	 * @return model list
	 */
	<M extends Model , S extends SqlModel> List<M> findPageBySqlModel(Class<M> modelClass , String sql, S sqlModel, int pageNum ,int pageSize);
	
}

/**
 * 
 */
package org.yelong.core.model.service;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.yelong.commons.beans.BeanUtils;
import org.yelong.core.annotation.Nullable;
import org.yelong.core.jdbc.DataBaseOperationType;
import org.yelong.core.jdbc.sql.attribute.AttributeSqlFragment;
import org.yelong.core.jdbc.sql.condition.ConditionSqlFragment;
import org.yelong.core.jdbc.sql.condition.combination.CombinationConditionSqlFragment;
import org.yelong.core.jdbc.sql.executable.AbstractSqlFragmentExecutor;
import org.yelong.core.jdbc.sql.executable.CountSqlFragment;
import org.yelong.core.jdbc.sql.executable.DeleteSqlFragment;
import org.yelong.core.jdbc.sql.executable.InsertSqlFragment;
import org.yelong.core.jdbc.sql.executable.SelectSqlFragment;
import org.yelong.core.jdbc.sql.executable.UpdateSqlFragment;
import org.yelong.core.jdbc.sql.sort.SortSqlFragment;
import org.yelong.core.model.Model;
import org.yelong.core.model.ModelConfiguration;
import org.yelong.core.model.ModelNullProperty;
import org.yelong.core.model.exception.ModelException;
import org.yelong.core.model.exception.PrimaryKeyException;
import org.yelong.core.model.resolve.FieldAndColumn;
import org.yelong.core.model.resolve.ModelAndTable;
import org.yelong.core.model.resolve.ModelAndTableManager;
import org.yelong.core.model.sql.ModelSqlFragmentFactory;

/**
 * @author 彭飞
 * @date 2019年9月12日上午11:56:10
 * @version 1.2
 */
public abstract class AbstractModelService extends AbstractSqlFragmentExecutor implements ModelService{
	
	private final ModelConfiguration modelConfiguration;
	
	private final ModelAndTableManager modelAndTableManager;
	
	private final ModelSqlFragmentFactory modelSqlFragmentFactory;
	
	public AbstractModelService(ModelConfiguration modelConfiguration) {
		this.modelConfiguration = modelConfiguration;
		this.modelAndTableManager = modelConfiguration.getModelAndTableManager();
		this.modelSqlFragmentFactory = modelConfiguration.getModelSqlFragmentFactory();
	}
	
	@Override
	public <M extends Model> boolean save(M model) {
		return save(model, false, ModelColumnValidateWay.ALL) > 0;
	}
	
	@Override
	public <M extends Model> boolean saveSelective(M model) {
		return save(model,true, ModelColumnValidateWay.ALL) > 0;
	}
	
	@Override
	public <M extends Model> boolean removeById(Class<M> modelClass, Object id) {
		CombinationConditionSqlFragment conditionFragment = getModelSqlFragmentFactory().createCombinationConditionSqlFragment();
		conditionFragment.and(getOnlyPrimaryKey(modelClass).getColumn(), "=", id);
		return remove(modelClass, conditionFragment) > 0;
	}
	
	@Override
	public <M extends Model> Integer removeByIds(Class<M> modelClass, Object[] ids) {
		CombinationConditionSqlFragment conditionFragment = getModelSqlFragmentFactory().createCombinationConditionSqlFragment();
		conditionFragment.and(getOnlyPrimaryKey(modelClass).getColumn(), "IN", Arrays.asList(ids));
		return remove(modelClass, conditionFragment);
	}
	
	@Override
	public <M extends Model> Integer removeByCondition(Class<M> modelClass, ConditionSqlFragment condition) {
		return remove(modelClass, condition);
	}
	
	@Override
	public <M extends Model> Integer removeAll(Class<M> modelClass) {
		return remove(modelClass, null);
	}
	
	@Override
	public <M extends Model> boolean modifyById(M model) {
		return modify(model, false);
	}
	
	@Override
	public <M extends Model> boolean modifySelectiveById(M model) {
		return modify(model, true);
	}
	
	@Override
	public <M extends Model> Long countAll(Class<M> modelClass) {
		return count(modelClass, null);
	}
	
	@Override
	public <M extends Model> Long countById(Class<M> modelClass, Object id) {
		CombinationConditionSqlFragment conditionFragment = getModelSqlFragmentFactory().createCombinationConditionSqlFragment();
		conditionFragment.and(getOnlyPrimaryKey(modelClass).getColumn(), "=", id);
		return count(modelClass, conditionFragment);
	}
	
	@Override
	public <M extends Model> Long countByIds(Class<M> modelClass, Object[] ids) {
		CombinationConditionSqlFragment conditionFragment = getModelSqlFragmentFactory().createCombinationConditionSqlFragment();
		conditionFragment.and(getOnlyPrimaryKey(modelClass).getColumn(), "IN", Arrays.asList(ids));
		return count(modelClass, conditionFragment);
	}
	
	@Override
	public <M extends Model> Long countByCondition(Class<M> modelClass, ConditionSqlFragment conditionFragment) {
		return count(modelClass, conditionFragment);
	}
	/**
	 * 
	 * @author 彭飞
	 * @date 2019年10月24日下午3:48:39
	 * @version 1.2
	 * @param <M>
	 * @param model
	 * @param selective 可选择性
	 * @return
	 * @throws SQLException
	 */
	protected <M extends Model> boolean modify(M model , boolean selective){
		FieldAndColumn fieldAndColumn = getOnlyPrimaryKey(model.getClass());
		Object value;
		try {
			value = getBeanProperty(model,fieldAndColumn.getFieldName());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		CombinationConditionSqlFragment conditionFragment = getModelSqlFragmentFactory().createCombinationConditionSqlFragment();
		conditionFragment.and(fieldAndColumn.getColumn(), "=", value);
		return modify(model, selective,ModelColumnValidateWay.SELECTIVE, conditionFragment) > 0;
	}
	
	@Override
	public <M extends Model> Integer modifyByCondition(M model, ConditionSqlFragment conditionFragment) {
		return modify(model,false, ModelColumnValidateWay.SELECTIVE, conditionFragment);
	}
	
	@Override
	public <M extends Model> Integer modifySelectiveByCondition(M model, ConditionSqlFragment conditionFragment) {
		return modify(model, true, ModelColumnValidateWay.SELECTIVE, conditionFragment);
	}
	
	@Override
	public <M extends Model> List<M> findAll(Class<M> modelClass) {
		return find(modelClass, null, null, null, null);
	}

	@Override
	public <M extends Model> M findById(Class<M> modelClass, Object id) {
		ModelAndTable modelAndTable = getModelAndTable(modelClass);
		String idColumn = modelAndTable.getTableAlias()+"."+getOnlyPrimaryKey(modelClass).getColumn();
		ConditionSqlFragment conditionFragment = getModelSqlFragmentFactory().createConditionSqlFragment(idColumn+"=?",ArrayUtils.toArray(id));
		List<M> modelList = findByCondition(modelClass, conditionFragment);
		if( null == modelList || modelList.isEmpty()) {
			return null;
		}
		return modelList.get(0);
	}
	

	@Override
	public <M extends Model> List<M> findByCondition(Class<M> modelClass, ConditionSqlFragment condition) {
		return find(modelClass, condition, null, null, null);
	}

	@Override
	public <M extends Model> List<M> findBySort(Class<M> modelClass, SortSqlFragment sort) {
		return find(modelClass, null, sort, null, null);
	}

	@Override
	public <M extends Model> List<M> findByConditionSort(Class<M> modelClass, ConditionSqlFragment condition,
			SortSqlFragment sort) {
		return find(modelClass, condition, sort, null, null);
	}

	@Override
	public <M extends Model> List<M> findPage(Class<M> modelClass, Integer pageNum, Integer pageSize) {
		return find(modelClass, null, null, pageNum, pageSize);
	}

	@Override
	public <M extends Model> List<M> findPageBySort(Class<M> modelClass, SortSqlFragment sort, Integer pageNum,
			Integer pageSize) {
		return find(modelClass, null, sort, pageNum, pageSize);
	}

	@Override
	public <M extends Model> List<M> findPageByCondition(Class<M> modelClass, ConditionSqlFragment condition,
			Integer pageNum, Integer pageSize) {
		return find(modelClass, condition, null, pageNum, pageSize);
	}

	@Override
	public <M extends Model> List<M> findPageByConditionSort(Class<M> modelClass, ConditionSqlFragment condition,
			SortSqlFragment sort, Integer pageNum, Integer pageSize) {
		return find(modelClass, condition, sort, pageNum, pageSize);
	}
	
	@Override
	public <M extends Model> List<M> findBySQL(Class<M> modelClass, String sql, Object[] params) {
		SelectSqlFragment selectSqlFragment = getModelSqlFragmentFactory().createSelectSqlFragment(sql, params);
		return execute(modelClass, selectSqlFragment);
	}
	
	@Override
	public <M extends Model> List<M> findPageBySQL(Class<M> modelClass, String sql, Object[] params, Integer pageNum,
			Integer pageSize) {
		SelectSqlFragment selectSqlFragment = getModelSqlFragmentFactory().createSelectSqlFragment(sql, params);
		selectSqlFragment.startPage(pageNum, pageSize);
		return execute(modelClass, selectSqlFragment);
	}
	
	/**
	 * 保存
	 * @author 彭飞
	 * @date 2019年10月24日下午3:21:54
	 * @version 1.2
	 * @param <M>
	 * @param <A>
	 * @param model 保存的模块
	 * @param attributeFragment 属性sql片段
	 * @return
	 */
	protected <M extends Model> Integer save(M model, boolean selective, ModelColumnValidateWay modelColumnValidateWay){
		AttributeSqlFragment attributeSqlFragment = createAttributeFragment(model, DataBaseOperationType.INSERT, selective, modelColumnValidateWay);
		InsertSqlFragment insertSqlFragment = getModelSqlFragmentFactory().createInsertSqlFragment(model.getClass(), attributeSqlFragment);
		return execute(insertSqlFragment);
	}
	
	/**
	 * 删除
	 * @author 彭飞
	 * @date 2019年10月24日下午3:23:01
	 * @version 1.2
	 * @param <M>
	 * @param <C>
	 * @param modelClass
	 * @param conditionFragment 条件
	 * @return
	 */
	protected <M extends Model, C extends ConditionSqlFragment> Integer remove(Class<M> modelClass,@Nullable C conditionFragment) {
		DeleteSqlFragment deleteSqlFragment = getModelSqlFragmentFactory().createDeleteSqlFragment(modelClass);
		if( null != conditionFragment ) {
			deleteSqlFragment.setConditionSqlFragment(conditionFragment);
		}
		return execute(deleteSqlFragment);
	}
	
	/**
	 * 查询记录
	 * @author 彭飞
	 * @date 2019年10月24日下午3:23:01
	 * @version 1.2
	 * @param <M>
	 * @param <C>
	 * @param modelClass
	 * @param conditionFragment 条件
	 * @return
	 */
	protected <M extends Model, C extends ConditionSqlFragment> Long count(Class<M> modelClass,@Nullable C conditionFragment) {
		CountSqlFragment countSqlFragment = getModelSqlFragmentFactory().createCountSqlFragment(modelClass);
		if( null != conditionFragment ) {
			countSqlFragment.setConditionSqlFragment(conditionFragment);
		}
		return execute(countSqlFragment);
	}
	
	/**
	 * 修改
	 * @author 彭飞
	 * @date 2019年10月24日下午3:25:05
	 * @version 1.2
	 * @param <M>
	 * @param <C>
	 * @param model
	 * @param attributeFragment 属性
	 * @param condintionFragment 条件
	 * @return
	 */
	protected <M extends Model, C extends ConditionSqlFragment> Integer modify(M model, boolean selective,
			ModelColumnValidateWay modelColumnValidateWay,@Nullable C conditionFragment) {
		AttributeSqlFragment attributeSqlFragment = createAttributeFragment(model, DataBaseOperationType.UPDATE, selective, modelColumnValidateWay);
		//不修改id
		attributeSqlFragment.removeAttr(getOnlyPrimaryKey(model.getClass()).getColumn());
		UpdateSqlFragment updateSqlFragment = getModelSqlFragmentFactory().createUpdateSqlFragment(model.getClass(), attributeSqlFragment);
		if( null != conditionFragment ) {
			updateSqlFragment.setConditionSqlFragment(conditionFragment);
		}
		return execute(updateSqlFragment);
	}
	
	/**
	 * 
	 * @author 彭飞
	 * @date 2019年11月1日上午11:47:45
	 * @version 1.2
	 * @param <M>
	 * @param modelClass
	 * @param conditionFragment 条件
	 * @param sortFragment 排序
	 * @param pageNum 记录数量
	 * @param pageSize 页码
	 * @return
	 * @throws SQLException
	 */
	protected <M extends Model> List<M> find(Class<M> modelClass,@Nullable ConditionSqlFragment conditionFragment,
			@Nullable SortSqlFragment sortFragment, @Nullable Integer pageNum,@Nullable Integer pageSize){
		SelectSqlFragment selectSqlFragment = getModelSqlFragmentFactory().createSelectSqlFragment(modelClass);
		if( null != conditionFragment ) {
			selectSqlFragment.setConditionSqlFragment(conditionFragment);
		}
		if( null != sortFragment ) {
			selectSqlFragment.setSortSqlFragment(sortFragment);
		}
		if( null != pageNum && null != pageSize ) {
			selectSqlFragment.startPage(pageNum, pageSize);
		}
		return execute(modelClass,selectSqlFragment);
	}
	
	/**
	 * 创建属性sql
	 * 如果是可选择性的，则根据{@link ModelNullProperty}来判断是属性是否保存或修改为null
	 * @author 彭飞
	 * @date 2019年10月24日上午11:06:13
	 * @version 1.2
	 * @param <M> model
	 * @param <A> attributeFragment
	 * @param model 
	 * @param dataBaseOperationType sql类型
	 * @param selective 可选择性：为空则不进行修改或者保存
	 * @param modelColumnValidateWay model列验证方式
	 * @param attributeFragmentFactory 属性sql片段工厂
	 * @return
	 */
	protected <M extends Model> AttributeSqlFragment createAttributeFragment(M model ,
			 DataBaseOperationType dataBaseOperationType ,boolean selective , ModelColumnValidateWay modelColumnValidateWay) {
		AttributeSqlFragment attributeSqlFragment = getModelSqlFragmentFactory().createAttributeSqlFragment();
		attributeSqlFragment.setDataBaseOperationType(dataBaseOperationType);
		ModelAndTable modelAndTable = getModelAndTable(model.getClass());
		List<FieldAndColumn> fieldAndColumns = modelAndTable.getFieldAndColumns();
		for (FieldAndColumn fieldAndColumn : fieldAndColumns) {
			try {
				Object value = getBeanProperty(model, fieldAndColumn.getFieldName());
				boolean valueIsNull = null == value;
				if( (modelColumnValidateWay == ModelColumnValidateWay.ALL)
						|| (modelColumnValidateWay == ModelColumnValidateWay.SELECTIVE && !valueIsNull )) {
					validFieldAndColumn(fieldAndColumn, value);
				}
				//如果不是可选择性，或者可选择性且value不为null
				if( (!selective) || ( selective && ! valueIsNull ) ) {
					//只有是可选择性时才判断是否存在伪装空值
					if( selective )
						//判断是否是伪装空值
						value = ModelNullProperty.isPretendNull(value) ? null : value;
					attributeSqlFragment.addAttr(fieldAndColumn.getColumn(), value);
				}
			} catch (Exception e) {

			}
		}
		return attributeSqlFragment;
	}

	/**
	 * 验证列属性。
	 * 验证是否非空、字段长度是否符合
	 * @author 彭飞
	 * @date 2019年9月29日下午1:54:16
	 * @version 1.2
	 * @param modelFieldColumn
	 * @param value
	 * @throws ModelException
	 */
	protected void validFieldAndColumn( FieldAndColumn fieldAndColumn , Object value ) throws ModelException{
		if( null == value ) {
			if( !fieldAndColumn.isAllowNull() ) {
				throw new ModelException("列:"+fieldAndColumn.getColumn()+"不支持为null");
			} else {
				return;
			}
		} else {
			Class<?> fieldType = fieldAndColumn.getFieldType();
			if( CharSequence.class.isAssignableFrom(fieldType) ) {
				if( !fieldAndColumn.isAllowBlank() ) {
					if(StringUtils.isBlank((CharSequence)value)){
						throw new ModelException("列:"+fieldAndColumn.getColumn()+"不支持为空白字符");
					}
				}
			}
		}

		long minLength = fieldAndColumn.getMinLength();
		long maxLength = fieldAndColumn.getMaxLength();
		int valueLength = 0;
		if( value instanceof Number ) {
			valueLength = (value+"").length();
		} else if( value instanceof CharSequence) {
			valueLength = ((CharSequence)value).length();
		} else {
			valueLength = value.toString().length();
		}
		if( valueLength < minLength ) {
			throw new ModelException("列\""+fieldAndColumn.getColumn()+"\"的值("+value+")太小(实际值: "+valueLength+", 最小值: "+minLength+")");
		} else if( valueLength > maxLength ){
			throw new ModelException("列\""+fieldAndColumn.getColumn()+"\"的值("+value+")太大 (实际值: "+valueLength+", 最大值: "+maxLength+")");
		}
	}


	@Override
	public ModelConfiguration getModelConfiguration() {
		return modelConfiguration;
	}

	protected <M extends Model> ModelAndTable getModelAndTable(M model) {
		return getModelAndTable(model.getClass());
	}
	
	protected <M extends Model> ModelAndTable getModelAndTable(Class<M> modelClass) {
		return modelAndTableManager.getModelAndTable(modelClass);
	}

	public ModelSqlFragmentFactory getModelSqlFragmentFactory() {
		return modelSqlFragmentFactory;
	}
	
	/**
	 * 
	 * 获取对象属性值
	 * @param bean
	 * @param fieldName
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 * @throws IntrospectionException 
	 */
	protected Object getBeanProperty(Object bean , String fieldName) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException, IntrospectionException{
		return BeanUtils.getProperty(bean, fieldName);
	}
	
	/**
	 * 获取唯一的主键列
	 * @param <M>
	 * @param modelClass
	 * @return
	 */
	protected <M extends Model> FieldAndColumn getOnlyPrimaryKey(Class<M> modelClass) throws PrimaryKeyException{
		ModelAndTable modelAndTable = getModelAndTable(modelClass);
		List<FieldAndColumn> primaryKeys = modelAndTable.getPrimaryKey();
		if( primaryKeys.isEmpty() ) {
			throw new PrimaryKeyException("在获取唯一主键时出现错误：["+modelClass.getName()+"]不存在主键列!");
		} else if( primaryKeys.size() > 1 ) {
			throw new PrimaryKeyException("在获取唯一主键时出现错误：["+modelClass.getName()+"]存在"+primaryKeys.size()+"主键列("+primaryKeys.stream().map(FieldAndColumn::getColumn).toString()+")!");
		} else {
			return primaryKeys.get(0);
		}
	}
	
	/**
	 * model列验证方式
	 * @author 彭飞
	 * @date 2019年10月24日上午11:17:44
	 * @version 1.2
	 */
	public enum ModelColumnValidateWay {
		/**
		 * 所有列验证
		 */
		ALL,
		/**
		 * 不验证
		 */
		NO,
		/**
		 * 选择性的，只有被修改或者新增的字段进行验证
		 */
		SELECTIVE
	}

}

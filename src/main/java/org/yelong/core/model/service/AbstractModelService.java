/**
 * 
 */
package org.yelong.core.model.service;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.yelong.core.annotation.Nullable;
import org.yelong.core.jdbc.DataBaseOperationType;
import org.yelong.core.jdbc.sql.BoundSql;
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
import org.yelong.core.model.ModelConfiguration;
import org.yelong.core.model.ModelNullProperty;
import org.yelong.core.model.Modelable;
import org.yelong.core.model.exception.ModelException;
import org.yelong.core.model.exception.PrimaryKeyException;
import org.yelong.core.model.property.ModelProperty;
import org.yelong.core.model.resolve.FieldAndColumn;
import org.yelong.core.model.resolve.ModelAndTable;
import org.yelong.core.model.resolve.ModelAndTableManager;
import org.yelong.core.model.sql.ModelSqlFragmentFactory;

/**
 * 抽象的modelService实现
 * 
 * @author PengFei
 */
public abstract class AbstractModelService extends AbstractSqlFragmentExecutor implements ModelService {

	private final ModelConfiguration modelConfiguration;

	private final ModelAndTableManager modelAndTableManager;

	private final ModelSqlFragmentFactory modelSqlFragmentFactory;

	public AbstractModelService(ModelConfiguration modelConfiguration) {
		this.modelConfiguration = modelConfiguration;
		this.modelAndTableManager = modelConfiguration.getModelAndTableManager();
		this.modelSqlFragmentFactory = modelConfiguration.getModelSqlFragmentFactory();
	}

	@Override
	public <M extends Modelable> boolean save(M model) {
		return save(model, false, ModelColumnValidateWay.ALL) > 0;
	}

	@Override
	public <M extends Modelable> boolean saveSelective(M model) {
		return save(model, true, ModelColumnValidateWay.ALL) > 0;
	}

	@Override
	public <M extends Modelable> boolean removeById(Class<M> modelClass, Object id) {
		CombinationConditionSqlFragment conditionFragment = getModelSqlFragmentFactory()
				.createCombinationConditionSqlFragment();
		conditionFragment.and(getOnlyPrimaryKey(modelClass).getColumn(), "=", id);
		return remove(modelClass, conditionFragment) > 0;
	}

	@Override
	public <M extends Modelable> Integer removeByIds(Class<M> modelClass, Object[] ids) {
		CombinationConditionSqlFragment conditionFragment = getModelSqlFragmentFactory()
				.createCombinationConditionSqlFragment();
		conditionFragment.and(getOnlyPrimaryKey(modelClass).getColumn(), "IN", Arrays.asList(ids));
		return remove(modelClass, conditionFragment);
	}

	@Override
	public <M extends Modelable> Integer removeByCondition(Class<M> modelClass, ConditionSqlFragment condition) {
		return remove(modelClass, condition);
	}

	@Override
	public <M extends Modelable> Integer removeAll(Class<M> modelClass) {
		return remove(modelClass, null);
	}

	@Override
	public <M extends Modelable> boolean modifyById(M model) {
		return modify(model, false);
	}

	@Override
	public <M extends Modelable> boolean modifySelectiveById(M model) {
		return modify(model, true);
	}

	@Override
	public <M extends Modelable> Long countAll(Class<M> modelClass) {
		return count(modelClass, null);
	}

	@Override
	public <M extends Modelable> Long countById(Class<M> modelClass, Object id) {
		CombinationConditionSqlFragment conditionFragment = getModelSqlFragmentFactory()
				.createCombinationConditionSqlFragment();
		conditionFragment.and(getOnlyPrimaryKey(modelClass).getColumn(), "=", id);
		return count(modelClass, conditionFragment);
	}

	@Override
	public <M extends Modelable> Long countByIds(Class<M> modelClass, Object[] ids) {
		CombinationConditionSqlFragment conditionFragment = getModelSqlFragmentFactory()
				.createCombinationConditionSqlFragment();
		conditionFragment.and(getOnlyPrimaryKey(modelClass).getColumn(), "IN", Arrays.asList(ids));
		return count(modelClass, conditionFragment);
	}

	@Override
	public <M extends Modelable> Long countByCondition(Class<M> modelClass, ConditionSqlFragment conditionFragment) {
		return count(modelClass, conditionFragment);
	}

	/**
	 * 
	 * @param <M>
	 * @param model
	 * @param selective 可选择性
	 * @return 修改的结果
	 */
	protected <M extends Modelable> boolean modify(M model, boolean selective) {
		FieldAndColumn fieldAndColumn = getOnlyPrimaryKey(model.getClass());
		Object value;
		try {
			value = getBeanProperty(model, fieldAndColumn.getFieldName());
			// 修改前将主键值清空，不让其修改
			setBeanProperty(model, fieldAndColumn.getFieldName(), null);
		} catch (NoSuchMethodException e) {
			throw new ModelException(e);
		}
		CombinationConditionSqlFragment conditionFragment = getModelSqlFragmentFactory()
				.createCombinationConditionSqlFragment();
		conditionFragment.and(fieldAndColumn.getColumn(), "=", value);
		boolean result = modify(model, selective, ModelColumnValidateWay.SELECTIVE, conditionFragment) > 0;
		// 修改完之后将主键值重新设置上。
		try {
			setBeanProperty(model, fieldAndColumn.getFieldName(), value);
		} catch (NoSuchMethodException e) {
			throw new ModelException(e);
		}
		return result;
	}

	@Override
	public <M extends Modelable> Integer modifyByCondition(M model, ConditionSqlFragment conditionFragment) {
		return modify(model, false, ModelColumnValidateWay.SELECTIVE, conditionFragment);
	}

	@Override
	public <M extends Modelable> Integer modifySelectiveByCondition(M model, ConditionSqlFragment conditionFragment) {
		return modify(model, true, ModelColumnValidateWay.SELECTIVE, conditionFragment);
	}

	// ==================================================find==================================================

	@Override
	public <M extends Modelable> List<M> findAll(Class<M> modelClass) {
		return find(modelClass, null, null, null, null);
	}

	@Override
	public <M extends Modelable> M findById(Class<M> modelClass, Object id) {
		ModelAndTable modelAndTable = getModelAndTable(modelClass);
		String idColumn = modelAndTable.getTableAlias() + "." + getOnlyPrimaryKey(modelClass).getColumn();
		ConditionSqlFragment conditionFragment = getModelSqlFragmentFactory()
				.createConditionSqlFragment(idColumn + "=?", ArrayUtils.toArray(id));
		List<M> modelList = findByCondition(modelClass, conditionFragment);
		if (null == modelList || modelList.isEmpty()) {
			return null;
		}
		return modelList.get(0);
	}

	@Override
	public <M extends Modelable> List<M> findByCondition(Class<M> modelClass, ConditionSqlFragment condition) {
		return find(modelClass, condition, null, null, null);
	}

	@Override
	public <M extends Modelable> List<M> findBySort(Class<M> modelClass, SortSqlFragment sort) {
		return find(modelClass, null, sort, null, null);
	}

	@Override
	public <M extends Modelable> List<M> findByConditionSort(Class<M> modelClass, ConditionSqlFragment condition,
			SortSqlFragment sort) {
		return find(modelClass, condition, sort, null, null);
	}

	@Override
	public <M extends Modelable, T> List<T> findSingleColumn(Class<M> modelClass, String selectColumn,
			ConditionSqlFragment condition, SortSqlFragment sort) {
		SelectSqlFragment selectSqlFragment = getModelSqlFragmentFactory().createSelectSqlFragmentByColumns(modelClass,
				Arrays.asList(selectColumn));
		if (null != condition) {
			selectSqlFragment.setConditionSqlFragment(condition);
		}
		if (null != sort) {
			selectSqlFragment.setSortSqlFragment(sort);
		}
		BoundSql boundSql = selectSqlFragment.getBoundSql();
		return getBaseDataBaseOperation().selectColumn(boundSql.getSql(), boundSql.getParams());
	}

	@Override
	public <M extends Modelable, T> List<T> findSingleColumnByOnlyPrimaryKey(Class<M> modelClass, String selectColumn,
			Object primaryKeyValue) {
		ModelAndTable modelAndTable = getModelAndTable(modelClass);
		String idColumn = modelAndTable.getTableAlias() + "." + getOnlyPrimaryKey(modelClass).getColumn();
		ConditionSqlFragment conditionFragment = getModelSqlFragmentFactory()
				.createConditionSqlFragment(idColumn + "=?", ArrayUtils.toArray(primaryKeyValue));
		return findSingleColumn(modelClass, selectColumn, conditionFragment, null);
	}

	// ==================================================findPage==================================================

	@Override
	public <M extends Modelable> List<M> findPage(Class<M> modelClass, Integer pageNum, Integer pageSize) {
		return find(modelClass, null, null, pageNum, pageSize);
	}

	@Override
	public <M extends Modelable> List<M> findPageBySort(Class<M> modelClass, SortSqlFragment sort, Integer pageNum,
			Integer pageSize) {
		return find(modelClass, null, sort, pageNum, pageSize);
	}

	@Override
	public <M extends Modelable> List<M> findPageByCondition(Class<M> modelClass, ConditionSqlFragment condition,
			Integer pageNum, Integer pageSize) {
		return find(modelClass, condition, null, pageNum, pageSize);
	}

	@Override
	public <M extends Modelable> List<M> findPageByConditionSort(Class<M> modelClass, ConditionSqlFragment condition,
			SortSqlFragment sort, Integer pageNum, Integer pageSize) {
		return find(modelClass, condition, sort, pageNum, pageSize);
	}

	@Override
	public <M extends Modelable> List<M> findBySQL(Class<M> modelClass, String sql, Object[] params) {
		SelectSqlFragment selectSqlFragment = getModelSqlFragmentFactory().createSelectSqlFragment(sql, params);
		return execute(modelClass, selectSqlFragment);
	}

	@Override
	public <M extends Modelable> List<M> findPageBySQL(Class<M> modelClass, String sql, Object[] params,
			Integer pageNum, Integer pageSize) {
		SelectSqlFragment selectSqlFragment = getModelSqlFragmentFactory().createSelectSqlFragment(sql, params);
		selectSqlFragment.startPage(pageNum, pageSize);
		return execute(modelClass, selectSqlFragment);
	}

	/**
	 * 保存
	 * 
	 * @param <M>                    model type
	 * @param model                  model
	 * @param selective              可选择性
	 * @param modelColumnValidateWay 验证模式
	 * @return 响应的记录数
	 */
	protected <M extends Modelable> Integer save(M model, boolean selective,
			ModelColumnValidateWay modelColumnValidateWay) {
		AttributeSqlFragment attributeSqlFragment = createAttributeFragment(model, DataBaseOperationType.INSERT,
				selective, modelColumnValidateWay);
		InsertSqlFragment insertSqlFragment = getModelSqlFragmentFactory().createInsertSqlFragment(model.getClass(),
				attributeSqlFragment);
		return execute(insertSqlFragment);
	}

	/**
	 * 删除
	 * 
	 * @param <M>               model type
	 * @param <C>               conditionSqlFragment type
	 * @param modelClass        model class
	 * @param conditionFragment 条件
	 * @return 删除的记录数
	 */
	protected <M extends Modelable, C extends ConditionSqlFragment> Integer remove(Class<M> modelClass,
			@Nullable C conditionFragment) {
		DeleteSqlFragment deleteSqlFragment = getModelSqlFragmentFactory().createDeleteSqlFragment(modelClass);
		if (null != conditionFragment) {
			deleteSqlFragment.setConditionSqlFragment(conditionFragment);
		}
		return execute(deleteSqlFragment);
	}

	/**
	 * 查询记录
	 * 
	 * @param <M>               model type
	 * @param <C>               conditionSqlFragment type
	 * @param modelClass        model class
	 * @param conditionFragment 条件
	 * @return 查询的记录数
	 */
	protected <M extends Modelable, C extends ConditionSqlFragment> Long count(Class<M> modelClass,
			@Nullable C conditionFragment) {
		CountSqlFragment countSqlFragment = getModelSqlFragmentFactory().createCountSqlFragment(modelClass);
		if (null != conditionFragment) {
			countSqlFragment.setConditionSqlFragment(conditionFragment);
		}
		return execute(countSqlFragment);
	}

	/**
	 * 修改
	 * 
	 * @param <M>                model type
	 * @param <C>                conditionSqlFragment type
	 * @param model              model
	 * @param selective          选择性
	 * @param attributeFragment  属性
	 * @param condintionFragment 条件
	 * @return 修改的记录数
	 */
	protected <M extends Modelable, C extends ConditionSqlFragment> Integer modify(M model, boolean selective,
			ModelColumnValidateWay modelColumnValidateWay, @Nullable C conditionFragment) {
		AttributeSqlFragment attributeSqlFragment = createAttributeFragment(model, DataBaseOperationType.UPDATE,
				selective, modelColumnValidateWay);
		// 不修改id
		// attributeSqlFragment.removeAttr(getOnlyPrimaryKey(model.getClass()).getColumn());
		UpdateSqlFragment updateSqlFragment = getModelSqlFragmentFactory().createUpdateSqlFragment(model.getClass(),
				attributeSqlFragment);
		if (null != conditionFragment) {
			updateSqlFragment.setConditionSqlFragment(conditionFragment);
		}
		return execute(updateSqlFragment);
	}

	/**
	 * @param <M>               model type
	 * @param modelClass        model class
	 * @param conditionFragment 条件
	 * @param sortFragment      排序
	 * @param pageNum           记录数量
	 * @param pageSize          页码
	 * @return model list
	 */
	protected <M extends Modelable> List<M> find(Class<M> modelClass, @Nullable ConditionSqlFragment conditionFragment,
			@Nullable SortSqlFragment sortFragment, @Nullable Integer pageNum, @Nullable Integer pageSize) {
		SelectSqlFragment selectSqlFragment = getModelSqlFragmentFactory().createSelectSqlFragment(modelClass);
		if (null != conditionFragment) {
			selectSqlFragment.setConditionSqlFragment(conditionFragment);
		}
		if (null != sortFragment) {
			selectSqlFragment.setSortSqlFragment(sortFragment);
		}
		if (null != pageNum && null != pageSize) {
			selectSqlFragment.startPage(pageNum, pageSize);
		}
		return execute(modelClass, selectSqlFragment);
	}

	/**
	 * 创建属性sql 如果是可选择性的，则根据{@link ModelNullProperty}来判断是属性是否保存或修改为null
	 * 
	 * @param <M>                      model type
	 * @param model                    model
	 * @param dataBaseOperationType    sql类型
	 * @param selective                可选择性：为空则不进行修改或者保存
	 * @param modelColumnValidateWay   model列验证方式
	 * @param attributeFragmentFactory 属性sql片段工厂
	 * @return {@link AttributeSqlFragment}
	 */
	protected <M extends Modelable> AttributeSqlFragment createAttributeFragment(M model,
			DataBaseOperationType dataBaseOperationType, boolean selective,
			ModelColumnValidateWay modelColumnValidateWay) {
		AttributeSqlFragment attributeSqlFragment = getModelSqlFragmentFactory().createAttributeSqlFragment();
		attributeSqlFragment.setDataBaseOperationType(dataBaseOperationType);
		ModelAndTable modelAndTable = getModelAndTable(model.getClass());
		List<FieldAndColumn> fieldAndColumns = modelAndTable.getFieldAndColumns();
		for (FieldAndColumn fieldAndColumn : fieldAndColumns) {
			if (fieldAndColumn.isExtend()) {// 不对拓展字段进行新增、修改操作
				continue;
			}
			try {
				Object value = getBeanProperty(model, fieldAndColumn.getFieldName());
				boolean valueIsNull = null == value;
				if ((modelColumnValidateWay == ModelColumnValidateWay.ALL)
						|| (modelColumnValidateWay == ModelColumnValidateWay.SELECTIVE && !valueIsNull)) {
					validFieldAndColumn(fieldAndColumn, value);
				}
				// 如果不是可选择性，或者可选择性且value不为null
				if ((!selective) || (selective && !valueIsNull)) {
					// 只有是可选择性时才判断是否存在伪装空值
					if (selective)
						// 判断是否是伪装空值
						value = ModelNullProperty.isPretendNull(value) ? null : value;
					attributeSqlFragment.addAttr(fieldAndColumn.getColumn(), value);
				}
			} catch (ModelException e) { // 1.0.1 版本修改。修复了长度验证错误但是没有抛出异常的问题
				throw e;
			} catch (Exception e) {
				throw new ModelException(e);
			}
		}
		return attributeSqlFragment;
	}

	/**
	 * 验证列属性。 验证是否非空、字段长度是否符合
	 * 
	 * @param modelFieldColumn 字段与列
	 * @param value            value
	 */
	protected void validFieldAndColumn(FieldAndColumn fieldAndColumn, Object value) {
		if (null == value) {
			if (!fieldAndColumn.isAllowNull()) {
				throw new ModelException("列:" + fieldAndColumn.getColumn() + "不支持为null");
			} else {
				return;
			}
		} else {
			Class<?> fieldType = fieldAndColumn.getFieldType();
			if (CharSequence.class.isAssignableFrom(fieldType)) {
				if (!fieldAndColumn.isAllowBlank()) {
					if (StringUtils.isBlank((CharSequence) value)) {
						throw new ModelException("列:" + fieldAndColumn.getColumn() + "不支持为空白字符");
					}
				}
			}
		}

		long minLength = fieldAndColumn.getMinLength();
		long maxLength = fieldAndColumn.getMaxLength();
		int valueLength = 0;
		if (value instanceof Number) {
			valueLength = (value + "").length();
		} else if (value instanceof CharSequence) {
			valueLength = ((CharSequence) value).length();
		} else {
			valueLength = value.toString().length();
		}
		if (valueLength < minLength) {
			throw new ModelException("列\"" + fieldAndColumn.getColumn() + "\"的值(" + value + ")太小(实际值: " + valueLength
					+ ", 最小值: " + minLength + ")");
		} else if (valueLength > maxLength) {
			throw new ModelException("列\"" + fieldAndColumn.getColumn() + "\"的值(" + value + ")太大 (实际值: " + valueLength
					+ ", 最大值: " + maxLength + ")");
		}
	}

	@Override
	public ModelConfiguration getModelConfiguration() {
		return modelConfiguration;
	}

	protected <M extends Modelable> ModelAndTable getModelAndTable(M model) {
		return getModelAndTable(model.getClass());
	}

	protected <M extends Modelable> ModelAndTable getModelAndTable(Class<M> modelClass) {
		return modelAndTableManager.getModelAndTable(modelClass);
	}

	public ModelSqlFragmentFactory getModelSqlFragmentFactory() {
		return modelSqlFragmentFactory;
	}

	/**
	 * @see #getModelProperty(Object, String)
	 * @deprecated
	 */
	protected Object getBeanProperty(Object bean, String fieldName) throws NoSuchMethodException {
		return getModelProperty(bean, fieldName);
	}

	/**
	 * @throws NoSuchMethodException
	 * @see #setModelProperty(Object, String, Object)
	 * @deprecated
	 */
	protected void setBeanProperty(Object bean, String propertyName, Object value) throws NoSuchMethodException {
		setModelProperty(bean, propertyName, value);
	}

	/**
	 * 获取model属性值
	 * 
	 * @see ModelProperty#get(Object, String)
	 */
	protected Object getModelProperty(Object model, String property) {
		return modelConfiguration.getModelProperty().get(model, property);
	}

	/**
	 * 设置model属性值
	 * 
	 * @see ModelProperty#set(Object, String, Object)
	 */
	protected void setModelProperty(Object model, String property, Object value) {
		modelConfiguration.getModelProperty().set(model, property, value);
	}

	/**
	 * 获取唯一的主键列
	 * 
	 * @param <M>
	 * @param modelClass
	 * @return
	 */
	protected <M extends Modelable> FieldAndColumn getOnlyPrimaryKey(Class<M> modelClass) throws PrimaryKeyException {
		ModelAndTable modelAndTable = getModelAndTable(modelClass);
		return modelAndTable.getOnlyPrimaryKey();
	}

	/**
	 * model列验证方式
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

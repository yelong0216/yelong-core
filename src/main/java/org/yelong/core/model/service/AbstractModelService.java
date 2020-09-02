/**
 * 
 */
package org.yelong.core.model.service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.yelong.commons.util.ListUtilsE;
import org.yelong.core.annotation.Nullable;
import org.yelong.core.jdbc.DataBaseOperationType;
import org.yelong.core.jdbc.sql.BoundSql;
import org.yelong.core.jdbc.sql.attribute.AttributeSqlFragment;
import org.yelong.core.jdbc.sql.condition.ConditionSqlFragment;
import org.yelong.core.jdbc.sql.executable.AbstractSqlFragmentExecutor;
import org.yelong.core.jdbc.sql.executable.CountSqlFragment;
import org.yelong.core.jdbc.sql.executable.DeleteSqlFragment;
import org.yelong.core.jdbc.sql.executable.InsertSqlFragment;
import org.yelong.core.jdbc.sql.executable.SelectSqlFragment;
import org.yelong.core.jdbc.sql.executable.UpdateSqlFragment;
import org.yelong.core.jdbc.sql.group.GroupSqlFragment;
import org.yelong.core.jdbc.sql.sort.SortSqlFragment;
import org.yelong.core.model.ModelConfiguration;
import org.yelong.core.model.ModelNullProperty;
import org.yelong.core.model.Modelable;
import org.yelong.core.model.manage.FieldAndColumn;
import org.yelong.core.model.manage.ModelAndTable;
import org.yelong.core.model.property.ModelProperty;
import org.yelong.core.model.sql.ModelSqlFragmentFactory;
import org.yelong.core.model.sql.SelectSqlColumnMode;

/**
 * 抽象模型业务实现
 * 
 * @since 1.0
 */
public abstract class AbstractModelService extends AbstractSqlFragmentExecutor implements ModelService {

	private final ModelConfiguration modelConfiguration;

	public AbstractModelService(ModelConfiguration modelConfiguration) {
		this.modelConfiguration = modelConfiguration;
	}

	// ==================================================save==================================================

	@Override
	public boolean save(Modelable model) {
		return save(model, false, ModelColumnValidateWay.ALL) > 0;
	}

	@Override
	public boolean saveSelective(Modelable model) {
		return save(model, true, ModelColumnValidateWay.ALL) > 0;
	}

	protected Integer save(Modelable model, boolean selective, ModelColumnValidateWay modelColumnValidateWay) {
		Objects.requireNonNull(model);
		AttributeSqlFragment attributeSqlFragment = createAttributeFragment(model, DataBaseOperationType.INSERT,
				selective, modelColumnValidateWay);
		InsertSqlFragment insertSqlFragment = getModelSqlFragmentFactory().createInsertSqlFragment(model.getClass(),
				attributeSqlFragment);
		return execute(insertSqlFragment);
	}

	// ==================================================remove==================================================

	@Override
	public Integer removeBySqlFragment(Class<? extends Modelable> modelClass,
			ConditionSqlFragment conditionSqlFragment) {
		DeleteSqlFragment deleteSqlFragment = getModelSqlFragmentFactory().createDeleteSqlFragment(modelClass);
		if (null != conditionSqlFragment) {
			deleteSqlFragment.setConditionSqlFragment(conditionSqlFragment);
		}
		return execute(deleteSqlFragment);
	}

	// ==================================================modify==================================================

	@Override
	public Integer modifyBySqlFragment(Modelable model, ConditionSqlFragment conditionSqlFragment) {
		return modify(model, false, ModelColumnValidateWay.SELECTIVE, conditionSqlFragment);
	}

	@Override
	public Integer modifySelectiveBySqlFragment(Modelable model, ConditionSqlFragment conditionSqlFragment) {
		return modify(model, true, ModelColumnValidateWay.SELECTIVE, conditionSqlFragment);
	}

	@Override
	public Integer modifyBySqlFragment(String updateSql, Object[] updateSqlparams,
			ConditionSqlFragment conditionSqlFragment) {
		UpdateSqlFragment updateSqlFragment = modelConfiguration.getModelSqlFragmentFactory()
				.createUpdateSqlFragment(updateSql, updateSqlparams);
		if (null != conditionSqlFragment) {
			updateSqlFragment.setConditionSqlFragment(conditionSqlFragment);
		}
		return execute(updateSqlFragment);
	}

	protected Integer modify(Modelable model, boolean selective, ModelColumnValidateWay modelColumnValidateWay,
			@Nullable ConditionSqlFragment conditionSqlFragment) {
		AttributeSqlFragment attributeSqlFragment = createAttributeFragment(model, DataBaseOperationType.UPDATE,
				selective, modelColumnValidateWay);
		UpdateSqlFragment updateSqlFragment = getModelSqlFragmentFactory().createUpdateSqlFragment(model.getClass(),
				attributeSqlFragment);
		if (null != conditionSqlFragment) {
			updateSqlFragment.setConditionSqlFragment(conditionSqlFragment);
		}
		return execute(updateSqlFragment);
	}

	// ==================================================count==================================================

	@Override
	public Long countBySqlFragment(Class<? extends Modelable> modelClass, ConditionSqlFragment conditionSqlFragment) {
		CountSqlFragment countSqlFragment = getModelSqlFragmentFactory().createCountSqlFragment(modelClass);
		if (null != conditionSqlFragment) {
			countSqlFragment.setConditionSqlFragment(conditionSqlFragment);
		}
		return execute(countSqlFragment);
	}

	@Override
	public Long countBySqlFragment(String countSql, Object[] countSqlParams,
			ConditionSqlFragment conditionSqlFragment) {
		CountSqlFragment countSqlFragment = getModelSqlFragmentFactory().createCountSqlFragment(countSql,
				countSqlParams);
		if (null != conditionSqlFragment) {
			countSqlFragment.setConditionSqlFragment(conditionSqlFragment);
		}
		return execute(countSqlFragment);
	}

	// ==================================================find==================================================

	@Override
	public <M extends Modelable> List<M> findBySqlFragment(Class<M> modelClass,
			ConditionSqlFragment conditionSqlFragment, SortSqlFragment sortSqlFragment) {
		return findBySqlFragment(modelClass, conditionSqlFragment, null, sortSqlFragment);
	}

	@Override
	public <M extends Modelable> List<M> findBySqlFragment(Class<M> modelClass,
			ConditionSqlFragment conditionSqlFragment, GroupSqlFragment groupSqlFragment,
			SortSqlFragment sortSqlFragment) {
		SelectSqlColumnMode selectSqlColumnMode = modelConfiguration.getModelProperties().getSelectSqlColumnMode();
		SelectSqlFragment selectSqlFragment = getModelSqlFragmentFactory().createSelectSqlFragment(modelClass,
				selectSqlColumnMode, false);
		if (null != conditionSqlFragment) {
			selectSqlFragment.setConditionSqlFragment(conditionSqlFragment);
		}
		if (null != groupSqlFragment) {
			selectSqlFragment.setGroupSqlFragment(groupSqlFragment);
		}
		if (null != sortSqlFragment) {
			selectSqlFragment.setSortSqlFragment(sortSqlFragment);
		}
		return execute(modelClass, selectSqlFragment);
	}

	@Override
	public <M extends Modelable> List<M> findBySqlFragment(Class<M> modelClass, String selectSql,
			Object[] selectSqlParams, ConditionSqlFragment conditionSqlFragment, SortSqlFragment sortSqlFragment) {
		return findBySqlFragment(modelClass, selectSql, selectSqlParams, conditionSqlFragment, null, sortSqlFragment);
	}

	@Override
	public <M extends Modelable> List<M> findBySqlFragment(Class<M> modelClass, String selectSql,
			Object[] selectSqlParams, ConditionSqlFragment conditionSqlFragment, GroupSqlFragment groupSqlFragment,
			SortSqlFragment sortSqlFragment) {
		SelectSqlFragment selectSqlFragment = getModelSqlFragmentFactory().createSelectSqlFragment(selectSql,
				selectSqlParams);
		if (null != conditionSqlFragment) {
			selectSqlFragment.setConditionSqlFragment(conditionSqlFragment);
		}
		if (null != groupSqlFragment) {
			selectSqlFragment.setGroupSqlFragment(groupSqlFragment);
		}
		if (null != sortSqlFragment) {
			selectSqlFragment.setSortSqlFragment(sortSqlFragment);
		}
		return execute(modelClass, selectSqlFragment);
	}

	@Override
	public <M extends Modelable> List<M> findBySQL(Class<M> modelClass, String selectSql, Object[] params) {
		SelectSqlFragment selectSqlFragment = getModelSqlFragmentFactory().createSelectSqlFragment(selectSql, params);
		return execute(modelClass, selectSqlFragment);
	}

	// ==================================================findFirst==================================================

	@Override
	public <M extends Modelable> M findFirstBySqlFragment(Class<M> modelClass,
			ConditionSqlFragment conditionSqlFragment, SortSqlFragment sortSqlFragment) {
		return ListUtilsE.get(findBySqlFragment(modelClass, conditionSqlFragment, sortSqlFragment), 0);
	}

	@Override
	public <M extends Modelable> M findFirstBySqlFragment(Class<M> modelClass,
			ConditionSqlFragment conditionSqlFragment, GroupSqlFragment groupSqlFragment,
			SortSqlFragment sortSqlFragment) {
		return ListUtilsE.get(findBySqlFragment(modelClass, conditionSqlFragment, groupSqlFragment, sortSqlFragment),
				0);
	}

	@Override
	public <M extends Modelable> M findFirstBySqlFragment(Class<M> modelClass, String selectSql,
			Object[] selectSqlParams, ConditionSqlFragment conditionSqlFragment, SortSqlFragment sortSqlFragment) {
		return ListUtilsE.get(
				findBySqlFragment(modelClass, selectSql, selectSqlParams, conditionSqlFragment, sortSqlFragment), 0);
	}

	@Override
	public <M extends Modelable> M findFirstBySqlFragment(Class<M> modelClass, String selectSql,
			Object[] selectSqlParams, ConditionSqlFragment conditionSqlFragment, GroupSqlFragment groupSqlFragment,
			SortSqlFragment sortSqlFragment) {
		return ListUtilsE.get(findBySqlFragment(modelClass, selectSql, selectSqlParams, conditionSqlFragment,
				groupSqlFragment, sortSqlFragment), 0);
	}

	@Override
	public <M extends Modelable> M findFirstBySQL(Class<M> modelClass, String selectSql, Object[] params) {
		return ListUtilsE.get(findBySQL(modelClass, selectSql, params), 0);
	}

	// ==================================================findSingleColumn==================================================

	@Override
	public <T> List<T> findSingleColumnBySqlFragment(Class<? extends Modelable> modelClass, String selectColumn,
			ConditionSqlFragment conditionSqlFragment, SortSqlFragment sortSqlFragment) {
		return findSingleColumnBySqlFragment(modelClass, selectColumn, conditionSqlFragment, null, sortSqlFragment);
	}

	@Override
	public <T> List<T> findSingleColumnBySqlFragment(Class<? extends Modelable> modelClass, String selectColumn,
			ConditionSqlFragment conditionSqlFragment, GroupSqlFragment groupSqlFragment,
			SortSqlFragment sortSqlFragment) {
		SelectSqlFragment selectSqlFragment = getModelSqlFragmentFactory().createSelectSqlFragmentByColumns(modelClass,
				Arrays.asList(selectColumn));
		if (null != conditionSqlFragment) {
			selectSqlFragment.setConditionSqlFragment(conditionSqlFragment);
		}
		if (null != groupSqlFragment) {
			selectSqlFragment.setGroupSqlFragment(groupSqlFragment);
		}
		if (null != sortSqlFragment) {
			selectSqlFragment.setSortSqlFragment(sortSqlFragment);
		}
		BoundSql boundSql = selectSqlFragment.getBoundSql();
		return getBaseDataBaseOperation().selectColumn(boundSql.getSql(), boundSql.getParams());
	}

	@Override
	public <T> T findFirstSingleColumnBySqlFragment(Class<? extends Modelable> modelClass, String selectColumn,
			ConditionSqlFragment conditionSqlFragment, SortSqlFragment sortSqlFragment) {
		return ListUtilsE
				.get(findSingleColumnBySqlFragment(modelClass, selectColumn, conditionSqlFragment, sortSqlFragment), 0);
	}

	@Override
	public <T> T findFirstSingleColumnBySqlFragment(Class<? extends Modelable> modelClass, String selectColumn,
			ConditionSqlFragment conditionSqlFragment, GroupSqlFragment groupSqlFragment,
			SortSqlFragment sortSqlFragment) {
		return ListUtilsE.get(findSingleColumnBySqlFragment(modelClass, selectColumn, conditionSqlFragment,
				groupSqlFragment, sortSqlFragment), 0);
	}

	@Override
	public <T> List<T> findSingleColumnBySqlFragment(String selectSql, Object[] selectSqlParams,
			ConditionSqlFragment conditionSqlFragment, SortSqlFragment sortSqlFragment) {
		return findSingleColumnBySqlFragment(selectSql, selectSqlParams, conditionSqlFragment, null, sortSqlFragment);
	}

	@Override
	public <T> List<T> findSingleColumnBySqlFragment(String selectSql, Object[] selectSqlParams,
			ConditionSqlFragment conditionSqlFragment, GroupSqlFragment groupSqlFragment,
			SortSqlFragment sortSqlFragment) {
		SelectSqlFragment selectSqlFragment = getModelSqlFragmentFactory().createSelectSqlFragment(selectSql,
				selectSqlParams);
		if (null != conditionSqlFragment) {
			selectSqlFragment.setConditionSqlFragment(conditionSqlFragment);
		}
		if (null != groupSqlFragment) {
			selectSqlFragment.setGroupSqlFragment(groupSqlFragment);
		}
		if (null != sortSqlFragment) {
			selectSqlFragment.setSortSqlFragment(sortSqlFragment);
		}
		BoundSql boundSql = selectSqlFragment.getBoundSql();
		return getBaseDataBaseOperation().selectColumn(boundSql.getSql(), boundSql.getParams());
	}

	@Override
	public <T> T findFirstSingleColumnBySqlFragment(String selectSql, Object[] selectSqlParams,
			ConditionSqlFragment conditionSqlFragment, SortSqlFragment sortSqlFragment) {
		return ListUtilsE.get(
				findSingleColumnBySqlFragment(selectSql, selectSqlParams, conditionSqlFragment, sortSqlFragment), 0);
	}

	@Override
	public <T> T findFirstSingleColumnBySqlFragment(String selectSql, Object[] selectSqlParams,
			ConditionSqlFragment conditionSqlFragment, GroupSqlFragment groupSqlFragment,
			SortSqlFragment sortSqlFragment) {
		return ListUtilsE.get(findSingleColumnBySqlFragment(selectSql, selectSqlParams, conditionSqlFragment,
				groupSqlFragment, sortSqlFragment), 0);
	}

	// ==================================================findPage==================================================

	@Override
	public <M extends Modelable> List<M> findPageBySqlFragment(Class<M> modelClass,
			ConditionSqlFragment conditionSqlFragment, SortSqlFragment sortSqlFragment, int pageNum, int pageSize) {
		return findPageBySqlFragment(modelClass, conditionSqlFragment, null, sortSqlFragment, pageNum, pageSize);
	}

	@Override
	public <M extends Modelable> List<M> findPageBySqlFragment(Class<M> modelClass,
			ConditionSqlFragment conditionSqlFragment, GroupSqlFragment groupSqlFragment,
			SortSqlFragment sortSqlFragment, int pageNum, int pageSize) {
		SelectSqlColumnMode selectSqlColumnMode = modelConfiguration.getModelProperties().getSelectSqlColumnMode();
		SelectSqlFragment selectSqlFragment = getModelSqlFragmentFactory().createSelectSqlFragment(modelClass,
				selectSqlColumnMode, false);
		if (null != conditionSqlFragment) {
			selectSqlFragment.setConditionSqlFragment(conditionSqlFragment);
		}
		if (null != groupSqlFragment) {
			selectSqlFragment.setGroupSqlFragment(groupSqlFragment);
		}
		if (null != sortSqlFragment) {
			selectSqlFragment.setSortSqlFragment(sortSqlFragment);
		}
		if (pageNum < 1) {
			pageNum = 1;
		}
		if (pageSize <= 0) {
			throw new IllegalArgumentException("Page size cannot be less than or equal to 0");
		}
		selectSqlFragment.startPage(pageNum, pageSize);
		return execute(modelClass, selectSqlFragment);
	}

	@Override
	public <M extends Modelable> List<M> findPageBySqlFragment(Class<M> modelClass, String selectSql,
			Object[] selectSqlParams, ConditionSqlFragment conditionSqlFragment, SortSqlFragment sortSqlFragment,
			int pageNum, int pageSize) {
		return findPageBySqlFragment(modelClass, selectSql, selectSqlParams, conditionSqlFragment, null,
				sortSqlFragment, pageNum, pageSize);
	}

	@Override
	public <M extends Modelable> List<M> findPageBySqlFragment(Class<M> modelClass, String selectSql,
			Object[] selectSqlParams, ConditionSqlFragment conditionSqlFragment, GroupSqlFragment groupSqlFragment,
			SortSqlFragment sortSqlFragment, int pageNum, int pageSize) {
		SelectSqlFragment selectSqlFragment = getModelSqlFragmentFactory().createSelectSqlFragment(selectSql,
				selectSqlParams);
		if (null != conditionSqlFragment) {
			selectSqlFragment.setConditionSqlFragment(conditionSqlFragment);
		}
		if (null != groupSqlFragment) {
			selectSqlFragment.setGroupSqlFragment(groupSqlFragment);
		}
		if (null != sortSqlFragment) {
			selectSqlFragment.setSortSqlFragment(sortSqlFragment);
		}
		if (pageNum < 1) {
			pageNum = 1;
		}
		if (pageSize <= 0) {
			throw new IllegalArgumentException("Page size cannot be less than or equal to 0");
		}
		selectSqlFragment.startPage(pageNum, pageSize);
		return execute(modelClass, selectSqlFragment);
	}

	@Override
	public <M extends Modelable> List<M> findPageBySQL(Class<M> modelClass, String selectSql, Object[] params,
			int pageNum, int pageSize) {
		SelectSqlFragment selectSqlFragment = getModelSqlFragmentFactory().createSelectSqlFragment(selectSql, params);
		if (pageNum < 1) {
			pageNum = 1;
		}
		if (pageSize <= 0) {
			throw new IllegalArgumentException("Page size cannot be less than or equal to 0");
		}
		selectSqlFragment.startPage(pageNum, pageSize);
		return execute(modelClass, selectSqlFragment);
	}

	// ==================================================util==================================================

	protected AttributeSqlFragment createAttributeFragment(Modelable model, DataBaseOperationType dataBaseOperationType,
			boolean selective, ModelColumnValidateWay modelColumnValidateWay) {
		AttributeSqlFragment attributeSqlFragment = getModelSqlFragmentFactory().createAttributeSqlFragment();
		attributeSqlFragment.setDataBaseOperationType(dataBaseOperationType);
		ModelAndTable modelAndTable = getModelAndTable(model.getClass());
		List<FieldAndColumn> fieldAndColumns = modelAndTable.getNormalFieldAndColumns();
		for (FieldAndColumn fieldAndColumn : fieldAndColumns) {
			Object value = getModelProperty(model, fieldAndColumn.getFieldName());
			boolean valueIsNull = null == value;
			if ((modelColumnValidateWay == ModelColumnValidateWay.ALL)
					|| (modelColumnValidateWay == ModelColumnValidateWay.SELECTIVE && !valueIsNull)) {
				if (dataBaseOperationType == DataBaseOperationType.UPDATE) {
					if (modelConfiguration.getModelProperties().isModifyValidateModel()) {
						validFieldAndColumn(fieldAndColumn, value);
					}
				} else if (dataBaseOperationType == DataBaseOperationType.INSERT) {
					if (modelConfiguration.getModelProperties().isSaveValidateModel()) {
						validFieldAndColumn(fieldAndColumn, value);
					}
				}

			}
			// 如果不是可选择性，或者可选择性且value不为null
			if ((!selective) || (selective && !valueIsNull)) {
				// 只有是可选择性时才判断是否存在伪装空值
				if (selective)
					// 判断是否是伪装空值
					value = ModelNullProperty.isPretendNull(value) ? null : value;
				attributeSqlFragment.addAttr(fieldAndColumn.getColumn(), value);
			}
		}
		return attributeSqlFragment;
	}

	protected void validFieldAndColumn(FieldAndColumn fieldAndColumn, Object value) {
		getModelConfiguration().getModelValidator().validateFieldAndColumn(fieldAndColumn, value);
	}

	@Override
	public ModelConfiguration getModelConfiguration() {
		return modelConfiguration;
	}

	protected ModelAndTable getModelAndTable(Modelable model) {
		return getModelAndTable(model.getClass());
	}

	protected ModelAndTable getModelAndTable(Class<? extends Modelable> modelClass) {
		return getModelConfiguration().getModelManager().getModelAndTable(modelClass);
	}

	protected ModelSqlFragmentFactory getModelSqlFragmentFactory() {
		return getModelConfiguration().getModelSqlFragmentFactory();
	}

	/**
	 * 获取model属性值
	 * 
	 * @see ModelProperty#get(Object, String)
	 */
	protected Object getModelProperty(Modelable model, String property) {
		return modelConfiguration.getModelProperty().get(model, property);
	}

	/**
	 * 设置model属性值
	 * 
	 * @see ModelProperty#set(Object, String, Object)
	 */
	protected void setModelProperty(Modelable model, String property, Object value) {
		modelConfiguration.getModelProperty().set(model, property, value);
	}

	protected FieldAndColumn getOnlyPrimaryKey(Class<? extends Modelable> modelClass) {
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

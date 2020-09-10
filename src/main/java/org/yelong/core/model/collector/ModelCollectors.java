/**
 * 
 */
package org.yelong.core.model.collector;

import static org.yelong.core.jdbc.sql.condition.ConditionalOperator.EQUAL;
import static org.yelong.core.jdbc.sql.condition.ConditionalOperator.IN;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.yelong.commons.lang.Strings;
import org.yelong.commons.util.MapUtilsE;
import org.yelong.core.annotation.Nullable;
import org.yelong.core.jdbc.DataBaseOperationType;
import org.yelong.core.jdbc.sql.attribute.AttributeSqlFragment;
import org.yelong.core.jdbc.sql.condition.ConditionSqlFragment;
import org.yelong.core.jdbc.sql.condition.ConditionalOperator;
import org.yelong.core.jdbc.sql.condition.support.Condition;
import org.yelong.core.jdbc.sql.condition.support.ConditionFactory;
import org.yelong.core.jdbc.sql.executable.UpdateSqlFragment;
import org.yelong.core.jdbc.sql.sort.support.Sort;
import org.yelong.core.model.ModelConfiguration;
import org.yelong.core.model.Modelable;
import org.yelong.core.model.collector.support.copy.CopyListModelCollector;
import org.yelong.core.model.collector.support.copy.CopySingleModelCollector;
import org.yelong.core.model.collector.support.copy.impl.AbstractCopyListModelCollector;
import org.yelong.core.model.collector.support.copy.impl.AbstractCopySingleModelCollector;
import org.yelong.core.model.collector.support.copy.impl.CopyListModelCollectorImpl;
import org.yelong.core.model.collector.support.copy.impl.CopySingleModelCollectorImpl;
import org.yelong.core.model.collector.support.count.CountModelCollector;
import org.yelong.core.model.collector.support.count.impl.AbstractCountModelCollector;
import org.yelong.core.model.collector.support.count.impl.CountEmptyModelCollector;
import org.yelong.core.model.collector.support.exist.ExistModelCollector;
import org.yelong.core.model.collector.support.exist.impl.AbstractExistModelCollector;
import org.yelong.core.model.collector.support.exist.impl.NotExistModelCollector;
import org.yelong.core.model.collector.support.find.FindModelCollector;
import org.yelong.core.model.collector.support.find.FindSingleColumnModelCollector;
import org.yelong.core.model.collector.support.find.impl.AbstractFindModelCollector;
import org.yelong.core.model.collector.support.find.impl.AbstractFindSingleColumnModelCollector;
import org.yelong.core.model.collector.support.find.impl.FindEmptyModelCollector;
import org.yelong.core.model.collector.support.find.impl.FindEmptySingleColumnModelCollector;
import org.yelong.core.model.collector.support.get.GetModelCollector;
import org.yelong.core.model.collector.support.get.GetSingleColumnModelCollector;
import org.yelong.core.model.collector.support.get.impl.AbstractGetModelCollector;
import org.yelong.core.model.collector.support.get.impl.AbstractGetSingleColumnModelCollector;
import org.yelong.core.model.collector.support.get.impl.GetNullModelCollector;
import org.yelong.core.model.collector.support.get.impl.GetSingleColumnNullModelCollector;
import org.yelong.core.model.collector.support.modify.ModifyModelCollector;
import org.yelong.core.model.collector.support.modify.impl.AbstractModifyModelCollector;
import org.yelong.core.model.collector.support.remove.RemoveModelCollector;
import org.yelong.core.model.collector.support.remove.impl.AbstractRemoveModelCollector;
import org.yelong.core.model.collector.support.remove.single.condition.RemoveBySingleConditionModelCollector;
import org.yelong.core.model.collector.support.remove.single.condition.RemoveBySingleConditionModelCollectorImpl;
import org.yelong.core.model.collector.support.remove.single.contains.RemoveBySingleColumnContainsModelCollector;
import org.yelong.core.model.collector.support.remove.single.contains.RemoveBySingleColumnContainsModelCollectorImpl;
import org.yelong.core.model.collector.support.remove.single.primarykey.AbstractRemoveByOnlyPrimaryKeyModelCollector;
import org.yelong.core.model.collector.support.remove.single.primarykey.RemoveByOnlyPrimaryKeyModelCollector;
import org.yelong.core.model.collector.support.save.SaveListModelCollector;
import org.yelong.core.model.collector.support.save.SaveSingleModelCollector;
import org.yelong.core.model.collector.support.save.impl.SaveListModelCollectorImpl;
import org.yelong.core.model.collector.support.save.impl.SaveSingleModelCollectorImpl;
import org.yelong.core.model.manage.FieldAndColumn;
import org.yelong.core.model.manage.ModelAndTable;
import org.yelong.core.model.property.ModelProperty;
import org.yelong.core.model.service.SqlModelService;
import org.yelong.core.model.sql.ModelSqlFragmentFactory;
import org.yelong.core.model.sql.SqlModel;
import org.yelong.core.model.sql.SqlModelResolver;

/**
 * 模型收集者
 * 
 * 提供常用的模型收集方法
 * 
 * @since 1.3
 */
public final class ModelCollectors {

	/**
	 * 模型修改的可选择性
	 */
	private static final ThreadLocal<Boolean> MODEL_MODIFY_SELECTIVE = new ThreadLocal<Boolean>();

	private ModelCollectors() {
	}

	// ==================================================threadLocalSet/Get==================================================

	/**
	 * 设置的选择性仅会影响下一次的修改模型的方法
	 */
	public static void setModifySelective(boolean modifySelective) {
		MODEL_MODIFY_SELECTIVE.set(modifySelective);
	}

	public static boolean getModifySelective() {
		Boolean value = MODEL_MODIFY_SELECTIVE.get();
		if (null == value) {
			return true;
		}
		MODEL_MODIFY_SELECTIVE.remove();
		return value;
	}

	// ==================================================save==================================================

	/**
	 * 保存单个模型
	 * 
	 * @param <M>   model type
	 * @param model model
	 * @return 保存模型收集器
	 */
	public static <M extends Modelable> SaveSingleModelCollector<M> saveSingle(M model) {
		return new SaveSingleModelCollectorImpl<M>(model);
	}

	/**
	 * 保存模型集合
	 * 
	 * @param <M>        model type
	 * @param modelClass model class
	 * @param models     model集合
	 * @return 保存模型收集器
	 */
	public static <M extends Modelable> SaveListModelCollector<M> saveList(Class<M> modelClass, List<M> models) {
		return new SaveListModelCollectorImpl<M>(modelClass, models);
	}

	// ==================================================copy-single==================================================

	/**
	 * 复制单个模型
	 * 
	 * @param <M>   model type
	 * @param model 需要被复制的model
	 * @return 复制单个模型的模型收集器
	 */
	public static <M extends Modelable> CopySingleModelCollector<M> copySingle(M model) {
		Objects.requireNonNull(model);
		return new CopySingleModelCollectorImpl<M>(model);
	}

	/**
	 * 根据唯一主键条件复制该条记录 如果不存在该条记录则不会进行复制操作
	 * 
	 * <pre>
	 * String userId = "123456";
	 * // 如果复制的对象不存在则返回null
	 * User user = modelService
	 * 		.collect(ModelCollectors.copySingleByOnlyPrimaryKeyEQ(User.class, userId).beforeSave((x, model) -> {
	 * 			model.setUserNo("123");// 设置某些属性
	 * 		}));
	 * Objects.requireNonNull(user, "不存在的用户：" + userId);
	 * </pre>
	 * 
	 * @param <M>             model type
	 * @param modelClass      model class
	 * @param primaryKeyValue 唯一主键值
	 * @return 复制单个模型收集器
	 */
	public static <M extends Modelable> CopySingleModelCollector<M> copySingleByOnlyPrimaryKeyEQ(
			final Class<M> modelClass, final Object primaryKeyValue) {
		return new AbstractCopySingleModelCollector<M>(modelClass) {
			@Override
			protected M getCopySourceModel(SqlModelService modelService) {
				return modelService.collect(ModelCollectors.getModelByOnlyPrimaryKeyEQ(modelClass, primaryKeyValue));
			}
		};
	}

	/**
	 * 根据单列等于某值的条件复制查询到的第一条记录
	 * 
	 * @param <M>                  model type
	 * @param modelClass           model class
	 * @param conditionColumn      条件列
	 * @param conditionColumnValue 条件列的值
	 * @return 复制单个模型收集器
	 */
	public static <M extends Modelable> CopySingleModelCollector<M> copySingleBySingleColumnEQ(Class<M> modelClass,
			String conditionColumn, Object conditionColumnValue) {
		return copySingleByCondition(modelClass, new Condition(conditionColumn, EQUAL, conditionColumnValue));
	}

	/**
	 * 根据条件复制查询到的第一条记录
	 * 
	 * @param <M>        model type
	 * @param modelClass model class
	 * @param condition  条件
	 * @return 复制单个模型收集器
	 */
	public static <M extends Modelable> CopySingleModelCollector<M> copySingleByCondition(Class<M> modelClass,
			Condition condition) {
		return copySingleConditionSort(modelClass, condition, null);
	}

	/**
	 * 根据条件和排序复制查询到的第一条记录
	 * 
	 * @param <M>        model type
	 * @param modelClass model class
	 * @param condition  条件
	 * @param sort       排序
	 * @return 复制单个模型收集器
	 */
	public static <M extends Modelable> CopySingleModelCollector<M> copySingleConditionSort(Class<M> modelClass,
			@Nullable Condition condition, @Nullable Sort sort) {
		SqlModel<M> sqlModel = new SqlModel<M>(modelClass);
		if (null != condition) {
			sqlModel.addCondition(condition);
		}
		if (null != sort) {
			sqlModel.addSort(sort);
		}
		return copySingleBySqlModel(modelClass, sqlModel);
	}

	/**
	 * 根据 SqlModel<M> 复制查询到的第一条记录
	 * 
	 * @param <M>        model type
	 * @param modelClass model class
	 * @param sqlModel   sqlModel
	 * @return 复制单个模型收集器
	 */
	public static <M extends Modelable> CopySingleModelCollector<M> copySingleBySqlModel(Class<M> modelClass,
			SqlModel<M> sqlModel) {
		return new AbstractCopySingleModelCollector<M>(modelClass) {
			@Override
			protected M getCopySourceModel(SqlModelService modelService) {
				return modelService.findFirstBySqlModel(modelClass, sqlModel);
			}
		};
	}

	// ==================================================copy-list==================================================

	/**
	 * 复制模型集合
	 * 
	 * @param <M>        model type
	 * @param modelClass model class
	 * @param models     需要被复制的model集合
	 * @return 复制模型的模型收集器
	 */
	public static <M extends Modelable> CopyListModelCollector<M> copyList(Class<M> modelClass, List<M> models) {
		return new CopyListModelCollectorImpl<M>(modelClass, models);
	}

	/**
	 * 根据主键包含某些值得条件复制查询到的所有记录
	 * 
	 * @param <M>              model type
	 * @param modelClass       model class
	 * @param primaryKeyValues 唯一主键值数组
	 * @return 复制模型收集器
	 */
	public static <M extends Modelable> CopyListModelCollector<M> copyListByByOnlyPrimaryKeyContains(
			Class<M> modelClass, Object[] primaryKeyValues) {
		return new AbstractCopyListModelCollector<M>(modelClass) {
			@Override
			protected List<M> getCopySourceModels(SqlModelService modelService) {
				return modelService.collect(ModelCollectors.findByOnlyPrimaryKeyContains(modelClass, primaryKeyValues));
			}
		};
	}

	/**
	 * 根据单列等于某值的条件复制查询到的所有记录 <br/>
	 * 
	 * <pre>
	 * modelService.collect(ModelCollectors.copyListBySingleColumnEQ(User.class, "age", 18));
	 * </pre>
	 * 
	 * @param <M>                  model type
	 * @param modelClass           model class
	 * @param conditionColumn      条件列
	 * @param conditionColumnValue 条件列的值
	 * @return 复制集合模型收集器
	 */
	public static <M extends Modelable> CopyListModelCollector<M> copyListBySingleColumnEQ(Class<M> modelClass,
			String conditionColumn, Object conditionColumnValue) {
		return copyListByCondition(modelClass, new Condition(conditionColumn, EQUAL, conditionColumnValue));
	}

	/**
	 * 根据单列等于某值的条件复制查询到的所有记录
	 * 
	 * @param <M>                   model type
	 * @param modelClass            model class
	 * @param conditionColumn       条件列
	 * @param conditionColumnValues 条件列的值数组
	 * @return 复制集合模型收集器
	 */
	public static <M extends Modelable> CopyListModelCollector<M> copyListBySingleColumnContains(Class<M> modelClass,
			String conditionColumn, Object[] conditionColumnValues) {
		return copyListByCondition(modelClass, new Condition(conditionColumn, IN, conditionColumnValues));
	}

	/**
	 * 根据条件复制查询到的所有记录
	 * 
	 * @param <M>        model type
	 * @param modelClass model class
	 * @param condition  条件
	 * @return 复制集合模型收集器
	 */
	public static <M extends Modelable> CopyListModelCollector<M> copyListByCondition(Class<M> modelClass,
			Condition condition) {
		SqlModel<M> sqlModel = new SqlModel<M>(modelClass);
		sqlModel.addCondition(condition);
		return copyListBySqlModel(modelClass, sqlModel);
	}

	/**
	 * 根据条件复制查询到的所有记录
	 * 
	 * @param <M>        model type
	 * @param modelClass model class
	 * @param sqlModel   sqlModel
	 * @return 复制集合模型收集器
	 */
	public static <M extends Modelable> CopyListModelCollector<M> copyListBySqlModel(Class<M> modelClass,
			SqlModel<M> sqlModel) {
		return new AbstractCopyListModelCollector<M>(modelClass) {
			@Override
			protected List<M> getCopySourceModels(SqlModelService modelService) {
				return modelService.findBySqlModel(modelClass, sqlModel);
			}
		};
	}

	// ==================================================remove==================================================

	/**
	 * 删除所有记录数
	 * 
	 * @param <M>        model type
	 * @param modelClass model class
	 * @return 删除模型收集器
	 */
	public static <M extends Modelable> RemoveModelCollector<M> removeAll(Class<M> modelClass) {
		return new AbstractRemoveModelCollector<M>(modelClass) {
			@Override
			protected Integer doCollect(SqlModelService modelService) {
				return modelService.removeBySqlFragment(modelClass, null);
			}
		};
	}

	/**
	 * 根据主键等于某值的条件删除记录
	 * 
	 * @param <M>             model type
	 * @param modelClass      model class
	 * @param primaryKeyValue 唯一主键值
	 * @return 根据主键删除的模型收集器
	 */
	public static <M extends Modelable> RemoveByOnlyPrimaryKeyModelCollector<M> removeByOnlyPrimaryKeyEQ(
			Class<M> modelClass, Object primaryKeyValue) {
		return removeByOnlyPrimaryKeyContains(modelClass, ArrayUtils.toArray(primaryKeyValue));
	}

	/**
	 * 根据主键包含某些值得条件删除记录
	 * 
	 * @param <M>              model type
	 * @param modelClass       model class
	 * @param primaryKeyValues 唯一主键值数组
	 * @return 根据主键删除的模型收集器
	 */
	public static <M extends Modelable> RemoveByOnlyPrimaryKeyModelCollector<M> removeByOnlyPrimaryKeyContains(
			Class<M> modelClass, Object[] primaryKeyValues) {
		return new AbstractRemoveByOnlyPrimaryKeyModelCollector<M>(modelClass) {
			@Override
			protected Integer doCollect(SqlModelService modelService) {
				String onlyPrimaryKeyColumn = getOnlyPrimaryKeyColumn(modelService);
				return removeBySingleColumnContains(modelClass, onlyPrimaryKeyColumn, primaryKeyValues)
						.collect(modelService);
			}
		};
	}

	/**
	 * 根据单列等于某值的条件删除模型记录
	 * 
	 * <pre>
	 * modelService.collect(ModelCollectors.removeBySingleColumnEQ(User.class, "userName", "夜龙"));
	 * modelService.collect(ModelCollectors.removeBySingleColumnEQ(User.class, "age", 18));
	 * </pre>
	 * 
	 * @param <M>                  model type
	 * @param modelClass           model class
	 * @param conditionColumn      条件列
	 * @param conditionColumnValue 条件值
	 * @return 根据单列包含某值删除的模型收集器
	 */
	public static <M extends Modelable> RemoveBySingleColumnContainsModelCollector<M> removeBySingleColumnEQ(
			Class<M> modelClass, String conditionColumn, Object conditionColumnValue) {
		return removeBySingleColumnContains(modelClass, conditionColumn, ArrayUtils.toArray(conditionColumnValue));
	}

	/**
	 * 根据单列包含某值的条件删除模型记录
	 * 
	 * <pre>
	 * modelService.collect(
	 * 		ModelCollectors.removeBySingleColumnContains(User.class, "userName", ArrayUtils.toArray("夜龙", "夜龙1")));
	 * modelService
	 * 		.collect(ModelCollectors.removeBySingleColumnContains(User.class, "age", ArrayUtils.toArray(18, 19)));
	 * </pre>
	 * 
	 * @param <M>                   model type
	 * @param modelClass            model class
	 * @param conditionColumn       条件列
	 * @param conditionColumnValues 条件值数组
	 * @return 根据单列包含某值删除的模型收集器
	 */
	public static <M extends Modelable> RemoveBySingleColumnContainsModelCollector<M> removeBySingleColumnContains(
			Class<M> modelClass, String conditionColumn, Object[] conditionColumnValues) {
		return new RemoveBySingleColumnContainsModelCollectorImpl<M>(modelClass, conditionColumn,
				conditionColumnValues);
	}

	/**
	 * 根据指定的条件删除模型记录
	 * 
	 * @param <M>        model type
	 * @param modelClass model class
	 * @param condition  条件
	 * @return 根据条件删除的模型收集者
	 */
	public static <M extends Modelable> RemoveBySingleConditionModelCollector<M> removeByCondition(Class<M> modelClass,
			Condition condition) {
		Objects.requireNonNull(condition);
		return new RemoveBySingleConditionModelCollectorImpl<M>(modelClass, condition);
	}

	// ==================================================modify-model==================================================

	/**
	 * 根据模型唯一主键值修改模型数据。这个主键值直接在模型对象中获取且不会修改模型对象的唯一主键列数据
	 * 
	 * @param <M>   model type
	 * @param model 修改的模型对象。根据这个对象中的主键值作为修改条件
	 * @return 修改模型收集器
	 */
	@SuppressWarnings("unchecked")
	public static <M extends Modelable> ModifyModelCollector<M> modifyModelByOnlyPrimaryKeyEQ(final M model) {
		Class<M> modelClass = (Class<M>) model.getClass();
		boolean modifySelective = getModifySelective();
		return new AbstractModifyModelCollector<M>(modelClass) {
			@Override
			protected Integer doCollect(SqlModelService modelService) {
				ModelConfiguration modelConfiguration = modelService.getModelConfiguration();
				ModelAndTable modelAndTable = modelConfiguration.getModelManager().getModelAndTable(modelClass);
				FieldAndColumn fieldAndColumn = modelAndTable.getOnlyPrimaryKey();

				ModelProperty modelProperty = modelConfiguration.getModelProperty();
				String fieldName = fieldAndColumn.getFieldName();
				Object primaryKeyValue = modelProperty.get(model, fieldAndColumn.getFieldName());
				Objects.requireNonNull(primaryKeyValue, "唯一主键列(" + fieldAndColumn + ")值不能为 null");
				// 修改前将主键值清空，不进行修改
				modelProperty.set(model, fieldName, null);
				Condition condition = new Condition(fieldAndColumn.getColumn(), EQUAL, primaryKeyValue);
				SqlModel<M> sqlModel = new SqlModel<M>(modelClass).addCondition(condition);
				Integer result = modifySelective ? modelService.modifySelectiveBySqlModel(model, sqlModel)
						: modelService.modifyBySqlModel(model, sqlModel);
				// 修改后在主键值设置上
				modelProperty.set(model, fieldName, primaryKeyValue);
				return result;
			}
		};
	}

	/**
	 * 根据唯一主键列等于某值的条件修改模型记录
	 * 
	 * @param <M>             model type
	 * @param model           model
	 * @param primaryKeyValue 唯一主键值
	 * @return 修改模型收集器
	 */
	public static <M extends Modelable> ModifyModelCollector<M> modifyModelByOnlyPrimaryKeyEQ(final M model,
			Object primaryKeyValue) {
		return modifyModelByOnlyPrimaryKeyContains(model, ArrayUtils.toArray(primaryKeyValue));
	}

	/**
	 * 根据唯一主键包含某些值的条件修改模型数据
	 * 
	 * @param <M>              model type
	 * @param model            model
	 * @param primaryKeyValues 唯一主键的值数组
	 * @return 修改模型收集器
	 */
	@SuppressWarnings("unchecked")
	public static <M extends Modelable> ModifyModelCollector<M> modifyModelByOnlyPrimaryKeyContains(final M model,
			Object[] primaryKeyValues) {
		Class<M> modelClass = (Class<M>) model.getClass();
		boolean modifySelective = getModifySelective();
		return new AbstractModifyModelCollector<M>(modelClass) {
			@Override
			protected Integer doCollect(SqlModelService modelService) {
				ModelAndTable modelAndTable = modelService.getModelConfiguration().getModelManager()
						.getModelAndTable(modelClass);
				FieldAndColumn fieldAndColumn = modelAndTable.getOnlyPrimaryKey();
				Condition condition = new Condition(fieldAndColumn.getColumn(), IN, primaryKeyValues);
				SqlModel<M> sqlModel = new SqlModel<M>(modelClass).addCondition(condition);
				return modifySelective ? modelService.modifySelectiveBySqlModel(model, sqlModel)
						: modelService.modifyBySqlModel(model, sqlModel);
			}
		};
	}

	/**
	 * 根据单列等于某值的条件修改模型数据
	 * 
	 * @param <M>                  model type
	 * @param model                model
	 * @param conditionColumn      条件列
	 * @param conditionColumnValue 条件值
	 * @return 修改模型收集器
	 * @see SqlModelService#modifySelectiveBySqlModel(Modelable, SqlModel<M>)
	 */
	public static <M extends Modelable> ModifyModelCollector<M> modifyModelBySingleColumnEQ(M model,
			String conditionColumn, Object conditionColumnValue) {
		return modifyModelByCondition(model, new Condition(conditionColumn, EQUAL, conditionColumnValue));
	}

	/**
	 * 根据单列包含某些值的条件修改模型数据
	 * 
	 * @param <M>                   model type
	 * @param model                 model
	 * @param conditionColumn       条件列
	 * @param conditionColumnValues 条件值
	 * @return 修改模型收集器
	 * @see SqlModelService#modifySelectiveBySqlModel(Modelable, SqlModel<M>)
	 */
	public static <M extends Modelable> ModifyModelCollector<M> modifyModelBySingleColumnContains(M model,
			String conditionColumn, Object[] conditionColumnValues) {
		return modifyModelByCondition(model, new Condition(conditionColumn, IN, conditionColumnValues));
	}

	/**
	 * 根据条件修改模型数据
	 * 
	 * @param <M>       model type
	 * @param model     model
	 * @param condition 条件
	 * @return 修改模型收集器
	 * @see SqlModelService#modifySelectiveBySqlModel(Modelable, SqlModel<M>)
	 */
	public static <M extends Modelable> ModifyModelCollector<M> modifyModelByCondition(M model, Condition condition) {
		return modifyModelByConditions(model, Arrays.asList(condition));
	}

	/**
	 * 根据多条件修改模型数据
	 * 
	 * @param <M>        model type
	 * @param model      model
	 * @param conditions 条件集合
	 * @return 修改模型收集器
	 * @see SqlModelService#modifySelectiveBySqlModel(Modelable, SqlModel<M>)
	 */
	@SuppressWarnings("unchecked")
	public static <M extends Modelable> ModifyModelCollector<M> modifyModelByConditions(M model,
			List<Condition> conditions) {
		Class<M> modelClass = (Class<M>) model.getClass();
		boolean modifySelective = getModifySelective();
		return new AbstractModifyModelCollector<M>(modelClass) {
			@Override
			protected Integer doCollect(SqlModelService modelService) {
				SqlModel<M> sqlModel = new SqlModel<M>(modelClass).addConditions(conditions);
				return modifySelective ? modelService.modifySelectiveBySqlModel(model, sqlModel)
						: modelService.modifyBySqlModel(model, sqlModel);
			}
		};
	}

	// ==================================================modify-single-column==================================================

	/**
	 * 根据唯一主键等于某值的条件修改单列数据
	 * 
	 * @param <M>             model type
	 * @param modelClass      model class
	 * @param modifyColumn    修改的列
	 * @param modifyValue     修改后的值
	 * @param primaryKeyValue 唯一主键的值
	 * @return 修改模型收集器
	 */
	public static <M extends Modelable> ModifyModelCollector<M> modifySingleColumnByOnlyPrimaryKeyEQ(
			Class<M> modelClass, String modifyColumn, Object modifyValue, Object primaryKeyValue) {
		return modifyMultiColumnByOnlyPrimaryKey(modelClass, MapUtilsE.asMap(modifyColumn, modifyValue),
				primaryKeyValue, EQUAL);
	}

	/**
	 * 根据唯一主键包含某些值的条件修改单列数据
	 * 
	 * @param <M>              model type
	 * @param modelClass       model class
	 * @param modifyColumn     修改的列
	 * @param modifyValue      修改后的值
	 * @param primaryKeyValues 唯一主键的值数组
	 * @return 修改模型收集器
	 */
	public static <M extends Modelable> ModifyModelCollector<M> modifySingleColumnByOnlyPrimaryKeyContains(
			Class<M> modelClass, String modifyColumn, Object modifyValue, Object[] primaryKeyValues) {
		return modifyMultiColumnByOnlyPrimaryKey(modelClass, MapUtilsE.asMap(modifyColumn, modifyValue),
				primaryKeyValues, IN);
	}

	/**
	 * 根据单列等于某值的条件修改单列数据
	 * 
	 * @param <M>                  model type
	 * @param modelClass           model class
	 * @param modifyColumn         修改的列
	 * @param modifyValue          修改后的值
	 * @param conditionColumn      条件列
	 * @param conditionColumnValue 条件值
	 * @return 修改模型收集器
	 */
	public static <M extends Modelable> ModifyModelCollector<M> modifySingleColumnBySingleColumnEQ(Class<M> modelClass,
			String modifyColumn, Object modifyValue, String conditionColumn, Object conditionColumnValue) {
		return modifySingleColumnByCondition(modelClass, modifyColumn, modifyValue,
				new Condition(conditionColumn, EQUAL, conditionColumnValue));
	}

	/**
	 * 根据单列包含某些值的条件修改单列数据
	 * 
	 * @param <M>                   model type
	 * @param modelClass            model class
	 * @param modifyColumn          修改的列
	 * @param modifyValue           修改后的值
	 * @param conditionColumn       条件列
	 * @param conditionColumnValues 条件值
	 * @return 修改模型收集器
	 */
	public static <M extends Modelable> ModifyModelCollector<M> modifySingleColumnBySingleColumnContains(
			Class<M> modelClass, String modifyColumn, Object modifyValue, String conditionColumn,
			Object[] conditionColumnValues) {
		return modifySingleColumnByCondition(modelClass, modifyColumn, modifyValue,
				new Condition(conditionColumn, IN, conditionColumnValues));
	}

	/**
	 * 根据条件修改单列数据
	 * 
	 * @param <M>          model type
	 * @param modelClass   model class
	 * @param modifyColumn 修改的列
	 * @param modifyValue  修改后的值
	 * @param condition    条件
	 * @return 修改模型收集器
	 */
	public static <M extends Modelable> ModifyModelCollector<M> modifySingleColumnByCondition(Class<M> modelClass,
			String modifyColumn, Object modifyValue, Condition condition) {
		return modifySingleColumnByConditions(modelClass, modifyColumn, modifyValue, Arrays.asList(condition));
	}

	/**
	 * 根据多条件修改单列数据
	 * 
	 * @param <M>          model type
	 * @param modelClass   model class
	 * @param modifyColumn 修改的列
	 * @param modifyValue  修改后的值
	 * @param conditions   条件集合
	 * @return 修改模型收集器
	 */
	public static <M extends Modelable> ModifyModelCollector<M> modifySingleColumnByConditions(Class<M> modelClass,
			String modifyColumn, Object modifyValue, List<Condition> conditions) {
		return modifySingleColumnBySqlModel(modelClass, modifyColumn, modifyValue,
				new SqlModel<M>(modelClass).addConditions(conditions));
	}

	/**
	 * 根据 SqlModel<M> 修改单列数据
	 * 
	 * @param <M>          model type
	 * @param modelClass   model class
	 * @param modifyColumn 修改的列
	 * @param modifyValue  修改后的值
	 * @param sqlModel     sqlModel
	 * @return 修改模型收集器
	 */
	public static <M extends Modelable> ModifyModelCollector<M> modifySingleColumnBySqlModel(Class<M> modelClass,
			String modifyColumn, Object modifyValue, SqlModel<M> sqlModel) {
		return modifyMultiColumnBySqlModel(modelClass, MapUtilsE.asMap(modifyColumn, modifyValue), sqlModel);
	}

	// ==================================================modify-multi-column==================================================

	/**
	 * 根据唯一主键等于某值的条件修改多列数据
	 * 
	 * @param <M>              model type
	 * @param modelClass       model class
	 * @param modifyAttributes 修改的列值映射
	 * @param primaryKeyValue  唯一主键的值
	 * @return 修改模型收集器
	 */
	public static <M extends Modelable> ModifyModelCollector<M> modifyMultiColumnByOnlyPrimaryKeyEQ(Class<M> modelClass,
			Map<String, Object> modifyAttributes, Object primaryKeyValue) {
		return modifyMultiColumnByOnlyPrimaryKey(modelClass, modifyAttributes, primaryKeyValue, EQUAL);
	}

	/**
	 * 根据唯一主键包含某些值的条件修改多列数据
	 * 
	 * @param <M>              model type
	 * @param modelClass       model class
	 * @param modifyAttributes 修改的列值映射
	 * @param primaryKeyValues 唯一主键的值数组
	 * @return 修改模型收集器
	 */
	public static <M extends Modelable> ModifyModelCollector<M> modifyMultiColumnByOnlyPrimaryKeyContains(
			Class<M> modelClass, Map<String, Object> modifyAttributes, Object[] primaryKeyValues) {
		return modifyMultiColumnByOnlyPrimaryKey(modelClass, modifyAttributes, primaryKeyValues, IN);
	}

	/**
	 * 根据唯一主键条件修改多列数据
	 * 
	 * @param <M>                 model type
	 * @param modelClass          model class
	 * @param modifyAttributes    修改的列值映射
	 * @param primaryKeyValue     唯一主键的值
	 * @param conditionalOperator 唯一主键的条件预算符
	 * @return 修改模型收集器
	 */
	private static <M extends Modelable> ModifyModelCollector<M> modifyMultiColumnByOnlyPrimaryKey(Class<M> modelClass,
			Map<String, Object> modifyAttributes, Object primaryKeyValue, ConditionalOperator conditionalOperator) {
		return new AbstractModifyModelCollector<M>(modelClass) {
			protected Integer doCollect(SqlModelService modelService) {
				FieldAndColumn fieldAndColumn = modelService.getModelConfiguration().getModelManager()
						.getModelAndTable(modelClass).getOnlyPrimaryKey();
				return modifyMultiColumnByCondition(modelClass, modifyAttributes,
						new Condition(fieldAndColumn.getColumn(), conditionalOperator, primaryKeyValue))
								.collect(modelService);
			};
		};
	}

	/**
	 * 根据单列等于值的条件修改多列数据
	 * 
	 * @param <M>                  model type
	 * @param modelClass           model class
	 * @param modifyAttributes     修改的列值映射
	 * @param conditionColumn      条件列
	 * @param conditionColumnValue 条件值
	 * @return 修改模型收集器
	 */
	public static <M extends Modelable> ModifyModelCollector<M> modifyMultiColumnBySingleColumnEQ(Class<M> modelClass,
			Map<String, Object> modifyAttributes, String conditionColumn, Object conditionColumnValue) {
		return modifyMultiColumnByCondition(modelClass, modifyAttributes,
				new Condition(conditionColumn, EQUAL, conditionColumnValue));
	}

	/**
	 * 根据单列包含某些值的条件修改多列数据
	 * 
	 * @param <M>                   model type
	 * @param modelClass            model class
	 * @param modifyAttributes      修改的列值映射
	 * @param conditionColumn       条件列
	 * @param conditionColumnValues 条件值
	 * @return 修改模型收集器
	 */
	public static <M extends Modelable> ModifyModelCollector<M> modifyMultiColumnBySingleColumnContains(
			Class<M> modelClass, Map<String, Object> modifyAttributes, String conditionColumn,
			Object[] conditionColumnValues) {
		return modifyMultiColumnByCondition(modelClass, modifyAttributes,
				new Condition(conditionColumn, IN, conditionColumnValues));
	}

	/**
	 * 根据条件修改多列数据
	 * 
	 * @param <M>              model type
	 * @param modelClass       model class
	 * @param modifyAttributes 修改的列值映射
	 * @param condition        条件
	 * @return 修改模型收集器
	 */
	public static <M extends Modelable> ModifyModelCollector<M> modifyMultiColumnByCondition(Class<M> modelClass,
			Map<String, Object> modifyAttributes, Condition condition) {
		return modifyMultiColumnByConditions(modelClass, modifyAttributes, Arrays.asList(condition));
	}

	/**
	 * 根据多条件修改单列数据
	 * 
	 * @param <M>              model type
	 * @param modelClass       model class
	 * @param modifyAttributes 修改的列值映射
	 * @param conditions       条件集合
	 * @return 修改模型收集器
	 */
	public static <M extends Modelable> ModifyModelCollector<M> modifyMultiColumnByConditions(Class<M> modelClass,
			Map<String, Object> modifyAttributes, List<Condition> conditions) {
		return modifyMultiColumnBySqlModel(modelClass, modifyAttributes,
				new SqlModel<M>(modelClass).addConditions(conditions));
	}

	/**
	 * 根据 SqlModel的条件部分修改多列数据
	 * 
	 * @param <M>              model type
	 * @param modelClass       model class
	 * @param modifyAttributes 修改的列值映射
	 * @param sqlModel         sqlModel
	 * @return 修改模型收集器
	 */
	public static <M extends Modelable> ModifyModelCollector<M> modifyMultiColumnBySqlModel(Class<M> modelClass,
			Map<String, Object> modifyAttributes, SqlModel<M> sqlModel) {
		return new AbstractModifyModelCollector<M>(modelClass) {
			@Override
			protected Integer doCollect(SqlModelService modelService) {
				ModelConfiguration modelConfiguration = modelService.getModelConfiguration();

				ModelSqlFragmentFactory modelSqlFragmentFactory = modelConfiguration.getModelSqlFragmentFactory();
				SqlModelResolver sqlModelResolver = modelConfiguration.getSqlModelResolver();

				AttributeSqlFragment attributeSqlFragment = modelSqlFragmentFactory.createAttributeSqlFragment();
				modifyAttributes.forEach(attributeSqlFragment::addAttr);
				attributeSqlFragment.setDataBaseOperationType(DataBaseOperationType.UPDATE);

				UpdateSqlFragment updateSqlFragment = modelSqlFragmentFactory.createUpdateSqlFragment(modelClass,
						attributeSqlFragment);
				ConditionSqlFragment conditionSqlFragment = sqlModelResolver.resolveToConditionSqlFragment(sqlModel,
						false);
				if (null != conditionSqlFragment) {
					updateSqlFragment.setConditionSqlFragment(conditionSqlFragment);
				}
				return modelService.execute(updateSqlFragment);
			}
		};
	}

	// ==================================================getSingleValue==================================================

	/**
	 * 根据唯一主键等于某值的条件查询单列的第一个值
	 * 
	 * @param <M>             model type
	 * @param <T>             result type
	 * @param modelClass      model class
	 * @param selectColumn    查询的列
	 * @param primaryKeyValue 唯一主键值
	 * @return
	 */
	public static <M extends Modelable, T> GetSingleColumnModelCollector<M, T> getSingleValueByOnlyPrimaryKeyEQ(
			Class<M> modelClass, String selectColumn, Object primaryKeyValue) {
		return new AbstractGetSingleColumnModelCollector<M, T>(modelClass, selectColumn) {
			@Override
			protected T doCollect(SqlModelService modelService) {
				FieldAndColumn fieldAndColumn = modelService.getModelConfiguration().getModelManager()
						.getModelAndTable(modelClass).getOnlyPrimaryKey();
				SqlModel<M> sqlModel = new SqlModel<M>(modelClass)
						.addCondition(ConditionFactory.equal(fieldAndColumn.getColumn(), primaryKeyValue));
				return modelService.findFirstSingleColumnBySqlModel(modelClass, selectColumn, sqlModel);
			}
		};
	}

	/**
	 * 根据单列等于某值的条件获取查询列的第一个值
	 * 
	 * @param <M>                  model type
	 * @param <T>                  result type
	 * @param modelClass           model class
	 * @param selectColumn         查询的列
	 * @param conditionColumn      条件列
	 * @param conditionColumnValue 条件列的值
	 * @return 获取单个值模型收集器
	 */
	public static <M extends Modelable, T> GetSingleColumnModelCollector<M, T> getSingleValueBySingleColumnEQ(
			Class<M> modelClass, String selectColumn, String conditionColumn, Object conditionColumnValue) {
		return getSingleValueByCondition(modelClass, selectColumn,
				new Condition(conditionColumn, EQUAL, conditionColumnValue));
	}

	/**
	 * 根据指定的条件获取查询列的第一个值
	 * 
	 * @param <M>          model type
	 * @param <T>          result type
	 * @param modelClass   model class
	 * @param selectColumn 查询的列
	 * @param condition    条件
	 * @return 获取单个值模型收集器
	 */
	public static <M extends Modelable, T> GetSingleColumnModelCollector<M, T> getSingleValueByCondition(
			Class<M> modelClass, String selectColumn, Condition condition) {
		return getSingleValueByConditionSort(modelClass, selectColumn, condition, null);
	}

	/**
	 * 根据指定的条件和排序获取查询列的第一个值
	 * 
	 * @param <M>          model type
	 * @param <T>          result type
	 * @param modelClass   model class
	 * @param selectColumn 查询的列
	 * @param condition    条件
	 * @param sort         排序
	 * @return 获取单个值模型收集器
	 */
	public static <M extends Modelable, T> GetSingleColumnModelCollector<M, T> getSingleValueByConditionSort(
			Class<M> modelClass, String selectColumn, Condition condition, @Nullable Sort sort) {
		return getSingleValueByConditionSort(modelClass, selectColumn, asList(condition), asList(sort));
	}

	/**
	 * 根据指定的条件和排序获取查询列的第一个值
	 * 
	 * @param <M>          model type
	 * @param <T>          result type
	 * @param modelClass   model class
	 * @param selectColumn 查询的列
	 * @param conditions   条件
	 * @param sorts        排序
	 * @return 获取单个值模型收集器
	 */
	public static <M extends Modelable, T> GetSingleColumnModelCollector<M, T> getSingleValueByConditionSort(
			Class<M> modelClass, String selectColumn, List<Condition> conditions, @Nullable List<Sort> sorts) {
		List<Condition> afterRemoveConditions = removeBySingleNullOrBlankValue(conditions);
		if (CollectionUtils.isEmpty(afterRemoveConditions)) {
			return new GetSingleColumnNullModelCollector<M, T>(modelClass, selectColumn);
		}
		SqlModel<M> sqlModel = new SqlModel<M>(modelClass);
		sqlModel.addConditions(conditions);
		if (null != sorts) {
			sqlModel.addSorts(sorts);
		}
		return getSingleValueBySqlModel(modelClass, selectColumn, sqlModel);
	}

	/**
	 * 根据 SqlModel 获取查询列的第一个值
	 * 
	 * @param <M>          model type
	 * @param <T>          result type
	 * @param modelClass   model class
	 * @param selectColumn 查询的列
	 * @param sqlModel     sqlModel
	 * @return 获取单个值模型收集器
	 */
	public static <M extends Modelable, T> GetSingleColumnModelCollector<M, T> getSingleValueBySqlModel(
			Class<M> modelClass, String selectColumn, SqlModel<M> sqlModel) {
		return new AbstractGetSingleColumnModelCollector<M, T>(modelClass, selectColumn) {
			@Override
			protected T doCollect(SqlModelService modelService) {
				return modelService.findFirstSingleColumnBySqlModel(modelClass, selectColumn, sqlModel);
			}
		};
	}

	// ==================================================getModel==================================================

	/**
	 * 根据唯一主键列等于某值的条件查询模型记录
	 * 
	 * @param <M>             model type
	 * @param modelClass      model class
	 * @param primaryKeyValue 唯一主键列
	 * @return 获取模型收集器
	 */
	public static <M extends Modelable> GetModelCollector<M> getModelByOnlyPrimaryKeyEQ(Class<M> modelClass,
			Object primaryKeyValue) {
		if (null == primaryKeyValue) {
			return new GetNullModelCollector<M>(modelClass);
		}
		return new AbstractGetModelCollector<M>(modelClass) {
			@Override
			protected M doCollect(SqlModelService modelService) {
				ModelAndTable modelAndTable = modelService.getModelConfiguration().getModelManager()
						.getModelAndTable(modelClass);
				FieldAndColumn fieldAndColumn = modelAndTable.getOnlyPrimaryKey();
				SqlModel<M> sqlModel = new SqlModel<M>(modelClass)
						.addCondition(new Condition(fieldAndColumn.getColumn(), EQUAL, primaryKeyValue));
				return modelService.findFirstBySqlModel(modelClass, sqlModel);
			}
		};
	}

	/**
	 * 根据单例等于某值的条件获取第一个模型
	 * 
	 * <pre>
	 * User user = modelService.collect(ModelCollectors.getModelBySingleColumnEQ(User.class, "userName", "夜龙"));
	 * </pre>
	 * 
	 * @param <M>                  model type
	 * @param modelClass           model class
	 * @param conditionColumn      条件列
	 * @param conditionColumnValue 条件列值
	 * @return 获取模型收集器
	 */
	public static <M extends Modelable> GetModelCollector<M> getModelBySingleColumnEQ(Class<M> modelClass,
			String conditionColumn, Object conditionColumnValue) {
		return getModelByCondition(modelClass, new Condition(conditionColumn, EQUAL, conditionColumnValue));
	}

	/**
	 * 根据指定的条件获取查询到的第一个模型
	 * 
	 * @param <M>        model type
	 * @param modelClass model class
	 * @param condition  条件
	 * @return 获取模型收集器
	 */
	public static <M extends Modelable> GetModelCollector<M> getModelByCondition(Class<M> modelClass,
			Condition condition) {
		return getModelByConditionSort(modelClass, condition, null);
	}

	/**
	 * 根据指定的条件和排序获取查询到的第一个模型
	 * 
	 * @param <M>        model type
	 * @param modelClass model class
	 * @param condition  条件
	 * @param sort       排序
	 * @return 获取模型收集器
	 */
	public static <M extends Modelable> GetModelCollector<M> getModelByConditionSort(Class<M> modelClass,
			Condition condition, @Nullable Sort sort) {
		return getModelByConditionsSorts(modelClass, asList(condition), asList(sort));
	}

	/**
	 * 根据指定的条件和排序获取查询到的第一个模型
	 * 
	 * @param <M>        model type
	 * @param modelClass model class
	 * @param conditions 条件
	 * @param sorts      排序
	 * @return 获取模型收集器
	 */
	public static <M extends Modelable> GetModelCollector<M> getModelByConditionsSorts(Class<M> modelClass,
			List<Condition> conditions, @Nullable List<Sort> sorts) {
		List<Condition> afterRemoveConditions = removeBySingleNullOrBlankValue(conditions);
		if (CollectionUtils.isEmpty(afterRemoveConditions)) {
			return new GetNullModelCollector<M>(modelClass);
		}
		SqlModel<M> sqlModel = new SqlModel<M>(modelClass).addConditions(conditions);
		if (null != sorts) {
			sqlModel.addSorts(sorts);
		}
		return getModelBySqlModel(modelClass, sqlModel);
	}

	/**
	 * 根据 SqlModel获取查询到的第一个模型
	 * 
	 * @param <M>        model type
	 * @param modelClass model class
	 * @param sqlModel   sqlModel
	 * @return 获取模型收集器
	 */
	public static <M extends Modelable> GetModelCollector<M> getModelBySqlModel(Class<M> modelClass,
			SqlModel<M> sqlModel) {
		return new AbstractGetModelCollector<M>(modelClass) {
			@Override
			protected M doCollect(SqlModelService modelService) {
				return modelService.findFirstBySqlModel(modelClass, sqlModel);
			}
		};
	}

	// ==================================================find==================================================

	/**
	 * 获取模型的所有记录
	 * 
	 * @param <M>        model type
	 * @param modelClass model class
	 * @return 查询模型收集器
	 */
	public static <M extends Modelable> FindModelCollector<M> findAll(Class<M> modelClass) {
		return new AbstractFindModelCollector<M>(modelClass) {
			@Override
			protected List<M> doCollect(SqlModelService modelService) {
				return modelService.findBySqlFragment(modelClass, null, null);
			}
		};
	}

	/**
	 * 根据唯一主键包含某些值的条件查询模型记录
	 * 
	 * <pre>
	 * List users = modelService
	 * 		.collect(ModelCollectors.findByOnlyPrimaryKeyContains(User.class, ArrayUtils.toArray("AAA", "BBB")));
	 * </pre>
	 * 
	 * @param <M>              model type
	 * @param modelClass       model class
	 * @param primaryKeyValues 唯一主键值数组
	 * @return 查询模型收集器
	 */
	public static <M extends Modelable> FindModelCollector<M> findByOnlyPrimaryKeyContains(Class<M> modelClass,
			Object[] primaryKeyValues) {
		return findByOnlyPrimaryKeyContains(modelClass, primaryKeyValues, asList(null));
	}

	/**
	 * 根据唯一主键包含某些值的条件和排序查询模型记录
	 * 
	 * <pre>
	 * List users = modelService.collect(ModelCollectors.findByOnlyPrimaryKeyContains(User.class,
	 * 		ArrayUtils.toArray("AAA", "BBB"), new Sort("age", "DESC")));
	 * </pre>
	 * 
	 * @param <M>              model type
	 * @param modelClass       model class
	 * @param primaryKeyValues 唯一主键值数组
	 * @param sort             排序
	 * @return 查询模型收集器
	 */
	public static <M extends Modelable> FindModelCollector<M> findByOnlyPrimaryKeyContains(Class<M> modelClass,
			Object[] primaryKeyValues, @Nullable Sort sort) {
		return findByOnlyPrimaryKeyContains(modelClass, primaryKeyValues, asList(sort));
	}

	/**
	 * 根据唯一主键包含某些值的条件和多个排序查询模型记录
	 * 
	 * <pre>
	 * Sort ageSort = new Sort("age", "DESC");
	 * Sort createTimeSort = new Sort("createTime", "DESC");
	 * List users = modelService.collect(ModelCollectors.findByOnlyPrimaryKeyContains(User.class,
	 * 		ArrayUtils.toArray("AAA", "BBB"), Arrays.asList(ageSort, createTimeSort)));
	 * </pre>
	 * 
	 * @param <M>              model type
	 * @param modelClass       model class
	 * @param primaryKeyValues 唯一主键值数组
	 * @param sorts            排序集合
	 * @return 查询模型收集器
	 */
	public static <M extends Modelable> FindModelCollector<M> findByOnlyPrimaryKeyContains(Class<M> modelClass,
			final @Nullable Object[] primaryKeyValues, @Nullable List<Sort> sorts) {
		if (ArrayUtils.isEmpty(primaryKeyValues)) {
			return new FindEmptyModelCollector<M>(modelClass);
		}
		return new AbstractFindModelCollector<M>(modelClass) {
			@Override
			protected List<M> doCollect(SqlModelService modelService) {
				ModelAndTable modelAndTable = modelService.getModelConfiguration().getModelManager()
						.getModelAndTable(modelClass);
				FieldAndColumn fieldAndColumn = modelAndTable.getOnlyPrimaryKey();
				SqlModel<M> sqlModel = new SqlModel<M>(modelClass)
						.addCondition(new Condition(fieldAndColumn.getColumn(), IN, primaryKeyValues));
				if (CollectionUtils.isNotEmpty(sorts)) {
					sqlModel.addSorts(sorts);
				}
				return modelService.findBySqlModel(modelClass, sqlModel);
			}
		};
	}

	/**
	 * 根据单列等于某值的条件查询模型记录
	 * 
	 * <pre>
	 * List users = modelService.collect(ModelCollectors.findBySingleColumnEQ(User.class, "userName", "pengFei"));
	 * </pre>
	 * 
	 * @param <M>                  model type
	 * @param modelClass           model class
	 * @param conditionColumn      条件列
	 * @param conditionColumnValue 条件列的值
	 * @return 查询模型收集器
	 */
	public static <M extends Modelable> FindModelCollector<M> findBySingleColumnEQ(Class<M> modelClass,
			String conditionColumn, Object conditionColumnValue) {
		return findBySingleColumnEQ(modelClass, conditionColumn, conditionColumnValue, asList(null));
	}

	/**
	 * 根据单列等于某值的条件和排序查询模型记录
	 * 
	 * <pre>
	 * List users = modelService.collect(
	 * 		ModelCollectors.findBySingleColumnEQ(User.class, "userName", "pengFei", new Sort("age", "DESC")));
	 * </pre>
	 * 
	 * @param <M>                  model type
	 * @param modelClass           model class
	 * @param conditionColumn      条件列
	 * @param conditionColumnValue 条件列的值
	 * @param sort                 排序
	 * @return 查询模型收集器
	 */
	public static <M extends Modelable> FindModelCollector<M> findBySingleColumnEQ(Class<M> modelClass,
			String conditionColumn, Object conditionColumnValue, Sort sort) {
		return findBySingleColumnEQ(modelClass, conditionColumn, conditionColumnValue, asList(sort));
	}

	/**
	 * 根据单列等于某值的条件和多个排序查询模型记录
	 * 
	 * <pre>
	 * Sort ageSort = new Sort("age", "DESC");
	 * Sort createTimeSort = new Sort("createTime", "DESC");
	 * List users = modelService.collect(ModelCollectors.findBySingleColumnEQ(User.class, "userName", "pengFei",
	 * 		Arrays.asList(ageSort, createTimeSort)));
	 * </pre>
	 * 
	 * @param <M>                  model type
	 * @param modelClass           model class
	 * @param conditionColumn      条件列
	 * @param conditionColumnValue 条件列的值
	 * @param sorts                排序集合
	 * @return 查询模型收集器
	 */
	public static <M extends Modelable> FindModelCollector<M> findBySingleColumnEQ(Class<M> modelClass,
			String conditionColumn, Object conditionColumnValue, List<Sort> sorts) {
		return findByConditionsSorts(modelClass, asList(new Condition(conditionColumn, EQUAL, conditionColumnValue)),
				sorts);
	}

	/**
	 * 根据单列包含某些值的条件查询模型记录<br/>
	 * 
	 * <pre>
	 * List users = modelService.collect(ModelCollectors.findBySingleColumnContains(User.class, "userName",
	 * 		ArrayUtils.toArray("PengFei", "YeLong")));
	 * </pre>
	 * 
	 * @param <M>                   model type
	 * @param modelClass            model class
	 * @param conditionColumn       条件列
	 * @param conditionColumnValues 条件列的值数组
	 * @return 查询模型收集器
	 */
	public static <M extends Modelable> FindModelCollector<M> findBySingleColumnContains(Class<M> modelClass,
			String conditionColumn, Object[] conditionColumnValues) {
		return findBySingleColumnContains(modelClass, conditionColumn, conditionColumnValues, asList(null));
	}

	/**
	 * 根据单列包含某些值的条件和排序查询模型记录<br/>
	 * 
	 * <pre>
	 * List users = modelService.collect(ModelCollectors.findBySingleColumnContains(User.class, "userName",
	 * 		ArrayUtils.toArray("PengFei", "YeLong"), new Sort("age", "DESC")));
	 * </pre>
	 * 
	 * @param <M>                   model type
	 * @param modelClass            model class
	 * @param conditionColumn       条件列
	 * @param conditionColumnValues 条件列的值数组
	 * @param sort                  排序
	 * @return 查询模型收集器
	 */
	public static <M extends Modelable> FindModelCollector<M> findBySingleColumnContains(Class<M> modelClass,
			String conditionColumn, Object[] conditionColumnValues, Sort sort) {
		return findBySingleColumnContains(modelClass, conditionColumn, conditionColumnValues, asList(sort));
	}

	/**
	 * 根据单列包含某些值的条件和多个排序查询模型记录
	 * 
	 * <pre>
	 * Sort ageSort = new Sort("age", "DESC");
	 * Sort createTimeSort = new Sort("createTime", "DESC");
	 * List users = modelService.collect(ModelCollectors.findBySingleColumnContains(User.class, "userName",
	 * 		ArrayUtils.toArray("PengFei", "YeLong"), Arrays.asList(ageSort, createTimeSort)));
	 * </pre>
	 * 
	 * @param <M>                   model type
	 * @param modelClass            model class
	 * @param conditionColumn       条件列
	 * @param conditionColumnValues 条件列的值数组
	 * @param sorts                 排序集合
	 * @return 查询模型收集器
	 */
	public static <M extends Modelable> FindModelCollector<M> findBySingleColumnContains(Class<M> modelClass,
			String conditionColumn, Object[] conditionColumnValues, List<Sort> sorts) {
		return findByConditionsSorts(modelClass, asList(new Condition(conditionColumn, IN, conditionColumnValues)),
				sorts);
	}

	/**
	 * 根据指定的条件查询模型记录
	 * 
	 * <pre>
	 * List users = modelService
	 * 		.collect(ModelCollectors.findByCondition(User.class, new Condition("userName", "=", "PengFei")));
	 * </pre>
	 * 
	 * @param <M>        model type
	 * @param modelClass model class
	 * @param condition  条件
	 * @return 查询模型收集器
	 */
	public static <M extends Modelable> FindModelCollector<M> findByCondition(Class<M> modelClass,
			Condition condition) {
		return findByConditionSort(modelClass, condition, null);
	}

	/**
	 * 根据指定条件和排序进行查询模型记录
	 * 
	 * <pre>
	 * Condition condition = new Condition("userName", ConditionalOperator.NOT_LIKE, "P%");
	 * Sort sort = new Sort("createTime", "DESC");
	 * List users = modelService.collect(ModelCollectors.findByConditionSort(User.class, condition, sort));
	 * </pre>
	 * 
	 * @param <M>        model type
	 * @param modelClass model class
	 * @param condition  条件
	 * @param sort       排序
	 * @return 查询模型收集器
	 */
	public static <M extends Modelable> FindModelCollector<M> findByConditionSort(Class<M> modelClass,
			Condition condition, @Nullable Sort sort) {
		return findByConditionsSorts(modelClass, asList(condition), asList(sort));
	}

	/**
	 * 根据多个条件和多个排序进行查询模型记录
	 * 
	 * <pre>
	 * Condition condition = new Condition("userName", ConditionalOperator.NOT_LIKE, "P%");
	 * Condition condition1 = new Condition("age", ConditionalOperator.GREATER_THAN, 18);
	 * Sort sort = new Sort("createTime", "DESC");
	 * Sort sort1 = new Sort("updateTime", "ASC");
	 * List users = modelService.collect(ModelCollectors.findByConditionsSorts(User.class,
	 * 		Arrays.asList(condition, condition1), Arrays.asList(sort, sort1)));
	 * </pre>
	 * 
	 * @param <M>        model type
	 * @param modelClass model class
	 * @param conditions 条件集合
	 * @param sorts      排序集合
	 * @return 查询模型收集器
	 */
	public static <M extends Modelable> FindModelCollector<M> findByConditionsSorts(Class<M> modelClass,
			List<Condition> conditions, @Nullable List<Sort> sorts) {
		List<Condition> removeAfterConditions = removeBySingleNullOrBlankValue(conditions);
		if (CollectionUtils.isEmpty(removeAfterConditions)) {
			return new FindEmptyModelCollector<M>(modelClass);
		}
		SqlModel<M> sqlModel = new SqlModel<M>(modelClass).addConditions(conditions);
		if (null != sorts) {
			sqlModel.addSorts(sorts);
		}
		return findBySqlModel(modelClass, sqlModel);
	}

	/**
	 * 根据 sqlModel 进行查询
	 * 
	 * @param <M>        model type
	 * @param modelClass model class
	 * @param sqlModel   sqlModel
	 * @return 查询模型收集器
	 */
	public static <M extends Modelable> FindModelCollector<M> findBySqlModel(Class<M> modelClass,
			SqlModel<M> sqlModel) {
		return new AbstractFindModelCollector<M>(modelClass) {
			@Override
			protected List<M> doCollect(SqlModelService modelService) {
				return modelService.findBySqlModel(modelClass, sqlModel);
			}
		};
	}

	// ==================================================find-single-column==================================================

	/**
	 * 查询单列的所有记录
	 * 
	 * <pre>
	 * List<String> usernames = modelService.collect(ModelCollectors.findSingleColumnAll(User.class, "username"));
	 * </pre>
	 * 
	 * @param <M>          model type
	 * @param <T>          return list object type
	 * @param modelClass   model class
	 * @param selectColumn 查询的列
	 * @return 查询单列收集器
	 */
	public static <M extends Modelable, T> FindSingleColumnModelCollector<M, T> findSingleColumnAll(Class<M> modelClass,
			String selectColumn) {
		return new AbstractFindSingleColumnModelCollector<M, T>(modelClass, selectColumn) {
			@Override
			protected List<T> doCollect(SqlModelService modelService) {
				return modelService.findSingleColumnBySqlFragment(modelClass, selectColumn, null, null);
			}
		};
	}

	/**
	 * 根据唯一主键包含某些值的条件查询单例数据
	 * 
	 * <pre>
	 * List<String> usernames = modelService.collect(ModelCollectors
	 * 		.findSingleColumnByOnlyPrimaryKeyContains(User.class, "username", ArrayUtils.toArray("1")));
	 * </pre>
	 * 
	 * @param <M>              model type
	 * @param <T>              return list object type
	 * @param modelClass       model class
	 * @param selectColumn     查询的列
	 * @param primaryKeyValues 主键值数组
	 * @return 查询单列收集器
	 */
	public static <M extends Modelable, T> FindSingleColumnModelCollector<M, T> findSingleColumnByOnlyPrimaryKeyContains(
			Class<M> modelClass, String selectColumn, Object[] primaryKeyValues) {
		return findSingleColumnByOnlyPrimaryKeyContains(modelClass, selectColumn, primaryKeyValues, asList(null));
	}

	/**
	 * 根据唯一主键包含某些值的条件排序查询单例数据
	 * 
	 * @param <M>              model type
	 * @param <T>              return list object type
	 * @param modelClass       model class
	 * @param selectColumn     查询的列
	 * @param primaryKeyValues 主键值数组
	 * @param sort             排序
	 * @return 查询单列收集器
	 */
	public static <M extends Modelable, T> FindSingleColumnModelCollector<M, T> findSingleColumnByOnlyPrimaryKeyContains(
			Class<M> modelClass, String selectColumn, Object[] primaryKeyValues, @Nullable Sort sort) {
		return findSingleColumnByOnlyPrimaryKeyContains(modelClass, selectColumn, primaryKeyValues, asList(sort));
	}

	/**
	 * 根据唯一主键包含某些值的条件排序查询单例数据
	 * 
	 * @param <M>              model type
	 * @param <T>              return list object type
	 * @param modelClass       model class
	 * @param selectColumn     查询的列
	 * @param primaryKeyValues 主键值数组
	 * @param sorts            排序集合
	 * @return 查询单列收集器
	 */
	public static <M extends Modelable, T> FindSingleColumnModelCollector<M, T> findSingleColumnByOnlyPrimaryKeyContains(
			Class<M> modelClass, String selectColumn, final @Nullable Object[] primaryKeyValues,
			@Nullable List<Sort> sorts) {
		if (ArrayUtils.isEmpty(primaryKeyValues)) {
			return new FindEmptySingleColumnModelCollector<M, T>(modelClass, selectColumn);
		}
		return new AbstractFindSingleColumnModelCollector<M, T>(modelClass, selectColumn) {
			@Override
			protected List<T> doCollect(SqlModelService modelService) {
				ModelAndTable modelAndTable = modelService.getModelConfiguration().getModelManager()
						.getModelAndTable(modelClass);
				FieldAndColumn fieldAndColumn = modelAndTable.getOnlyPrimaryKey();
				SqlModel<M> sqlModel = new SqlModel<M>(modelClass)
						.addCondition(new Condition(fieldAndColumn.getColumn(), IN, primaryKeyValues));
				if (CollectionUtils.isNotEmpty(sorts)) {
					sqlModel.addSorts(sorts);
				}
				return modelService.findSingleColumnBySqlModel(modelClass, selectColumn, sqlModel);
			}
		};
	}

	/**
	 * 根据单列等于某值的条件查询单列数据
	 * 
	 * <pre>
	 * List<String> usernames = modelService
	 * 		.collect(ModelCollectors.findSingleColumnBySingleColumnEQ(User.class, "id", "username", "pengfei"));
	 * </pre>
	 * 
	 * @param <M>                  model type
	 * @param <T>                  return list object type
	 * @param modelClass           model class
	 * @param selectColumn         查询的列
	 * @param conditionColumn      条件列
	 * @param conditionColumnValue 条件列的值
	 * @return 查询单列收集器
	 */
	public static <M extends Modelable, T> FindSingleColumnModelCollector<M, T> findSingleColumnBySingleColumnEQ(
			Class<M> modelClass, String selectColumn, String conditionColumn, Object conditionColumnValue) {
		return findSingleColumnBySingleColumnEQ(modelClass, selectColumn, conditionColumn, conditionColumnValue,
				asList(null));
	}

	/**
	 * 根据单列等于某值的条件排序查询单列数据
	 * 
	 * @param <M>                  model type
	 * @param <T>                  return list object type
	 * @param modelClass           model class
	 * @param selectColumn         查询的列
	 * @param conditionColumn      条件列
	 * @param conditionColumnValue 条件列的值
	 * @param sort                 排序
	 * @return 查询单列收集器
	 */
	public static <M extends Modelable, T> FindSingleColumnModelCollector<M, T> findSingleColumnBySingleColumnEQ(
			Class<M> modelClass, String selectColumn, String conditionColumn, Object conditionColumnValue, Sort sort) {
		return findSingleColumnBySingleColumnEQ(modelClass, selectColumn, conditionColumn, conditionColumnValue,
				asList(sort));
	}

	/**
	 * 根据单列等于某值的条件排序查询单列数据
	 * 
	 * @param <M>                  model type
	 * @param <T>                  return list object type
	 * @param modelClass           model class
	 * @param selectColumn         查询的列
	 * @param conditionColumn      条件列
	 * @param conditionColumnValue 条件列的值
	 * @param sorts                排序集合
	 * @return 查询单列收集器
	 */
	public static <M extends Modelable, T> FindSingleColumnModelCollector<M, T> findSingleColumnBySingleColumnEQ(
			Class<M> modelClass, String selectColumn, String conditionColumn, Object conditionColumnValue,
			List<Sort> sorts) {
		return findSingleColumnByConditionsSorts(modelClass, selectColumn,
				asList(new Condition(conditionColumn, EQUAL, conditionColumnValue)), sorts);
	}

	/**
	 * 根据单列包含某些值的条件查询单列数据
	 * 
	 * <pre>
	 * List<String> usernames = modelService.collect(ModelCollectors.findSingleColumnBySingleColumnContains(User.class,
	 * 		"username", "username", ArrayUtils.toArray("pengfei", "yelong")));
	 * </pre>
	 * 
	 * @param <M>                   model type
	 * @param <T>                   return list object type
	 * @param modelClass            model class
	 * @param selectColumn          查询的列
	 * @param conditionColumn       条件列
	 * @param conditionColumnValues 条件值数组
	 * @return 查询单列收集器
	 */
	public static <M extends Modelable, T> FindSingleColumnModelCollector<M, T> findSingleColumnBySingleColumnContains(
			Class<M> modelClass, String selectColumn, String conditionColumn, Object[] conditionColumnValues) {
		return findSingleColumnBySingleColumnContains(modelClass, selectColumn, conditionColumn, conditionColumnValues,
				asList(null));
	}

	/**
	 * 根据单列包含某些值的条件排序查询单列数据
	 * 
	 * @param <M>                   model type
	 * @param <T>                   return list object type
	 * @param modelClass            model class
	 * @param selectColumn          查询的列
	 * @param conditionColumn       条件列
	 * @param conditionColumnValues 条件值数组
	 * @param sort                  排序
	 * @return 查询单列收集器
	 */
	public static <M extends Modelable, T> FindSingleColumnModelCollector<M, T> findSingleColumnBySingleColumnContains(
			Class<M> modelClass, String selectColumn, String conditionColumn, Object[] conditionColumnValues,
			Sort sort) {
		return findSingleColumnBySingleColumnContains(modelClass, selectColumn, conditionColumn, conditionColumnValues,
				asList(sort));
	}

	/**
	 * 根据单列包含某些值的条件排序查询单列数据
	 * 
	 * @param <M>                   model type
	 * @param <T>                   return list object type
	 * @param modelClass            model class
	 * @param selectColumn          查询的列
	 * @param conditionColumn       条件列
	 * @param conditionColumnValues 条件值数组
	 * @param sorts                 排序
	 * @return 查询单列收集器
	 */
	public static <M extends Modelable, T> FindSingleColumnModelCollector<M, T> findSingleColumnBySingleColumnContains(
			Class<M> modelClass, String selectColumn, String conditionColumn, Object[] conditionColumnValues,
			List<Sort> sorts) {
		return findSingleColumnByConditionsSorts(modelClass, selectColumn,
				asList(new Condition(conditionColumn, IN, conditionColumnValues)), sorts);
	}

	/**
	 * 根据条件查询单列数据
	 * 
	 * @param <M>          model type
	 * @param <T>          return list object type
	 * @param modelClass   model class
	 * @param selectColumn 查询的列
	 * @param condition    条件
	 * @return 查询单列收集器
	 */
	public static <M extends Modelable, T> FindSingleColumnModelCollector<M, T> findSingleColumnByCondition(
			Class<M> modelClass, String selectColumn, Condition condition) {
		return findSingleColumnByConditionSort(modelClass, selectColumn, condition, null);
	}

	/**
	 * 根据条件排序查询单列数据
	 * 
	 * @param <M>          model type
	 * @param <T>          return list object type
	 * @param modelClass   model class
	 * @param selectColumn 查询的列
	 * @param condition    条件
	 * @param sort         排序
	 * @return 查询单列收集器
	 */
	public static <M extends Modelable, T> FindSingleColumnModelCollector<M, T> findSingleColumnByConditionSort(
			Class<M> modelClass, String selectColumn, Condition condition, @Nullable Sort sort) {
		return findSingleColumnByConditionsSorts(modelClass, selectColumn, asList(condition), asList(sort));
	}

	/**
	 * 根据条件排序查询单列数据
	 * 
	 * @param <M>          model type
	 * @param <T>          return list object type
	 * @param modelClass   model class
	 * @param selectColumn 查询的列
	 * @param conditions   条件集合
	 * @param sorts        排序集合
	 * @return 查询单列收集器
	 */
	public static <M extends Modelable, T> FindSingleColumnModelCollector<M, T> findSingleColumnByConditionsSorts(
			Class<M> modelClass, String selectColumn, List<Condition> conditions, @Nullable List<Sort> sorts) {
		List<Condition> removeAfterConditions = removeBySingleNullOrBlankValue(conditions);
		if (CollectionUtils.isEmpty(removeAfterConditions)) {
			return new FindEmptySingleColumnModelCollector<M, T>(modelClass, selectColumn);
		}
		SqlModel<M> sqlModel = new SqlModel<M>(modelClass).addConditions(conditions);
		if (null != sorts) {
			sqlModel.addSorts(sorts);
		}
		return findSingleColumnBySqlModel(modelClass, selectColumn, sqlModel);
	}

	/**
	 * 根据 sqlModel 查询单列数据
	 * 
	 * @param <M>          model type
	 * @param <T>          return list object type
	 * @param modelClass   model class
	 * @param selectColumn 查询的列
	 * @param sqlModel     sqlModel
	 * @return 查询单列收集器
	 */
	public static <M extends Modelable, T> FindSingleColumnModelCollector<M, T> findSingleColumnBySqlModel(
			Class<M> modelClass, String selectColumn, SqlModel<M> sqlModel) {
		return new AbstractFindSingleColumnModelCollector<M, T>(modelClass, selectColumn) {
			@Override
			protected List<T> doCollect(SqlModelService modelService) {
				return modelService.findSingleColumnBySqlModel(modelClass, selectColumn, sqlModel);
			}
		};
	}

	// ==================================================count==================================================

	/**
	 * 查询模型记录总数
	 * 
	 * @param <M>        model type
	 * @param modelClass model class
	 * @return 查询记录数收集器
	 */
	public static <M extends Modelable> CountModelCollector<M> countAll(Class<M> modelClass) {
		return new AbstractCountModelCollector<M>(modelClass) {
			@Override
			protected Long doCollect(SqlModelService modelService) {
				return modelService.countBySqlFragment(modelClass, null);
			}
		};
	}

	/**
	 * 根据唯一主键列等于某值的条件查询记录总数
	 * 
	 * @param <M>             model type
	 * @param modelClass      model class
	 * @param primaryKeyValue 唯一主键列值
	 * @return 查询记录数收集器
	 */
	public static <M extends Modelable> CountModelCollector<M> countByOnlyPrimaryKeyEQ(Class<M> modelClass,
			Object primaryKeyValue) {
		return countByOnlyPrimaryKeyContains(modelClass, ArrayUtils.toArray(primaryKeyValue));
	}

	/**
	 * 根据唯一主键列包含某些值的条件查询记录总数
	 * 
	 * @param <M>              model type
	 * @param modelClass       model class
	 * @param primaryKeyValues 唯一主键列值数组
	 * @return 查询记录数收集器
	 */
	public static <M extends Modelable> CountModelCollector<M> countByOnlyPrimaryKeyContains(Class<M> modelClass,
			Object[] primaryKeyValues) {
		return new AbstractCountModelCollector<M>(modelClass) {
			@Override
			protected Long doCollect(SqlModelService modelService) {
				ModelAndTable modelAndTable = modelService.getModelConfiguration().getModelManager()
						.getModelAndTable(modelClass);
				FieldAndColumn fieldAndColumn = modelAndTable.getOnlyPrimaryKey();
				Condition condition = new Condition(fieldAndColumn.getColumn(), IN, primaryKeyValues);
				SqlModel<M> sqlModel = new SqlModel<M>(modelClass).addCondition(condition);
				return modelService.countBySqlModel(modelClass, sqlModel);
			}
		};
	}

	/**
	 * 根据单列等于某值的条件查询模型记录数
	 * 
	 * <pre>
	 * Long userNum = modelService.collect(ModelCollectors.countBySingleColumnEQ(User.class, "age", 18));
	 * </pre>
	 * 
	 * @param <M>                  model type
	 * @param modelClass           model class
	 * @param conditionColumn      条件列
	 * @param conditionColumnValue 条件列的值
	 * @return 查询记录模型收集器
	 */
	public static <M extends Modelable> CountModelCollector<M> countBySingleColumnEQ(Class<M> modelClass,
			String conditionColumn, Object conditionColumnValue) {
		return countByCondition(modelClass, new Condition(conditionColumn, EQUAL, conditionColumnValue));
	}

	/**
	 * 根据单列包含某些值的条件查询模型记录数
	 * 
	 * <pre>
	 * Long userNum = modelService.collect(ModelCollectors.countBySingleColumnContains(User.class, "age", [18,19]));
	 * </pre>
	 * 
	 * @param <M>                   model type
	 * @param modelClass            model class
	 * @param conditionColumn       条件列
	 * @param conditionColumnValues 条件列的值数组
	 * @return 查询模型记录模型收集器
	 */
	public static <M extends Modelable> CountModelCollector<M> countBySingleColumnContains(Class<M> modelClass,
			String conditionColumn, Object[] conditionColumnValues) {
		return countByCondition(modelClass, new Condition(conditionColumn, IN, conditionColumnValues));
	}

	/**
	 * 根据指定的条件查询记录数
	 * 
	 * @param <M>        model type
	 * @param modelClass model class
	 * @param condition  条件
	 * @return 查询模型记录数收集器
	 */
	public static <M extends Modelable> CountModelCollector<M> countByCondition(Class<M> modelClass,
			Condition condition) {
		return countByConditions(modelClass, asList(condition));
	}

	/**
	 * 根据指定条件和排序进行查询记录数
	 * 
	 * @param <M>        model type
	 * @param modelClass model class
	 * @param conditions 条件集合
	 * @return 查询模型记录数模型收集器
	 */
	public static <M extends Modelable> CountModelCollector<M> countByConditions(Class<M> modelClass,
			List<Condition> conditions) {
		List<Condition> removeAfterConditions = removeBySingleNullOrBlankValue(conditions);
		if (CollectionUtils.isEmpty(removeAfterConditions)) {
			return new CountEmptyModelCollector<M>(modelClass);
		}
		SqlModel<M> sqlModel = new SqlModel<M>(modelClass);
		sqlModel.addConditions(conditions);
		return countBySqlModel(modelClass, sqlModel);
	}

	/**
	 * 根据 sqlModel 进行查询记录数
	 * 
	 * @param <M>        model type
	 * @param modelClass model class
	 * @param sqlModel   sqlModel
	 * @return 查询记录模型收集器
	 */
	public static <M extends Modelable> CountModelCollector<M> countBySqlModel(Class<M> modelClass,
			SqlModel<M> sqlModel) {
		return new AbstractCountModelCollector<M>(modelClass) {
			@Override
			protected Long doCollect(SqlModelService modelService) {
				return modelService.countBySqlModel(modelClass, sqlModel);
			}
		};
	}

	// ==================================================exist==================================================

	/**
	 * 查询模型对应的表中是否存在数据
	 * 
	 * @param <M>        model type
	 * @param modelClass model class
	 * @return 是否存在模型记录的模型收集器
	 */
	public static <M extends Modelable> ExistModelCollector<M> exist(Class<M> modelClass) {
		return new AbstractExistModelCollector<M>(modelClass) {
			@Override
			protected Boolean doCollect(SqlModelService modelService) {
				return countAll(modelClass).collect(modelService) > 0;
			}
		};
	}

	/**
	 * 根据唯一主键列等于某值的条件查询是否存在记录
	 * 
	 * @param <M>             model type
	 * @param modelClass      model class
	 * @param primaryKeyValue 唯一主键列值
	 * @return 是否存在模型记录的模型收集器
	 */
	public static <M extends Modelable> ExistModelCollector<M> existByOnlyPrimaryKeyEQ(Class<M> modelClass,
			Object primaryKeyValue) {
		return existByOnlyPrimaryKeyContains(modelClass, ArrayUtils.toArray(primaryKeyValue));
	}

	/**
	 * 根据唯一主键列包含某些值的条件查询是否存在记录
	 * 
	 * @param <M>              model type
	 * @param modelClass       model class
	 * @param primaryKeyValues 唯一主键列值数组
	 * @return 是否存在模型记录的模型收集器
	 */
	public static <M extends Modelable> ExistModelCollector<M> existByOnlyPrimaryKeyContains(Class<M> modelClass,
			Object[] primaryKeyValues) {
		return new AbstractExistModelCollector<M>(modelClass) {
			@Override
			protected Boolean doCollect(SqlModelService modelService) {
				return countByOnlyPrimaryKeyContains(modelClass, primaryKeyValues)
						.collect(modelService) > primaryKeyValues.length;
			}
		};
	}

	/**
	 * 根据单列等于某值的条件查询模型记录是否存在
	 * 
	 * <pre>
	 * Boolean exist = modelService.collect(ModelCollectors.existBySingleColumnEQ(User.class, "age", 18));
	 * </pre>
	 * 
	 * @param <M>                  model type
	 * @param modelClass           model class
	 * @param conditionColumn      条件列
	 * @param conditionColumnValue 条件列的值
	 * @return 是否存在模型记录的模型收集器
	 */
	public static <M extends Modelable> ExistModelCollector<M> existBySingleColumnEQ(Class<M> modelClass,
			String conditionColumn, Object conditionColumnValue) {
		return existByCondition(modelClass, new Condition(conditionColumn, EQUAL, conditionColumnValue));
	}

	/**
	 * 根据单列包含某些值的条件查询模型记录是否存在
	 * 
	 * <pre>
	 * Boolean exist = modelService.collect(ModelCollectors.existBySingleColumnContains(User.class, "age", [18,19]));
	 * </pre>
	 * 
	 * @param <M>                   model type
	 * @param modelClass            model class
	 * @param conditionColumn       条件列
	 * @param conditionColumnValues 条件列的值数组
	 * @return 是否存在模型记录的模型收集器
	 */
	public static <M extends Modelable> ExistModelCollector<M> existBySingleColumnContains(Class<M> modelClass,
			String conditionColumn, Object[] conditionColumnValues) {
		return existByCondition(modelClass, new Condition(conditionColumn, IN, conditionColumnValues));
	}

	/**
	 * 根据指定的条件查询记录是否存在
	 * 
	 * @param <M>        model type
	 * @param modelClass model class
	 * @param condition  条件
	 * @return 是否存在模型记录的模型收集器
	 */
	public static <M extends Modelable> ExistModelCollector<M> existByCondition(Class<M> modelClass,
			Condition condition) {
		return existByConditions(modelClass, asList(condition));
	}

	/**
	 * 根据指定条件和排序进行查询记录是否存在
	 * 
	 * @param <M>        model type
	 * @param modelClass model class
	 * @param conditions 条件集合
	 * @return 是否存在模型记录的模型收集器
	 */
	public static <M extends Modelable> ExistModelCollector<M> existByConditions(Class<M> modelClass,
			List<Condition> conditions) {
		List<Condition> removeAfterConditions = removeBySingleNullOrBlankValue(conditions);
		if (CollectionUtils.isEmpty(removeAfterConditions)) {
			return new NotExistModelCollector<M>(modelClass);
		}
		SqlModel<M> sqlModel = new SqlModel<M>(modelClass);
		sqlModel.addConditions(conditions);
		return existBySqlModel(modelClass, sqlModel);
	}

	/**
	 * 根据 sqlModel 进行查询记录是否存在
	 * 
	 * @param <M>        model type
	 * @param modelClass model class
	 * @param sqlModel   sqlModel
	 * @return 是否存在模型记录的模型收集器
	 */
	public static <M extends Modelable> ExistModelCollector<M> existBySqlModel(Class<M> modelClass,
			SqlModel<M> sqlModel) {
		return new AbstractExistModelCollector<M>(modelClass) {
			@Override
			protected Boolean doCollect(SqlModelService modelService) {
				return modelService.countBySqlModel(modelClass, sqlModel) > 0;
			}
		};
	}

	// ==================================================util==================================================

	/**
	 * 将单个对象转换为集合
	 * 
	 * @param <T> item type
	 * @param obj obj
	 * @return list
	 */
	public static final <T> List<T> asList(T obj) {
		if (null == obj) {
			return Collections.emptyList();
		}
		return Arrays.asList(obj);
	}

	/**
	 * 移除条件集合中 值为单一值，且值为null或者为空白字符
	 * 
	 * @param conditions 操作的集合
	 * @return 移除指定条件后的新集合
	 */
	public static List<Condition> removeBySingleNullOrBlankValue(List<Condition> conditions) {
		conditions = new ArrayList<>(conditions);
		conditions.removeIf(x -> {
			if (x.isSingleValue()) {
				return Strings.isNullOrBlank(x.getValue());
			}
			return false;
		});
		return conditions;
	}

}

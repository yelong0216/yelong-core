/**
 * 
 */
package org.yelong.core.model.collector;

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
import org.yelong.core.jdbc.sql.BoundSql;
import org.yelong.core.jdbc.sql.attribute.AttributeSqlFragment;
import org.yelong.core.jdbc.sql.condition.support.Condition;
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
import org.yelong.core.model.collector.support.find.FindModelCollector;
import org.yelong.core.model.collector.support.find.impl.AbstractFindModelCollector;
import org.yelong.core.model.collector.support.find.impl.FindEmptyModelCollector;
import org.yelong.core.model.collector.support.get.GetModelCollector;
import org.yelong.core.model.collector.support.get.GetSingleColumnModelCollector;
import org.yelong.core.model.collector.support.get.impl.AbstractGetModelCollector;
import org.yelong.core.model.collector.support.get.impl.AbstractGetSingleColumnModelCollector;
import org.yelong.core.model.collector.support.get.impl.GetNullModelCollector;
import org.yelong.core.model.collector.support.get.impl.GetSingleColumnNullModelCollector;
import org.yelong.core.model.collector.support.modify.ModifyModelCollector;
import org.yelong.core.model.collector.support.modify.impl.AbstractModifyModelCollector;
import org.yelong.core.model.collector.support.remove.single.condition.RemoveBySingleConditionModelCollector;
import org.yelong.core.model.collector.support.remove.single.condition.RemoveBySingleConditionModelCollectorImpl;
import org.yelong.core.model.collector.support.remove.single.contains.RemoveBySingleColumnContainsModelCollector;
import org.yelong.core.model.collector.support.remove.single.contains.RemoveBySingleColumnContainsModelCollectorImpl;
import org.yelong.core.model.collector.support.save.SaveListModelCollector;
import org.yelong.core.model.collector.support.save.SaveSingleModelCollector;
import org.yelong.core.model.collector.support.save.impl.SaveListModelCollectorImpl;
import org.yelong.core.model.collector.support.save.impl.SaveSingleModelCollectorImpl;
import org.yelong.core.model.resolve.FieldAndColumn;
import org.yelong.core.model.service.SqlModelService;
import org.yelong.core.model.sql.ModelSqlFragmentFactory;
import org.yelong.core.model.sql.SqlModel;
import org.yelong.core.model.sql.SqlModelResolver;

/**
 * 模型收集者
 * 
 * 提供常用的模型收集方法
 * 
 * @author PengFei
 * @since 1.3.0
 */
public final class ModelCollectors {

	private ModelCollectors() {
	}

	// ==================================================save==================================================

	/**
	 * 保存单个模型
	 * 
	 * @param <M>   model type
	 * @param model model
	 * @return 保存模型收集器
	 */
	@SuppressWarnings("unchecked")
	public static <M extends Modelable> SaveSingleModelCollector<M> saveSingle(M model) {
		return saveSingle((Class<M>) model.getClass(), model);
	}

	/**
	 * 保存单个模型
	 * 
	 * @param <M>        model type
	 * @param modelClass model class
	 * @param model      model
	 * @return 保存模型收集器
	 */
	public static <M extends Modelable> SaveSingleModelCollector<M> saveSingle(Class<M> modelClass, M model) {
		return new SaveSingleModelCollectorImpl<M>(modelClass, model);
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

	// ==================================================copy==================================================

	/**
	 * 复制单个模型
	 * 
	 * @param <M>        model type
	 * @param modelClass model class
	 * @param model      需要被复制的model
	 * @return 复制单个模型的模型收集器
	 */
	@SuppressWarnings("unchecked")
	public static <M extends Modelable> CopySingleModelCollector<M> copySingle(M model) {
		Objects.requireNonNull(model);
		return copySingle((Class<M>) model.getClass(), model);
	}

	/**
	 * 复制单个模型
	 * 
	 * @param <M>        model type
	 * @param modelClass model class
	 * @param model      需要被复制的model
	 * @return 复制单个模型的模型收集器
	 */
	public static <M extends Modelable> CopySingleModelCollector<M> copySingle(Class<M> modelClass, M model) {
		return new CopySingleModelCollectorImpl<M>(modelClass, model);
	}

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
	 * 根据唯一主键条件复制该条记录 如果不存在该条记录则不会进行复制操作
	 * 
	 * <pre>
	 * String userId = "123456";
	 * // 如果复制的对象不存在则返回null
	 * User user = modelService
	 * 		.collect(ModelCollectors.copyByOnlyPrimaryKey(User.class, userId).beforeSave((x, model) -> {
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
	public static <M extends Modelable> CopySingleModelCollector<M> copySingleByOnlyPrimaryKey(
			final Class<M> modelClass, final Object primaryKeyValue) {
		return new AbstractCopySingleModelCollector<M>(modelClass) {
			@Override
			protected M getCopySourceModel(SqlModelService modelService) {
				if (Strings.isNullOrBlank(primaryKeyValue)) {
					return null;
				}
				return modelService.findByOnlyPrimaryKey(modelClass, primaryKeyValue);
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
		return copySingleByCondition(modelClass, new Condition(conditionColumn, "=", conditionColumnValue));
	}

	/**
	 * 根据单列等于某值的条件复制查询到的所有记录
	 * 
	 * @param <M>                  model type
	 * @param modelClass           model class
	 * @param conditionColumn      条件列
	 * @param conditionColumnValue 条件列的值
	 * @return 复制集合模型收集器
	 */
	public static <M extends Modelable> CopyListModelCollector<M> copyListBySingleColumnEQ(Class<M> modelClass,
			String conditionColumn, Object conditionColumnValue) {
		return copyListByCondition(modelClass, new Condition(conditionColumn, "=", conditionColumnValue));
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
			Condition condition, @Nullable Sort sort) {
		SqlModel sqlModel = new SqlModel(modelClass);
		sqlModel.addCondition(condition);
		if (null != sort) {
			sqlModel.addSort(sort);
		}
		return copySingleBySqlModel(modelClass, sqlModel);
	}

	/**
	 * 根据 SqlModel 复制查询到的第一条记录
	 * 
	 * @param <M>        model type
	 * @param modelClass model class
	 * @param sqlModel   sqlModel
	 * @return 复制单个模型收集器
	 */
	public static <M extends Modelable> CopySingleModelCollector<M> copySingleBySqlModel(Class<M> modelClass,
			SqlModel sqlModel) {
		return new AbstractCopySingleModelCollector<M>(modelClass) {
			@Override
			protected M getCopySourceModel(SqlModelService modelService) {
				return modelService.findFirstBySqlModel(modelClass, sqlModel);
			}
		};
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
		SqlModel sqlModel = new SqlModel(modelClass);
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
			SqlModel sqlModel) {
		return new AbstractCopyListModelCollector<M>(modelClass) {
			@Override
			protected List<M> getCopySourceModels(SqlModelService modelService) {
				return modelService.findBySqlModel(modelClass, sqlModel);
			}
		};
	}

	// ==================================================remove==================================================

	/**
	 * 根据单列等于某值的条件删除模型记录
	 * 
	 * <pre>
	 * modelService.collect(ModelCollectors.removeBySingleColumnEQ(User.class, "username", "夜龙"));
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
	 * modelService.collect(ModelCollectors.removeBySingleColumnContains(User.class, "username", ArrayUtils.toArray("夜龙", "夜龙1")));
	 * modelService.collect(ModelCollectors.removeBySingleColumnContains(User.class, "age", ArrayUtils.toArray(18, 19)));
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
		return new RemoveBySingleConditionModelCollectorImpl<M>(modelClass, condition);
	}

	// ==================================================modify==================================================

	/**
	 * 根据唯一主键修改单列
	 * 
	 * @param <M>             model type
	 * @param modelClass      model class
	 * @param modifyColumn    修改的列
	 * @param modifyValue     修改后的值
	 * @param primaryKeyValue 唯一主键的值
	 * @return 修改模型收集器
	 */
	public static <M extends Modelable> ModifyModelCollector<M> modifySingleColumnByOnlyPrimaryKey(Class<M> modelClass,
			String modifyColumn, Object modifyValue, Object primaryKeyValue) {
		return new AbstractModifyModelCollector<M>(modelClass) {
			protected Integer doCollect(SqlModelService modelService) {
				FieldAndColumn fieldAndColumn = modelService.getModelConfiguration().getModelAndTableManager()
						.getModelAndTable(modelClass).getOnlyPrimaryKey();
				return modifySingleColumnByCondition(modelClass, modifyColumn, modifyValue,
						new Condition(fieldAndColumn.getColumn(), "=", primaryKeyValue)).collect(modelService);
			};
		};
	}

	/**
	 * 根据单列等于值的条件修改单列数据
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
				new Condition(conditionColumn, "=", conditionColumnValue));
	}

	/**
	 * 根据条件修改单列
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
		return modifySingleColumnCondition(modelClass, modifyColumn, modifyValue, Arrays.asList(condition));
	}

	/**
	 * 根据条件修改单列
	 * 
	 * @param <M>          model type
	 * @param modelClass   model class
	 * @param modifyColumn 修改的列
	 * @param modifyValue  修改后的值
	 * @param conditions   条件
	 * @return 修改模型收集器
	 */
	public static <M extends Modelable> ModifyModelCollector<M> modifySingleColumnCondition(Class<M> modelClass,
			String modifyColumn, Object modifyValue, List<Condition> conditions) {
		SqlModel sqlModel = new SqlModel(modelClass);
		sqlModel.addConditions(conditions);
		return modifySingleColumnBySqlModel(modelClass, modifyColumn, modifyValue, sqlModel);
	}

	/**
	 * 根据 SqlModel 修改单列
	 * 
	 * @param <M>          model type
	 * @param modelClass   model class
	 * @param modifyColumn 修改的列
	 * @param modifyValue  修改后的值
	 * @param sqlModel     sqlModel
	 * @return 修改模型收集器
	 */
	public static <M extends Modelable> ModifyModelCollector<M> modifySingleColumnBySqlModel(Class<M> modelClass,
			String modifyColumn, Object modifyValue, SqlModel sqlModel) {
		return modifyBySqlModel(modelClass, MapUtilsE.asMap(modifyColumn, modifyValue), sqlModel);
	}

	/**
	 * 根据 SqlModel 修改列
	 * 
	 * @param <M>        model type
	 * @param modelClass model class
	 * @param attributes 修改的列映射
	 * @param sqlModel   sqlModel
	 * @return 修改模型收集器
	 */
	public static <M extends Modelable> ModifyModelCollector<M> modifyBySqlModel(Class<M> modelClass,
			Map<String, Object> attributes, SqlModel sqlModel) {
		return new AbstractModifyModelCollector<M>(modelClass) {
			@Override
			protected Integer doCollect(SqlModelService modelService) {
				ModelConfiguration modelConfiguration = modelService.getModelConfiguration();

				ModelSqlFragmentFactory modelSqlFragmentFactory = modelConfiguration.getModelSqlFragmentFactory();
				SqlModelResolver sqlModelResolver = modelConfiguration.getSqlModelResolver();

				AttributeSqlFragment attributeSqlFragment = modelSqlFragmentFactory.createAttributeSqlFragment();
				attributes.forEach(attributeSqlFragment::addAttr);
				attributeSqlFragment.setDataBaseOperationType(DataBaseOperationType.UPDATE);

				UpdateSqlFragment updateSqlFragment = modelSqlFragmentFactory.createUpdateSqlFragment(modelClass,
						attributeSqlFragment);
				updateSqlFragment.setConditionSqlFragment(sqlModelResolver.resolveToCondition(sqlModel, false));

				BoundSql boundSql = updateSqlFragment.getBoundSql();

				return modelService.getBaseDataBaseOperation().update(boundSql.getSql(), boundSql.getParams());
			}
		};
	}

	// ==================================================get==================================================

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
				new Condition(conditionColumn, "=", conditionColumnValue));
	}

	/**
	 * 根据主键获取查询列的第一个值
	 * 
	 * @param <M>             model type
	 * @param <T>             result type
	 * @param modelClass      model class
	 * @param selectColumn    查询的列
	 * @param primaryKeyValue 主键值
	 * @return 获取单个值模型收集器
	 */
	public static <M extends Modelable, T> GetSingleColumnModelCollector<M, T> getSingleValueByOnlyPrimaryKey(
			Class<M> modelClass, String selectColumn, String primaryKeyValue) {
		return new AbstractGetSingleColumnModelCollector<M, T>(modelClass, selectColumn) {
			@Override
			protected T doCollect(SqlModelService modelService) {
				return modelService.findFirstSingleColumnByOnlyPrimaryKey(modelClass, selectColumn, primaryKeyValue);
			}
		};
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
		SqlModel sqlModel = new SqlModel(modelClass);
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
			Class<M> modelClass, String selectColumn, SqlModel sqlModel) {
		return new AbstractGetSingleColumnModelCollector<M, T>(modelClass, selectColumn) {
			@Override
			protected T doCollect(SqlModelService modelService) {
				return modelService.findFirstSingleColumnBySqlModel(modelClass, selectColumn, sqlModel);
			}
		};
	}

	/**
	 * 根据单例等于某值的条件获取第一个模型
	 * 
	 * <pre>
	 * User user = modelService.collect(ModelCollectors.getBySingleColumnEQ(User.class, "username", "夜龙"));
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
		return getModelByCondition(modelClass, new Condition(conditionColumn, "=", conditionColumnValue));
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
		return getModelByConditionSort(modelClass, asList(condition), asList(sort));
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
	public static <M extends Modelable> GetModelCollector<M> getModelByConditionSort(Class<M> modelClass,
			List<Condition> conditions, @Nullable List<Sort> sorts) {
		List<Condition> afterRemoveConditions = removeBySingleNullOrBlankValue(conditions);
		if (CollectionUtils.isEmpty(afterRemoveConditions)) {
			return new GetNullModelCollector<M>(modelClass);
		}
		SqlModel sqlModel = new SqlModel(modelClass);
		sqlModel.addConditions(conditions);
		if (null != sorts) {
			sqlModel.addSorts(sorts);
		}
		return getModelBySqlModel(modelClass, sqlModel);
	}

	/**
	 * 根据指定的条件和排序获取查询到的第一个模型
	 * 
	 * @param <M>        model type
	 * @param modelClass model class
	 * @param sqlModel   sqlModel
	 * @return 获取模型收集器
	 */
	public static <M extends Modelable> GetModelCollector<M> getModelBySqlModel(Class<M> modelClass,
			SqlModel sqlModel) {
		return new AbstractGetModelCollector<M>(modelClass) {
			@Override
			protected M doCollect(SqlModelService modelService) {
				return modelService.findFirstBySqlModel(modelClass, sqlModel);
			}
		};
	}

	// ==================================================find==================================================

	/**
	 * 根据单列等于某值的条件查询模型记录
	 * 
	 * <pre>
	 * List<User> users = modelService.collect(ModelCollectors.findBySingleColumnEQ(User.class, "age", 18));
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
		return findByCondition(modelClass, new Condition(conditionColumn, "=", conditionColumnValue));
	}

	/**
	 * 根据单列等于某值的条件并排序查询模型记录
	 * 
	 * <pre>
	 * List<User> users = modelService.collect(ModelCollectors.findBySingleColumnEQ(User.class, "age", 18));
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
		return findByConditionSort(modelClass, new Condition(conditionColumn, "=", conditionColumnValue), sort);
	}

	/**
	 * 根据单列包含某些值的条件查询模型记录<br/>
	 * 
	 * <pre>
	 * List<User> users = modelService.collect(ModelCollectors.findBySingleColumnContains(User.class, "age", [18,19]));
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
		return findByCondition(modelClass, new Condition(conditionColumn, "IN", conditionColumnValues));
	}

	/**
	 * 根据指定的条件查询
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
	 * 根据指定条件和排序进行查询
	 * 
	 * @param <M>        model type
	 * @param modelClass model class
	 * @param condition  条件
	 * @param sort       排序
	 * @return 查询模型收集器
	 */
	public static <M extends Modelable> FindModelCollector<M> findByConditionSort(Class<M> modelClass,
			Condition condition, @Nullable Sort sort) {
		SqlModel sqlModel = new SqlModel(modelClass);
		sqlModel.addCondition(condition);
		if (null != sort) {
			sqlModel.addSort(sort);
		}
		return findBySqlModel(modelClass, sqlModel);
	}

	/**
	 * 根据指定条件和排序进行查询
	 * 
	 * @param <M>        model type
	 * @param modelClass model class
	 * @param conditions 条件
	 * @param sorts      排序
	 * @return 查询模型收集器
	 */
	public static <M extends Modelable> FindModelCollector<M> findByConditionSort(Class<M> modelClass,
			List<Condition> conditions, @Nullable List<Sort> sorts) {
		List<Condition> removeAfterConditions = removeBySingleNullOrBlankValue(conditions);
		if (CollectionUtils.isEmpty(removeAfterConditions)) {
			return new FindEmptyModelCollector<M>(modelClass);
		}
		SqlModel sqlModel = new SqlModel(modelClass);
		sqlModel.addConditions(conditions);
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
	public static <M extends Modelable> FindModelCollector<M> findBySqlModel(Class<M> modelClass, SqlModel sqlModel) {
		return new AbstractFindModelCollector<M>(modelClass) {
			@Override
			protected List<M> doCollect(SqlModelService modelService) {
				return modelService.findBySqlModel(modelClass, sqlModel);
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
	private static final <T> List<T> asList(T obj) {
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
	private static List<Condition> removeBySingleNullOrBlankValue(List<Condition> conditions) {
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

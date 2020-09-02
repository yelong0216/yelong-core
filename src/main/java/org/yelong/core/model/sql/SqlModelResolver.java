/**
 * 
 */
package org.yelong.core.model.sql;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.yelong.core.annotation.Nullable;
import org.yelong.core.jdbc.sql.condition.ConditionSqlFragment;
import org.yelong.core.jdbc.sql.condition.combination.CombinationConditionSqlFragment;
import org.yelong.core.jdbc.sql.condition.support.Condition;
import org.yelong.core.jdbc.sql.condition.support.ConditionResolver;
import org.yelong.core.jdbc.sql.factory.SqlFragmentFactory;
import org.yelong.core.jdbc.sql.group.GroupSqlFragment;
import org.yelong.core.jdbc.sql.sort.SortSqlFragment;
import org.yelong.core.jdbc.sql.sort.support.Sort;
import org.yelong.core.model.Modelable;
import org.yelong.core.model.manage.ExtendColumnAttribute;
import org.yelong.core.model.manage.FieldAndColumn;
import org.yelong.core.model.manage.ModelManager;
import org.yelong.core.model.property.ModelProperty;

/**
 * SqlModel 解析器
 * 
 * @since 1.0
 */
public interface SqlModelResolver {

	// ==================================================condition==================================================

	/**
	 * 解析为条件SQL片段
	 * 
	 * @param sqlModel sqlModel
	 * @return 条件SQL片段
	 * @see #resolveToConditionSqlFragment(SqlModel, boolean)
	 * @since 2.1
	 */
	@Nullable
	default ConditionSqlFragment resolveToConditionSqlFragment(SqlModel<? extends Modelable> sqlModel) {
		return resolveToConditionSqlFragment(sqlModel, true);
	}

	/**
	 * 解析sqlModel为条件SQL片段
	 * 
	 * @param sqlModel   sqlModel
	 * @param tableAlias 是否拼接表别名
	 * @return 条件SQL片段
	 * @see #resolveToConditions(SqlModel, boolean)
	 * @since 2.1
	 */
	@Nullable
	default ConditionSqlFragment resolveToConditionSqlFragment(SqlModel<? extends Modelable> sqlModel,
			boolean tableAlias) {
		List<Condition> conditions = resolveToConditions(sqlModel, tableAlias);
		if (CollectionUtils.isEmpty(conditions)) {
			return null;
		}
		return getConditionResolver().resolve(conditions);
	}

	/**
	 * 解析为条件SQL片段
	 * 
	 * @param sqlModel sqlModel
	 * @return 条件SQL片段
	 * @deprecated 不规范的命名
	 * @see #resolveToConditionSqlFragment(SqlModel)
	 * @since 2.1
	 */
	@Nullable
	@Deprecated
	default ConditionSqlFragment resolveToCondition(SqlModel<? extends Modelable> sqlModel) {
		return resolveToConditionSqlFragment(sqlModel, true);
	}

	/**
	 * 解析sqlModel为条件SQL片段
	 * 
	 * @param sqlModel   sqlModel
	 * @param tableAlias 是否拼接表别名
	 * @return 条件SQL片段
	 * @deprecated 不规范的命名
	 * @see #resolveToConditionSqlFragment(SqlModel, boolean)
	 * @since 2.1
	 */
	@Nullable
	@Deprecated
	default ConditionSqlFragment resolveToCondition(SqlModel<? extends Modelable> sqlModel, boolean tableAlias) {
		return resolveToConditionSqlFragment(sqlModel, tableAlias);
	}

	/**
	 * 解析为条件
	 * 
	 * @param sqlModel sqlModel
	 * @return 条件集合
	 * @see #resolveToConditions(SqlModel, boolean)
	 * @since 2.1
	 */
	@Nullable
	default List<Condition> resolveToConditions(SqlModel<? extends Modelable> sqlModel) {
		return resolveToConditions(sqlModel, true);
	}

	/**
	 * 解析sqlModel为条件<br/>
	 * 
	 * 1、sqlModel中所有映射且非空的列均设置为条件且都已AND连接<br/>
	 * 2、条件默认已“=”为操作符。这个操作符可以通过
	 * {@link SqlModel#addConditionOperator(String, String)}来设置<br/>
	 * 3、如果tableAlias = true，将会在添加条件中默认添加SqlModel映射的表的别名<br/>
	 * 4、条件的字段如果是拓展字段{@link FieldAndColumn#isExtend() ==
	 * true}，则拓展名称的字段为{@link FieldAndColumn#getExtendColumnAttribute()},
	 * {@link ExtendColumnAttribute#getOfTableAlias()},如果这个别名值为空字符时，这个属性不会添加为条件<br/>
	 * 5、sqlModel的拓展属性同样遵循以上原则<br/>
	 * 6、sqlModel的拓展属性如果携带别名(存在“.”)将不会添加默认别名<br/>
	 * 7、拓展字段会覆盖与sqlModel中相同的名称的字段<br/>
	 * 8、{@link Condition}对象可以完成复杂的条件拼接。这个和{@link CombinationConditionSqlFragment}功能相似。具体详见{@link ConditionResolver}<br/>
	 * 
	 * 如果sqlModel没有指定的ModelAndTable
	 * 将不会具有别名此功能({@link SqlModel#getModelClass()}等于SqlModel.class时)<br/>
	 * 
	 * @param sqlModel   sqlModel
	 * @param tableAlias 是否拼接表别名
	 * @return 条件集合
	 * @since 2.1
	 */
	@Nullable
	List<Condition> resolveToConditions(SqlModel<? extends Modelable> sqlModel, boolean tableAlias);

	/**
	 * sqlModel 中是否存在条件
	 * 
	 * @param sqlModel sqlModel
	 * @return <code>true</code> 存在条件
	 * @since 2.1
	 */
	boolean existCondition(SqlModel<? extends Modelable> sqlModel);

	// ==================================================group==================================================

	/**
	 * 解析为分组SQL片段。默认添加表别名
	 * 
	 * @param sqlModel sqlModel
	 * @return 分组SQL片段
	 */
	@Nullable
	default GroupSqlFragment resolveToGroupSqlFramgment(SqlModel<? extends Modelable> sqlModel) {
		return resolveToGroupSqlFramgment(sqlModel, true);
	}

	/**
	 * 解析为分组SQL片段
	 * 
	 * @param sqlModel   sqlModel
	 * @param tableAlias 是否在列前添加表别名
	 * @return 分组SQL片段
	 * @see SqlModel#getGroupColumns()
	 * @since 2.1
	 */
	GroupSqlFragment resolveToGroupSqlFramgment(SqlModel<? extends Modelable> sqlModel, boolean tableAlias);

	/**
	 * 是否存在分组
	 * 
	 * @param sqlModel sqlModel
	 * @return <code>true</code> 存在分组
	 * @since 2.1
	 */
	boolean existGroup(SqlModel<? extends Modelable> sqlModel);

	// ==================================================sort==================================================

	/**
	 * 解析为排序SQL片段
	 * 
	 * @param sqlModel sqlModel
	 * @return 排序SQL片段
	 * @see #resolveToSortSqlFragment(SqlModel, boolean)
	 * @deprecated 不规范的命名
	 * @since 2.1
	 */
	@Nullable
	default SortSqlFragment resolveToSort(SqlModel<? extends Modelable> sqlModel) {
		return resolveToSortSqlFragment(sqlModel, true);
	}

	/**
	 * 解析为排序SQL片段
	 * 
	 * @param sqlModel   sqlModel
	 * @param tableAlias 是否拼接别名
	 * @return 排序SQL片段
	 * @see #resolveToSortSqlFragment(SqlModel, boolean)
	 * @deprecated 不规范的命名
	 * @since 2.1
	 */
	@Nullable
	default SortSqlFragment resolveToSort(SqlModel<? extends Modelable> sqlModel, boolean tableAlias) {
		return resolveToSortSqlFragment(sqlModel, tableAlias);
	}

	/**
	 * 解析为排序SQL片段
	 * 
	 * @param sqlModel sqlModel
	 * @return 排序SQL片段
	 * @see #resolveToSorts(SqlModel, boolean)
	 * @since 2.1
	 */
	@Nullable
	default SortSqlFragment resolveToSortSqlFragment(SqlModel<? extends Modelable> sqlModel) {
		return resolveToSortSqlFragment(sqlModel, true);
	}

	/**
	 * 解析为排序SQL片段
	 * 
	 * @param sqlModel sqlModel
	 * @return 排序SQL片段
	 * @see #resolveToSorts(SqlModel, boolean)
	 * @since 2.1
	 */
	@Nullable
	default SortSqlFragment resolveToSortSqlFragment(SqlModel<? extends Modelable> sqlModel, boolean tableAlias) {
		List<Sort> sorts = resolveToSorts(sqlModel, tableAlias);
		if (CollectionUtils.isEmpty(sorts)) {
			return null;
		}
		SortSqlFragment sortSqlFragment = getSqlFragmentFactory().createSortSqlFragment();
		sorts.forEach(sortSqlFragment::addSort);
		return sortSqlFragment;
	}

	/**
	 * 解析为排序
	 * 
	 * @param sqlModel sqlModel
	 * @return 排序信息集合
	 * @since 2.1
	 */
	@Nullable
	default List<Sort> resolveToSorts(SqlModel<? extends Modelable> sqlModel) {
		return resolveToSorts(sqlModel, true);
	}

	/**
	 * 解析{@link SqlModel#getSortFieldMap()}为排序sql<br/>
	 * 
	 * 1、如果tableAlias = true，将会在排序的列中默认添加SqlModel映射的表的别名。<br/>
	 * 2、如果列自带别名(存在“.”)将不会添加默认别名<br/>
	 * 
	 * 如果sqlModel没有指定的ModelAndTable
	 * 将不会具有别名此功能({@link SqlModel#getModelClass()}等于SqlModel.class时)<br/>
	 * 
	 * @param sqlModel   sqlModel
	 * @param tableAlias 是否在字段前添加表别名
	 * @return 排序SQL片段
	 * @see SqlModel#getSortFields()
	 */
	@Nullable
	List<Sort> resolveToSorts(SqlModel<? extends Modelable> sqlModel, boolean tableAlias);

	/**
	 * sqlModel 中是否存在排序
	 * 
	 * @param sqlModel sqlModel
	 * @return <code>true</code>
	 * @since 2.1
	 */
	boolean existSort(SqlModel<? extends Modelable> sqlModel);

	// ==================================================util==================================================

	/**
	 * @return 模型的管理者
	 */
	ModelManager getModelManager();

	/**
	 * @return SQL片段工厂
	 */
	SqlFragmentFactory getSqlFragmentFactory();

	/**
	 * @return model 属性
	 */
	ModelProperty getModelProperty();

	/**
	 * @return 条件解析器
	 */
	ConditionResolver getConditionResolver();

}

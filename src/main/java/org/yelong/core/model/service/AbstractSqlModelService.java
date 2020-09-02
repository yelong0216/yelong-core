/**
 * 
 */
package org.yelong.core.model.service;

import java.util.List;

import org.yelong.commons.util.ListUtilsE;
import org.yelong.core.jdbc.sql.condition.ConditionSqlFragment;
import org.yelong.core.jdbc.sql.group.GroupSqlFragment;
import org.yelong.core.jdbc.sql.sort.SortSqlFragment;
import org.yelong.core.model.ModelConfiguration;
import org.yelong.core.model.Modelable;
import org.yelong.core.model.sql.SqlModel;
import org.yelong.core.model.sql.SqlModelResolver;

/**
 * 抽象的Sql模型业务实现
 * 
 * @since 1.0
 */
public abstract class AbstractSqlModelService extends AbstractModelService implements SqlModelService {

	public AbstractSqlModelService(ModelConfiguration modelConfiguration) {
		super(modelConfiguration);
	}
	// ==================================================remove==================================================

	@Override
	public Integer removeBySqlModel(Class<? extends Modelable> modelClass, SqlModel<? extends Modelable> sqlModel) {
		ConditionSqlFragment conditionSqlFragment = getSqlModelResolver().resolveToConditionSqlFragment(sqlModel,
				false);
		return removeBySqlFragment(modelClass, conditionSqlFragment);
	}

	// ==================================================modify==================================================

	@Override
	public Integer modifyBySqlModel(Modelable model, SqlModel<? extends Modelable> sqlModel) {
		ConditionSqlFragment conditionSqlFragment = getSqlModelResolver().resolveToConditionSqlFragment(sqlModel,
				false);
		return modifyBySqlFragment(model, conditionSqlFragment);
	}

	@Override
	public Integer modifySelectiveBySqlModel(Modelable model, SqlModel<? extends Modelable> sqlModel) {
		ConditionSqlFragment conditionSqlFragment = getSqlModelResolver().resolveToConditionSqlFragment(sqlModel,
				false);
		return modifySelectiveBySqlFragment(model, conditionSqlFragment);
	}

	@Override
	public Integer modifyBySqlModel(String updateSql, Object[] params, SqlModel<? extends Modelable> sqlModel) {
		ConditionSqlFragment conditionSqlFragment = getSqlModelResolver().resolveToConditionSqlFragment(sqlModel,
				false);
		return modifyBySqlFragment(updateSql, params, conditionSqlFragment);
	}

	// ==================================================count==================================================

	@Override
	public Long countBySqlModel(Class<? extends Modelable> modelClass, SqlModel<? extends Modelable> sqlModel) {
		ConditionSqlFragment conditionSqlFragment = getSqlModelResolver().resolveToConditionSqlFragment(sqlModel);
		return countBySqlFragment(modelClass, conditionSqlFragment);
	}

	@Override
	public Long countBySqlModel(String countSql, Object[] countSqlParams, SqlModel<? extends Modelable> sqlModel) {
		ConditionSqlFragment conditionSqlFragment = getSqlModelResolver().resolveToConditionSqlFragment(sqlModel);
		return countBySqlFragment(countSql, countSqlParams, conditionSqlFragment);
	}

	// ==================================================find==================================================

	@Override
	public <M extends Modelable> List<M> findBySqlModel(Class<M> modelClass, SqlModel<? extends Modelable> sqlModel) {
		ConditionSqlFragment conditionSqlFragment = getSqlModelResolver().resolveToConditionSqlFragment(sqlModel);
		GroupSqlFragment groupSqlFragment = getSqlModelResolver().resolveToGroupSqlFramgment(sqlModel);
		SortSqlFragment sortSqlFragment = getSqlModelResolver().resolveToSortSqlFragment(sqlModel);
		return findBySqlFragment(modelClass, conditionSqlFragment, groupSqlFragment, sortSqlFragment);
	}

	@Override
	public <M extends Modelable> List<M> findBySqlModel(Class<M> modelClass, String selectSql, Object[] selectSqlParams,
			SqlModel<? extends Modelable> sqlModel) {
		ConditionSqlFragment conditionSqlFragment = getSqlModelResolver().resolveToConditionSqlFragment(sqlModel);
		GroupSqlFragment groupSqlFragment = getSqlModelResolver().resolveToGroupSqlFramgment(sqlModel);
		SortSqlFragment sortSqlFragment = getSqlModelResolver().resolveToSortSqlFragment(sqlModel);
		return findBySqlFragment(modelClass, selectSql, selectSqlParams, conditionSqlFragment, groupSqlFragment,
				sortSqlFragment);
	}

	@Override
	public <M extends Modelable> M findFirstBySqlModel(Class<M> modelClass, SqlModel<? extends Modelable> sqlModel) {
		return ListUtilsE.get(findBySqlModel(modelClass, sqlModel), 0);
	}

	@Override
	public <M extends Modelable> M findFirstBySqlModel(Class<M> modelClass, String selectSql, Object[] selectSqlParams,
			SqlModel<? extends Modelable> sqlModel) {
		return ListUtilsE.get(findBySqlModel(modelClass, selectSql, selectSqlParams, sqlModel), 0);
	}

	// ==================================================findSingleColumn==================================================

	@Override
	public <T> List<T> findSingleColumnBySqlModel(Class<? extends Modelable> modelClass, String selectColumn,
			SqlModel<? extends Modelable> sqlModel) {
		ConditionSqlFragment conditionSqlFragment = getSqlModelResolver().resolveToConditionSqlFragment(sqlModel);
		GroupSqlFragment groupSqlFragment = getSqlModelResolver().resolveToGroupSqlFramgment(sqlModel);
		SortSqlFragment sortSqlFragment = getSqlModelResolver().resolveToSortSqlFragment(sqlModel);
		return findSingleColumnBySqlFragment(modelClass, selectColumn, conditionSqlFragment, groupSqlFragment,
				sortSqlFragment);
	}

	@Override
	public <T> T findFirstSingleColumnBySqlModel(Class<? extends Modelable> modelClass, String selectColumn,
			SqlModel<? extends Modelable> sqlModel) {
		return ListUtilsE.get(findSingleColumnBySqlModel(modelClass, selectColumn, sqlModel), 0);
	}

	@Override
	public <T> List<T> findSingleColumnBySqlModel(String selectSql, Object[] selectSqlParams,
			SqlModel<? extends Modelable> sqlModel) {
		ConditionSqlFragment conditionSqlFragment = getSqlModelResolver().resolveToConditionSqlFragment(sqlModel);
		GroupSqlFragment groupSqlFragment = getSqlModelResolver().resolveToGroupSqlFramgment(sqlModel);
		SortSqlFragment sortSqlFragment = getSqlModelResolver().resolveToSortSqlFragment(sqlModel);
		return findSingleColumnBySqlFragment(selectSql, selectSqlParams, conditionSqlFragment, groupSqlFragment,
				sortSqlFragment);
	}

	@Override
	public <T> T findFirstSingleColumnBySqlModel(String selectSql, Object[] selectSqlParams,
			SqlModel<? extends Modelable> sqlModel) {
		return ListUtilsE.get(findSingleColumnBySqlModel(selectSql, selectSqlParams, sqlModel), 0);
	}

	// ==================================================findPage==================================================

	@Override
	public <M extends Modelable> List<M> findPageBySqlModel(Class<M> modelClass, SqlModel<? extends Modelable> sqlModel,
			int pageNum, int pageSize) {
		ConditionSqlFragment conditionSqlFragment = getSqlModelResolver().resolveToConditionSqlFragment(sqlModel);
		GroupSqlFragment groupSqlFragment = getSqlModelResolver().resolveToGroupSqlFramgment(sqlModel);
		SortSqlFragment sortSqlFragment = getSqlModelResolver().resolveToSortSqlFragment(sqlModel);
		return findPageBySqlFragment(modelClass, conditionSqlFragment, groupSqlFragment, sortSqlFragment, pageNum,
				pageSize);
	}

	@Override
	public <M extends Modelable> List<M> findPageBySqlModel(Class<M> modelClass, String selectSql,
			Object[] selectSqlParams, SqlModel<? extends Modelable> sqlModel, int pageNum, int pageSize) {
		ConditionSqlFragment conditionSqlFragment = getSqlModelResolver().resolveToConditionSqlFragment(sqlModel);
		GroupSqlFragment groupSqlFragment = getSqlModelResolver().resolveToGroupSqlFramgment(sqlModel);
		SortSqlFragment sortSqlFragment = getSqlModelResolver().resolveToSortSqlFragment(sqlModel);
		return findPageBySqlFragment(modelClass, selectSql, selectSqlParams, conditionSqlFragment, groupSqlFragment,
				sortSqlFragment, pageNum, pageSize);
	}

	// ==================================================util==================================================

	protected SqlModelResolver getSqlModelResolver() {
		return getModelConfiguration().getSqlModelResolver();
	}
}

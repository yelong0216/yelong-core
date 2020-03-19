/**
 * 
 */
package org.yelong.core.model.service;

import java.util.List;

import org.yelong.core.jdbc.sql.condition.ConditionSqlFragment;
import org.yelong.core.jdbc.sql.executable.CountSqlFragment;
import org.yelong.core.jdbc.sql.executable.DeleteSqlFragment;
import org.yelong.core.jdbc.sql.executable.SelectSqlFragment;
import org.yelong.core.jdbc.sql.executable.UpdateSqlFragment;
import org.yelong.core.jdbc.sql.sort.SortSqlFragment;
import org.yelong.core.model.Model;
import org.yelong.core.model.ModelConfiguration;
import org.yelong.core.model.sql.SqlModel;
import org.yelong.core.model.sql.SqlModelResolver;

/**
 * @author PengFei
 * @date 2020年3月4日下午6:01:53
 * @since 1.0
 */
public abstract class AbstractSqlModelService extends AbstractModelService implements SqlModelService{

	public AbstractSqlModelService(ModelConfiguration modelConfiguration) {
		super(modelConfiguration);
	}

	@Override
	public <M extends Model, S extends SqlModel> Integer removeBySqlModel(Class<M> modelClass, S sqlModel) {
		return remove(modelClass, getSqlModelResolver().resolveToCondition(sqlModel, false));
	}

	@Override
	public <M extends Model, S extends SqlModel> Integer removeBySqlModel(String sql, S sqlModel) {
		DeleteSqlFragment deleteSqlFragment = getModelSqlFragmentFactory().createDeleteSqlFragment(sql);
		deleteSqlFragment.setConditionSqlFragment(getSqlModelResolver().resolveToCondition(sqlModel, false));
		return execute(deleteSqlFragment);
	}

	@Override
	public <M extends Model, S extends SqlModel> Integer modifyBySqlModel(M model, S sqlModel) {
		ConditionSqlFragment conditionSqlFragment = getSqlModelResolver().resolveToCondition(sqlModel, false);
		return modifyByCondition(model, conditionSqlFragment);
	}

	@Override
	public <M extends Model, S extends SqlModel> Integer modifySelectiveBySqlModel(M model, S sqlModel) {
		ConditionSqlFragment conditionSqlFragment = getSqlModelResolver().resolveToCondition(sqlModel, false);
		return modifySelectiveByCondition(model, conditionSqlFragment);
	}
	
	@Override
	public <M extends Model, S extends SqlModel> Integer modifyBySqlModel(String sql, Object[] params, S sqlModel) {
		UpdateSqlFragment updateSqlFragment = getModelSqlFragmentFactory().createUpdateSqlFragment(sql, params);
		ConditionSqlFragment conditionSqlFragment = getSqlModelResolver().resolveToCondition(sqlModel, false);
		updateSqlFragment.setConditionSqlFragment(conditionSqlFragment);
		return execute(updateSqlFragment);
	}

	@Override
	public <M extends Model, S extends SqlModel> Long countBySqlModel(Class<M> modelClass, S sqlModel) {
		ConditionSqlFragment conditionSqlFragment = getSqlModelResolver().resolveToCondition(sqlModel);
		return countByCondition(modelClass, conditionSqlFragment);
	}

	@Override
	public <M extends Model, S extends SqlModel> Long countBySqlModel(String sql, S sqlModel) {
		ConditionSqlFragment conditionSqlFragment = getSqlModelResolver().resolveToCondition(sqlModel);
		CountSqlFragment countSqlFragment = getModelSqlFragmentFactory().createCountSqlFragment(sql);
		countSqlFragment.setConditionSqlFragment(conditionSqlFragment);
		return execute(countSqlFragment);
	}

	@Override
	public <M extends Model, S extends SqlModel> List<M> findBySqlModel(Class<M> modelClass, S sqlModel) {
		ConditionSqlFragment conditionSqlFragment = getSqlModelResolver().resolveToCondition(sqlModel);
		return findByCondition(modelClass, conditionSqlFragment);
	}

	@Override
	public <M extends Model, S extends SqlModel> List<M> findBySqlModel(Class<M> modelClass,String sql, S sqlModel) {
		ConditionSqlFragment conditionSqlFragment = getSqlModelResolver().resolveToCondition(sqlModel);
		SelectSqlFragment selectSqlFragment = getModelSqlFragmentFactory().createSelectSqlFragment(sql);
		selectSqlFragment.setConditionSqlFragment(conditionSqlFragment);
		return execute(modelClass, selectSqlFragment);
	}

	@Override
	public <M extends Model, S extends SqlModel> List<M> findPageBySqlModel(Class<M> modelClass, S sqlModel,
			int pageNum, int pageSize) {
		ConditionSqlFragment conditionSqlFragment = getSqlModelResolver().resolveToCondition(sqlModel);
		SortSqlFragment sortSqlFragment = getSqlModelResolver().resolveToSort(sqlModel);
		return findPageByConditionSort(modelClass, conditionSqlFragment, sortSqlFragment, pageNum, pageSize);
	}

	@Override
	public <M extends Model, S extends SqlModel> List<M> findPageBySqlModel(Class<M> modelClass,String sql, S sqlModel, int pageNum,
			int pageSize) {
		ConditionSqlFragment conditionSqlFragment = getSqlModelResolver().resolveToCondition(sqlModel);
		SortSqlFragment sortSqlFragment = getSqlModelResolver().resolveToSort(sqlModel);
		SelectSqlFragment selectSqlFragment = getModelSqlFragmentFactory().createSelectSqlFragment(sql);
		selectSqlFragment.setConditionSqlFragment(conditionSqlFragment);
		selectSqlFragment.setSortSqlFragment(sortSqlFragment);
		return execute(modelClass, selectSqlFragment);
	}
	
	protected SqlModelResolver getSqlModelResolver() {
		return getModelConfiguration().getSqlModelResolver();
	}
	

}

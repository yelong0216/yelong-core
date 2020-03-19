/**
 * 
 */
package org.yelong.core.model.sql;

import org.apache.commons.lang3.StringUtils;
import org.yelong.core.jdbc.dialect.Dialect;
import org.yelong.core.jdbc.sql.attribute.AttributeSqlFragment;
import org.yelong.core.jdbc.sql.executable.CountSqlFragment;
import org.yelong.core.jdbc.sql.executable.DeleteSqlFragment;
import org.yelong.core.jdbc.sql.executable.InsertSqlFragment;
import org.yelong.core.jdbc.sql.executable.SelectSqlFragment;
import org.yelong.core.jdbc.sql.executable.UpdateSqlFragment;
import org.yelong.core.jdbc.sql.factory.DefaultSqlFragmentFactory;
import org.yelong.core.model.Model;
import org.yelong.core.model.annotation.Count;
import org.yelong.core.model.annotation.Select;
import org.yelong.core.model.resolve.ModelAndTable;
import org.yelong.core.model.resolve.ModelAndTableManager;

/**
 * @author PengFei
 * @date 2020年1月20日下午2:38:17
 */
public class DefaultModelSqlFragmentFactory extends DefaultSqlFragmentFactory implements ModelSqlFragmentFactory{

	private final ModelAndTableManager modelAndTableManager;
	
	public DefaultModelSqlFragmentFactory(Dialect dialect , ModelAndTableManager modelAndTableManager) {
		super(dialect);
		this.modelAndTableManager = modelAndTableManager;
	}

	@Override
	public <M extends Model> InsertSqlFragment createInsertSqlFragment(Class<M> modelClass,
			AttributeSqlFragment attributeSqlFragment) {
		String tableName = modelAndTableManager.getModelAndTable(modelClass).getTableName();
		return createInsertSqlFragment(tableName, attributeSqlFragment);
	}

	@Override
	public <M extends Model> DeleteSqlFragment createDeleteSqlFragment(Class<M> modelClass) {
		ModelAndTable modelAndTable = modelAndTableManager.getModelAndTable(modelClass);
		String sql = getDialect().getBaseDeleteSql(modelAndTable.getTableName(), modelAndTable.getTableAlias());
		return createDeleteSqlFragment(sql);
	}

	@Override
	public <M extends Model> UpdateSqlFragment createUpdateSqlFragment(Class<M> modelClass,
			AttributeSqlFragment attributeSqlFragment) {
		String tableName = modelAndTableManager.getModelAndTable(modelClass).getTableName();
		return createUpdateSqlFragment(tableName, attributeSqlFragment);
	}

	@Override
	public <M extends Model> SelectSqlFragment createSelectSqlFragment(Class<M> modelClass) {
		ModelAndTable modelAndTable = modelAndTableManager.getModelAndTable(modelClass);
		String selectSql = null;
		Select select = modelClass.isAnnotationPresent(Select.class) ? modelClass.getAnnotation(Select.class) : null;
		if( null != select ) {
			selectSql = select.value();
		}
		if(StringUtils.isEmpty(selectSql)) {
			selectSql = getDialect().getBaseSelectSql(modelAndTable.getTableName(), modelAndTable.getTableAlias());
		}
		return createSelectSqlFragment(selectSql);
	}

	@Override
	public <M extends Model> CountSqlFragment createCountSqlFragment(Class<M> modelClass) {
		ModelAndTable modelAndTable = modelAndTableManager.getModelAndTable(modelClass);
		String countSql = null;
		Count count = modelClass.isAnnotationPresent(Count.class) ? modelClass.getAnnotation(Count.class) : null;
		if( null != count ) {
			countSql = count.value();
		}
		if(StringUtils.isEmpty(countSql)) {
			countSql = getDialect().getBaseCountSql(modelAndTable.getTableName(), modelAndTable.getTableAlias());
		}
		return createCountSqlFragment(countSql);
	}

	@Override
	public ModelAndTableManager getModelAndTableManager() {
		return this.modelAndTableManager;
	}

}

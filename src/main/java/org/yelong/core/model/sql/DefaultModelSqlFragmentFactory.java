/**
 * 
 */
package org.yelong.core.model.sql;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.yelong.commons.annotation.AnnotationUtils;
import org.yelong.core.jdbc.sql.attribute.AttributeSqlFragment;
import org.yelong.core.jdbc.sql.executable.CountSqlFragment;
import org.yelong.core.jdbc.sql.executable.DeleteSqlFragment;
import org.yelong.core.jdbc.sql.executable.InsertSqlFragment;
import org.yelong.core.jdbc.sql.executable.SelectSqlFragment;
import org.yelong.core.jdbc.sql.executable.UpdateSqlFragment;
import org.yelong.core.jdbc.sql.factory.SqlFragmentFactory;
import org.yelong.core.jdbc.sql.factory.support.SqlFragmentFactoryWrapper;
import org.yelong.core.model.Modelable;
import org.yelong.core.model.annotation.Count;
import org.yelong.core.model.annotation.Select;
import org.yelong.core.model.exception.ExtendColumnException;
import org.yelong.core.model.exception.NotExistFieldAndColumnException;
import org.yelong.core.model.resolve.FieldAndColumn;
import org.yelong.core.model.resolve.ModelAndTable;
import org.yelong.core.model.resolve.ModelAndTableManager;
import org.yelong.core.model.sql.CustomModelSql.OperationType;

/**
 * 默认的model sql 片段工厂
 * 
 * @author PengFei
 */
public class DefaultModelSqlFragmentFactory extends SqlFragmentFactoryWrapper implements ModelSqlFragmentFactory {

	private final ModelAndTableManager modelAndTableManager;

	public DefaultModelSqlFragmentFactory(SqlFragmentFactory sqlFragmentFactory,
			ModelAndTableManager modelAndTableManager) {
		super(sqlFragmentFactory);
		this.modelAndTableManager = modelAndTableManager;
	}

	@Override
	public <M extends Modelable> InsertSqlFragment createInsertSqlFragment(Class<M> modelClass,
			AttributeSqlFragment attributeSqlFragment) {
		String tableName = modelAndTableManager.getModelAndTable(modelClass).getTableName();
		return createInsertSqlFragment(tableName, attributeSqlFragment);
	}

	@Override
	public <M extends Modelable> DeleteSqlFragment createDeleteSqlFragment(Class<M> modelClass) {
		ModelAndTable modelAndTable = modelAndTableManager.getModelAndTable(modelClass);
		String sql = getDialect().getBaseDeleteSql(modelAndTable.getTableName(), modelAndTable.getTableAlias());
		return createDeleteSqlFragment(sql);
	}

	@Override
	public <M extends Modelable> UpdateSqlFragment createUpdateSqlFragment(Class<M> modelClass,
			AttributeSqlFragment attributeSqlFragment) {
		String tableName = modelAndTableManager.getModelAndTable(modelClass).getTableName();
		return createUpdateSqlFragment(tableName, attributeSqlFragment);
	}

	@Override
	public <M extends Modelable> SelectSqlFragment createSelectSqlFragment(Class<M> modelClass) {
		ModelAndTable modelAndTable = modelAndTableManager.getModelAndTable(modelClass);
		String selectSql = null;
		Select select = AnnotationUtils.getAnnotation(modelClass, Select.class, true);
		if (null != select) {
			selectSql = select.value();
		}
		if (StringUtils.isEmpty(selectSql)) {
			selectSql = CustomModelSql.getModelSql(modelClass, OperationType.SELECT, getDialect().getName());
		}
		if (StringUtils.isEmpty(selectSql)) {
//			List<String> columns = modelAndTable.getFieldAndColumns().stream().filter(x->!x.isExtend()).map(FieldAndColumn::getColumn).collect(Collectors.toList());
//			selectSql = getDialect().getBaseSelectSql(modelAndTable.getTableName(), modelAndTable.getTableAlias(),columns);
			selectSql = getDialect().getBaseSelectSql(modelAndTable.getTableName(), modelAndTable.getTableAlias());
		}
		return createSelectSqlFragment(selectSql);
	}

	@Override
	public <M extends Modelable> SelectSqlFragment createSelectSqlFragmentByFieldNames(Class<M> modelClass,
			List<String> fieldNames) throws NotExistFieldAndColumnException {
		if (CollectionUtils.isEmpty(fieldNames)) {
			throw new UnsupportedOperationException("缺失查询的字段！");
		}
		ModelAndTable modelAndTable = modelAndTableManager.getModelAndTable(modelClass);
		List<String> columns = new ArrayList<String>(fieldNames.size());
		for (String fieldName : fieldNames) {
			FieldAndColumn fieldAndColumn = modelAndTable.getFieldAndColumn(fieldName);
			if (null == fieldAndColumn) {
				throw new NotExistFieldAndColumnException(modelClass.getName() + "不存在" + fieldName + "的映射字段！");
			}
			if (fieldAndColumn.isExtend()) {
				throw new ExtendColumnException(modelClass.getName() + "的" + fieldName + "字段映射的列是一个拓展列！");
			}
			columns.add(fieldAndColumn.getColumn());
		}
		return createSelectSqlFragmentByColumns(modelClass, columns);
	}

	@Override
	public <M extends Modelable> SelectSqlFragment createSelectSqlFragmentByColumns(Class<M> modelClass,
			List<String> columns) {
		if (CollectionUtils.isEmpty(columns)) {
			throw new UnsupportedOperationException("缺失查询的列！");
		}
		ModelAndTable modelAndTable = modelAndTableManager.getModelAndTable(modelClass);
		return createSelectSqlFragment(
				getDialect().getBaseSelectSql(modelAndTable.getTableName(), modelAndTable.getTableAlias(), columns));
	}

	@Override
	public <M extends Modelable> CountSqlFragment createCountSqlFragment(Class<M> modelClass) {
		ModelAndTable modelAndTable = modelAndTableManager.getModelAndTable(modelClass);
		String countSql = null;
		Count count = modelClass.isAnnotationPresent(Count.class) ? modelClass.getAnnotation(Count.class) : null;
		if (null != count) {
			countSql = count.value();
		}
		if (StringUtils.isEmpty(countSql)) {
			countSql = getDialect().getBaseCountSql(modelAndTable.getTableName(), modelAndTable.getTableAlias());
		}
		return createCountSqlFragment(countSql);
	}

	@Override
	public ModelAndTableManager getModelAndTableManager() {
		return this.modelAndTableManager;
	}

}

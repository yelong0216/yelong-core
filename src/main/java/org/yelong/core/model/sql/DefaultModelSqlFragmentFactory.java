/**
 * 
 */
package org.yelong.core.model.sql;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.yelong.core.jdbc.sql.BoundSql;
import org.yelong.core.jdbc.sql.attribute.AttributeSqlFragment;
import org.yelong.core.jdbc.sql.count.CountSqlParser;
import org.yelong.core.jdbc.sql.executable.CountSqlFragment;
import org.yelong.core.jdbc.sql.executable.DeleteSqlFragment;
import org.yelong.core.jdbc.sql.executable.InsertSqlFragment;
import org.yelong.core.jdbc.sql.executable.SelectSqlFragment;
import org.yelong.core.jdbc.sql.executable.UpdateSqlFragment;
import org.yelong.core.jdbc.sql.factory.SqlFragmentFactory;
import org.yelong.core.jdbc.sql.factory.support.SqlFragmentFactoryWrapper;
import org.yelong.core.model.Modelable;
import org.yelong.core.model.Models;
import org.yelong.core.model.manage.FieldAndColumn;
import org.yelong.core.model.manage.ModelAndTable;
import org.yelong.core.model.manage.ModelManager;
import org.yelong.core.model.manage.SelectColumnCondition;
import org.yelong.core.model.manage.exception.FieldAndColumnException;
import org.yelong.core.model.manage.exception.ModelAndTableException;
import org.yelong.core.model.manage.exception.NotExistFieldAndColumnException;
import org.yelong.core.model.sql.CustomModelSql.OperationType;

/**
 * 默认的model sql 片段工厂
 * 
 * @since 1.0
 */
public class DefaultModelSqlFragmentFactory extends SqlFragmentFactoryWrapper implements ModelSqlFragmentFactory {

	private final ModelManager modelManager;

	protected CountSqlParser countSqlParser = new CountSqlParser();

	public DefaultModelSqlFragmentFactory(SqlFragmentFactory sqlFragmentFactory, ModelManager modelManager) {
		super(sqlFragmentFactory);
		this.modelManager = modelManager;
	}

	@Override
	public InsertSqlFragment createInsertSqlFragment(Class<? extends Modelable> modelClass,
			AttributeSqlFragment attributeSqlFragment) {
		String tableName = modelManager.getModelAndTable(modelClass).getTableName();
		return createInsertSqlFragment(tableName, attributeSqlFragment);
	}

	@Override
	public DeleteSqlFragment createDeleteSqlFragment(Class<? extends Modelable> modelClass) {
		ModelAndTable modelAndTable = modelManager.getModelAndTable(modelClass);
		String deleteSql = modelAndTable.getDeleteSql();
		if(StringUtils.isBlank(deleteSql)) {
			deleteSql = getDialect().getBaseDeleteSql(modelAndTable.getTableName(), modelAndTable.getTableAlias());
		}
		return createDeleteSqlFragment(deleteSql);
	}

	@Override
	public UpdateSqlFragment createUpdateSqlFragment(Class<? extends Modelable> modelClass,
			AttributeSqlFragment attributeSqlFragment) {
		String tableName = modelManager.getModelAndTable(modelClass).getTableName();
		return createUpdateSqlFragment(tableName, attributeSqlFragment);
	}

	@Override
	public SelectSqlFragment createSelectSqlFragment(Class<? extends Modelable> modelClass,
			SelectSqlColumnMode selectSqlColumnMode, boolean force) {
		Objects.requireNonNull(selectSqlColumnMode);
		ModelAndTable modelAndTable = modelManager.getModelAndTable(modelClass);
		String selectSql = modelAndTable.getSelectSql();
		if (StringUtils.isEmpty(selectSql)) {
			selectSql = CustomModelSql.getModelSql(modelClass, OperationType.SELECT, getDialect().getName());
		}
		if (StringUtils.isEmpty(selectSql)) {
			String tableName = modelAndTable.getTableName();
			if (StringUtils.isBlank(tableName)) {
				if (modelAndTable.isView()) {
					throw new ModelAndTableException(modelAndTable, "视图没有指定查询语句并且也没有指定视图名称，无法生成'select sql'");
				} else {
					throw new ModelAndTableException(modelAndTable, "表没有指定查询语句并且也没有指定表名称，无法生成'select sql'");
				}
			}
			if (!force) {
				selectSqlColumnMode = modelAndTable.getSelectSqlColumnMode() != null
						? modelAndTable.getSelectSqlColumnMode()
						: selectSqlColumnMode;
			}
			switch (selectSqlColumnMode) {
			case STAR:
				selectSql = getDialect().getBaseSelectSql(modelAndTable.getTableName(), modelAndTable.getTableAlias());
				break;
			case COLUMN:
				List<String> columns = modelAndTable.getNormalFieldAndColumns().stream()//
						.filter(x -> x.isSelect())// 排除不查询的
						.filter(x -> {// 排除所有不需要映射的
							SelectColumnCondition selectColumnCondition = x.getSelectColumnCondition();
							if (null == selectColumnCondition) {
								return true;
							}
							String propertyName = selectColumnCondition.getPropertyName();
							Object value = Models.getProperty(propertyName);
							if (null == value) {
								return selectColumnCondition.isMatchIfMissing();
							}
							return value.toString().equals(selectColumnCondition.getHavingValue());
						}).map(FieldAndColumn::getColumn)// 获取所有的列
						.collect(Collectors.toList());
				selectSql = getDialect().getBaseSelectSql(modelAndTable.getTableName(), modelAndTable.getTableAlias(),
						columns);
				break;
			default:
				throw new UnsupportedOperationException();
			}
		}
		return createSelectSqlFragment(selectSql);
	}

	@Override
	public SelectSqlFragment createSelectSqlFragmentByFieldNames(Class<? extends Modelable> modelClass,
			List<String> fieldNames) {
		if (CollectionUtils.isEmpty(fieldNames)) {
			throw new UnsupportedOperationException("缺失查询的字段！");
		}
		ModelAndTable modelAndTable = modelManager.getModelAndTable(modelClass);
		List<String> columns = new ArrayList<String>(fieldNames.size());
		for (String fieldName : fieldNames) {
			FieldAndColumn fieldAndColumn = modelAndTable.getFieldAndColumn(fieldName);
			if (null == fieldAndColumn) {
				throw new NotExistFieldAndColumnException(modelAndTable,
						modelClass.getName() + "不存在" + fieldName + "的映射字段！");
			}
			if (fieldAndColumn.isExtend()) {
				throw new FieldAndColumnException(fieldAndColumn,
						modelClass.getName() + "的" + fieldName + "字段映射的列是一个拓展列！");
			}
			columns.add(fieldAndColumn.getColumn());
		}
		return createSelectSqlFragmentByColumns(modelClass, columns);
	}

	@Override
	public SelectSqlFragment createSelectSqlFragmentByColumns(Class<? extends Modelable> modelClass,
			List<String> columns) {
		if (CollectionUtils.isEmpty(columns)) {
			throw new UnsupportedOperationException("缺失查询的列！");
		}
		ModelAndTable modelAndTable = modelManager.getModelAndTable(modelClass);
		return createSelectSqlFragment(
				getDialect().getBaseSelectSql(modelAndTable.getTableName(), modelAndTable.getTableAlias(), columns));
	}

	@Override
	public CountSqlFragment createCountSqlFragment(Class<? extends Modelable> modelClass) {
		ModelAndTable modelAndTable = modelManager.getModelAndTable(modelClass);
		String countSql = modelAndTable.getCountSql();
		Object[] params = {};
		if (StringUtils.isEmpty(countSql)) {
			SelectSqlFragment selectSqlFragment = createSelectSqlFragment(modelClass);
			BoundSql boundSql = selectSqlFragment.getBoundSql();
			countSql = countSqlParser.getSmartCountSql(boundSql.getSql());
			params = boundSql.getParams();
		}
		return createCountSqlFragment(countSql, params);
	}

	@Override
	public ModelManager getModelManager() {
		return this.modelManager;
	}

}

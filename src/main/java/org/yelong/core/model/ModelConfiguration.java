/**
 * 
 */
package org.yelong.core.model;

import java.util.Objects;

import org.yelong.core.jdbc.dialect.Dialect;
import org.yelong.core.jdbc.sql.condition.support.ConditionResolver;
import org.yelong.core.model.resolve.ModelAndTableManager;
import org.yelong.core.model.sql.ModelSqlFragmentFactory;
import org.yelong.core.model.sql.SqlModelResolver;

/**
 * model核心配置
 * 
 * @author PengFei
 */
public class ModelConfiguration {

	private Dialect dialect;
	
	private ModelProperties modelProperties;
	
	private ModelAndTableManager modelAndTableManager;
	
	private ModelSqlFragmentFactory modelSqlFragmentFactory;
	
	private ConditionResolver conditionResolver;
	
	private SqlModelResolver sqlModelResolver;
	
	public ModelConfiguration(Dialect dialect, ModelProperties modelProperties,
			ModelAndTableManager modelAndTableManager , ModelSqlFragmentFactory modelSqlFragmentFactory,
			ConditionResolver conditionResolver, SqlModelResolver sqlModelResolver) {
		this.dialect = dialect;
		this.modelProperties = modelProperties;
		this.modelAndTableManager = modelAndTableManager;
		this.modelSqlFragmentFactory = modelSqlFragmentFactory;
		this.conditionResolver = conditionResolver;
		this.sqlModelResolver = sqlModelResolver;
	}

	public ModelAndTableManager getModelAndTableManager() {
		return modelAndTableManager;
	}

	public void setModelAndTableManager(ModelAndTableManager modelAndTableManager) {
		Objects.requireNonNull(modelAndTableManager);
		this.modelAndTableManager = modelAndTableManager;
	}

	public ModelSqlFragmentFactory getModelSqlFragmentFactory() {
		return modelSqlFragmentFactory;
	}

	public void setModelSqlFragmentFactory(ModelSqlFragmentFactory modelSqlFragmentFactory) {
		Objects.requireNonNull(modelSqlFragmentFactory);
		this.modelSqlFragmentFactory = modelSqlFragmentFactory;
	}

	public SqlModelResolver getSqlModelResolver() {
		return sqlModelResolver;
	}

	public void setSqlModelResolver(SqlModelResolver sqlModelResolver) {
		Objects.requireNonNull(sqlModelResolver);
		this.sqlModelResolver = sqlModelResolver;
	}

	public ModelProperties getModelProperties() {
		return modelProperties;
	}

	public ConditionResolver getConditionResolver() {
		return conditionResolver;
	}

	public void setConditionResolver(ConditionResolver conditionResolver) {
		this.conditionResolver = conditionResolver;
	}

	public Dialect getDialect() {
		return dialect;
	}

	public void setDialect(Dialect dialect) {
		Objects.requireNonNull(dialect);
		this.dialect = dialect;
	}

	public void setModelProperties(ModelProperties modelProperties) {
		Objects.requireNonNull(modelProperties);
		this.modelProperties = modelProperties;
	}
	
}

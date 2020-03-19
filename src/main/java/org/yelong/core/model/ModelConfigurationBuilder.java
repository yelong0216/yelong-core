/**
 * 
 */
package org.yelong.core.model;

import org.yelong.core.jdbc.dialect.Dialect;
import org.yelong.core.jdbc.sql.condition.support.ConditionResolver;
import org.yelong.core.jdbc.sql.condition.support.DefaultConditionResolver;
import org.yelong.core.model.resolve.AnnotationModelResolver;
import org.yelong.core.model.resolve.ModelAndTableManager;
import org.yelong.core.model.sql.DefaultModelSqlFragmentFactory;
import org.yelong.core.model.sql.DefaultSqlModelResolver;
import org.yelong.core.model.sql.ModelSqlFragmentFactory;
import org.yelong.core.model.sql.SqlModelResolver;

/**
 * @author PengFei
 * @date 2020年3月2日上午10:57:19
 * @since 1.0
 */
public class ModelConfigurationBuilder {
	
	private final Dialect dialect;
	
	private final ModelProperties modelProperties;
	
	private ModelAndTableManager modelAndTableManager;
	
	private ModelSqlFragmentFactory modelSqlFragmentFactory;
	
	private ConditionResolver conditionResolver;
	
	private SqlModelResolver sqlModelResolver;
	
	public ModelConfigurationBuilder(Dialect dialect,ModelProperties modelProperties) {
		this.dialect = dialect;
		this.modelProperties = modelProperties;
		this.modelAndTableManager = new ModelAndTableManager(new AnnotationModelResolver(modelProperties));
		this.modelSqlFragmentFactory = new DefaultModelSqlFragmentFactory(dialect, modelAndTableManager);
		this.conditionResolver = new DefaultConditionResolver(modelSqlFragmentFactory);
		this.sqlModelResolver = new DefaultSqlModelResolver(modelAndTableManager, conditionResolver);
	}
	
	public void setModelAndTableManager(ModelAndTableManager modelAndTableManager) {
		this.modelAndTableManager = modelAndTableManager;
	}

	public void setModelSqlFragmentFactory(ModelSqlFragmentFactory modelSqlFragmentFactory) {
		this.modelSqlFragmentFactory = modelSqlFragmentFactory;
	}

	public void setSqlModelResolver(SqlModelResolver sqlModelResolver) {
		this.sqlModelResolver = sqlModelResolver;
	}

	public void setConditionResolver(ConditionResolver conditionResolver) {
		this.conditionResolver = conditionResolver;
	}

	public ModelConfiguration build() {
		return new ModelConfiguration(dialect, modelProperties, modelAndTableManager, modelSqlFragmentFactory, conditionResolver, sqlModelResolver);
	}
	
}

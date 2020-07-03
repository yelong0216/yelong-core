/**
 * 
 */
package org.yelong.core.model;

import java.util.Objects;

import org.yelong.core.jdbc.dialect.Dialect;
import org.yelong.core.jdbc.sql.condition.support.ConditionResolver;
import org.yelong.core.jdbc.sql.condition.support.DefaultConditionResolver;
import org.yelong.core.model.property.ModelProperty;
import org.yelong.core.model.resolve.AnnotationModelResolver;
import org.yelong.core.model.resolve.ModelAndTableManager;
import org.yelong.core.model.sql.DefaultModelSqlFragmentFactory;
import org.yelong.core.model.sql.DefaultSqlModelResolver;
import org.yelong.core.model.sql.ModelSqlFragmentFactory;
import org.yelong.core.model.sql.SqlModelResolver;

/**
 * {@link ModelConfiguration}的建造者
 * 
 * @author PengFei
 */
public class ModelConfigurationBuilder {

	private Dialect dialect;

	@SuppressWarnings("deprecation")
	private ModelProperties modelProperties;

	private ModelAndTableManager modelAndTableManager;

	private ModelSqlFragmentFactory modelSqlFragmentFactory;

	private ConditionResolver conditionResolver;

	private SqlModelResolver sqlModelResolver;

	private ModelProperty modelProperty;

	public ModelConfigurationBuilder() {

	}

	/**
	 * @param dialect 数据库方言
	 */
	public ModelConfigurationBuilder(Dialect dialect) {
		this.dialect = dialect;
	}

	/**
	 * @param dialect         数据库方言
	 * @param modelProperties 模型属性配置
	 */
	@SuppressWarnings("deprecation")
	public ModelConfigurationBuilder(Dialect dialect, ModelProperties modelProperties) {
		this.dialect = dialect;
		this.modelProperties = modelProperties;
	}

	// =======================set====================

	public ModelConfigurationBuilder setModelAndTableManager(ModelAndTableManager modelAndTableManager) {
		this.modelAndTableManager = modelAndTableManager;
		return this;
	}

	public ModelConfigurationBuilder setModelSqlFragmentFactory(ModelSqlFragmentFactory modelSqlFragmentFactory) {
		this.modelSqlFragmentFactory = modelSqlFragmentFactory;
		return this;
	}

	public ModelConfigurationBuilder setSqlModelResolver(SqlModelResolver sqlModelResolver) {
		this.sqlModelResolver = sqlModelResolver;
		return this;
	}

	public ModelConfigurationBuilder setConditionResolver(ConditionResolver conditionResolver) {
		this.conditionResolver = conditionResolver;
		return this;
	}

	public ModelConfigurationBuilder setDialect(Dialect dialect) {
		this.dialect = dialect;
		return this;
	}

	@SuppressWarnings("deprecation")
	public void setModelProperties(ModelProperties modelProperties) {
		this.modelProperties = modelProperties;
	}

	public void setModelProperty(ModelProperty modelProperty) {
		this.modelProperty = modelProperty;
	}

	// =======================get====================

	public Dialect getDialect() {
		return dialect;
	}

	@SuppressWarnings("deprecation")
	public ModelProperties getModelProperties() {
		return modelProperties;
	}

	public ModelAndTableManager getModelAndTableManager() {
		return modelAndTableManager;
	}

	public ModelSqlFragmentFactory getModelSqlFragmentFactory() {
		return modelSqlFragmentFactory;
	}

	public ConditionResolver getConditionResolver() {
		return conditionResolver;
	}

	public SqlModelResolver getSqlModelResolver() {
		return sqlModelResolver;
	}

	public ModelProperty getModelProperty() {
		return modelProperty;
	}

	// =======================build====================

	/**
	 * 构建模型配置
	 * 
	 * @return {@link ModelConfiguration}
	 */
	@SuppressWarnings("deprecation")
	public ModelConfiguration build() {
		Objects.requireNonNull(dialect, "dialect not allow to null");
		if (null == modelProperties) {
			this.modelProperties = new ModelProperties();
		}
		if (null == modelAndTableManager) {
			this.modelAndTableManager = new ModelAndTableManager(new AnnotationModelResolver(modelProperties));
		}
		if (null == modelSqlFragmentFactory) {
			this.modelSqlFragmentFactory = new DefaultModelSqlFragmentFactory(dialect.getSqlFragmentFactory(),
					modelAndTableManager);
		}
		if (null == conditionResolver) {
			this.conditionResolver = new DefaultConditionResolver(modelSqlFragmentFactory);
		}
		if (null == sqlModelResolver) {
			this.sqlModelResolver = new DefaultSqlModelResolver(modelAndTableManager, conditionResolver);
		}
		ModelConfiguration modelConfiguration = new ModelConfiguration(dialect, modelProperties, modelAndTableManager,
				modelSqlFragmentFactory, conditionResolver, sqlModelResolver);
		if (null != modelProperty) {
			modelConfiguration.setModelProperty(modelProperty);
		}
		return modelConfiguration;
	}

}

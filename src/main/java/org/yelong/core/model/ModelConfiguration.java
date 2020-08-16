/**
 * 
 */
package org.yelong.core.model;

import org.yelong.core.jdbc.dialect.Dialect;
import org.yelong.core.jdbc.sql.condition.support.ConditionResolver;
import org.yelong.core.model.manage.ModelAndTable;
import org.yelong.core.model.manage.ModelManager;
import org.yelong.core.model.property.ModelProperty;
import org.yelong.core.model.sql.ModelSqlFragmentFactory;
import org.yelong.core.model.sql.SqlModelResolver;
import org.yelong.core.model.validator.ModelValidator;

/**
 * model核心配置
 * 
 * @since 1.0
 */
public class ModelConfiguration {

	protected Dialect dialect;

	protected ModelProperties modelProperties;

	protected ModelManager modelManager;

	protected ModelSqlFragmentFactory modelSqlFragmentFactory;

	protected ConditionResolver conditionResolver;

	protected SqlModelResolver sqlModelResolver;

	protected ModelProperty modelProperty;

	protected ModelValidator modelValidator;

	protected ModelConfiguration() {
	}

	protected ModelConfiguration(final Dialect dialect) {
		this.dialect = dialect;
	}

	public ModelManager getModelManager() {
		return modelManager;
	}

	public ModelSqlFragmentFactory getModelSqlFragmentFactory() {
		return modelSqlFragmentFactory;
	}

	public SqlModelResolver getSqlModelResolver() {
		return sqlModelResolver;
	}

	public ModelProperties getModelProperties() {
		return modelProperties;
	}

	public ConditionResolver getConditionResolver() {
		return conditionResolver;
	}

	public Dialect getDialect() {
		return dialect;
	}

	public ModelProperty getModelProperty() {
		return modelProperty;
	}

	public ModelValidator getModelValidator() {
		return modelValidator;
	}

	// ==================================================support==================================================

	/**
	 * @param modelClass model class
	 * @return 模型对应的模型表
	 * @see ModelManager#getModelAndTable(Class)
	 */
	public ModelAndTable getModelAndTable(Class<? extends Modelable> modelClass) {
		return getModelManager().getModelAndTable(modelClass);
	}

	/**
	 * @param modelClass model class
	 * @return 模型映射的表名称
	 * @see ModelManager#getTableName(Class)
	 */
	public String getTableName(Class<? extends Modelable> modelClass) {
		return getModelManager().getTableName(modelClass);
	}

}

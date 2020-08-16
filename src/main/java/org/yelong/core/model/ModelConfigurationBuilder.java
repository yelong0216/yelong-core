/**
 * 
 */
package org.yelong.core.model;

import java.util.Objects;

import org.yelong.core.jdbc.dialect.Dialect;
import org.yelong.core.jdbc.sql.condition.support.ConditionResolver;
import org.yelong.core.jdbc.sql.condition.support.DefaultConditionResolver;
import org.yelong.core.model.manage.DefaultModelManager;
import org.yelong.core.model.manage.ModelManager;
import org.yelong.core.model.pojo.POJOModelResolver;
import org.yelong.core.model.pojo.annotation.AnnotationFieldResolver;
import org.yelong.core.model.pojo.annotation.AnnotationPOJOModelResolver;
import org.yelong.core.model.property.DefaultModelProperty;
import org.yelong.core.model.property.ModelProperty;
import org.yelong.core.model.resolve.DefaultModelResolverManager;
import org.yelong.core.model.resolve.ModelResolverManager;
import org.yelong.core.model.sql.DefaultModelSqlFragmentFactory;
import org.yelong.core.model.sql.DefaultSqlModelResolver;
import org.yelong.core.model.sql.ModelSqlFragmentFactory;
import org.yelong.core.model.sql.SqlModelResolver;
import org.yelong.core.model.validator.DefaultModelValidator;
import org.yelong.core.model.validator.ModelValidator;

/**
 * @since 2.0
 */
public class ModelConfigurationBuilder {

	private final ModelConfiguration mc;

	private ModelConfigurationBuilder(ModelConfiguration mc) {
		this.mc = Objects.requireNonNull(mc);
	}

	public static ModelConfigurationBuilder create(Dialect dialect) {
		return create(new ModelConfiguration(Objects.requireNonNull(dialect)));
	}

	public static ModelConfigurationBuilder create(final ModelConfiguration modelConfiguration) {
		return new ModelConfigurationBuilder(modelConfiguration);
	}

	public ModelConfigurationBuilder setModelManager(ModelManager modelManager) {
		mc.modelManager = Objects.requireNonNull(modelManager);
		return this;
	}

	public ModelConfigurationBuilder setModelSqlFragmentFactory(ModelSqlFragmentFactory modelSqlFragmentFactory) {
		mc.modelSqlFragmentFactory = Objects.requireNonNull(modelSqlFragmentFactory);
		return this;
	}

	public ModelConfigurationBuilder setSqlModelResolver(SqlModelResolver sqlModelResolver) {
		mc.sqlModelResolver = Objects.requireNonNull(sqlModelResolver);
		return this;
	}

	public ModelConfigurationBuilder setConditionResolver(ConditionResolver conditionResolver) {
		mc.conditionResolver = Objects.requireNonNull(conditionResolver);
		return this;
	}

	public ModelConfigurationBuilder setModelProperties(ModelProperties modelProperties) {
		mc.modelProperties = Objects.requireNonNull(modelProperties);
		return this;
	}

	public ModelConfigurationBuilder setModelProperty(ModelProperty modelProperty) {
		mc.modelProperty = modelProperty;
		return this;
	}

	public ModelConfigurationBuilder setModelValidator(ModelValidator modelValidator) {
		mc.modelValidator = Objects.requireNonNull(modelValidator);
		return this;
	}

	public ModelConfiguration build() {
		if (null == mc.modelProperties) {
			setModelProperties(new ModelProperties());
		}
		if (null == mc.modelManager) {
			ModelResolverManager modelResolverManager = new DefaultModelResolverManager();
			POJOModelResolver modelResolver = new AnnotationPOJOModelResolver();
			modelResolver.registerFieldResovler(new AnnotationFieldResolver());
			modelResolverManager.registerModelResolver(modelResolver);
			ModelManager modelManager = new DefaultModelManager(modelResolverManager);
			setModelManager(modelManager);
		}
		if (null == mc.modelSqlFragmentFactory) {
			setModelSqlFragmentFactory(
					new DefaultModelSqlFragmentFactory(mc.dialect.getSqlFragmentFactory(), mc.modelManager));
		}
		if (null == mc.conditionResolver) {
			setConditionResolver(new DefaultConditionResolver(mc.modelSqlFragmentFactory));
		}
		if (null == mc.sqlModelResolver) {
			setSqlModelResolver(new DefaultSqlModelResolver(mc.modelManager, mc.conditionResolver));
		}
		if (null == mc.modelProperty) {
			setModelProperty(DefaultModelProperty.INSTANCE);
		}
		if (null == mc.modelValidator) {
			setModelValidator(new DefaultModelValidator(mc.modelManager, mc.modelProperty));
		}
		return mc;
	}

}

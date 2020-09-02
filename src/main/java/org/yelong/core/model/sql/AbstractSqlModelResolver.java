/**
 * 
 */
package org.yelong.core.model.sql;

import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.yelong.core.annotation.Nullable;
import org.yelong.core.jdbc.sql.condition.ConditionalOperatorResolver;
import org.yelong.core.jdbc.sql.condition.support.ConditionResolver;
import org.yelong.core.jdbc.sql.factory.SqlFragmentFactory;
import org.yelong.core.model.Modelable;
import org.yelong.core.model.manage.ModelManager;
import org.yelong.core.model.property.ModelProperty;

/**
 * 抽象的SqlModel解析器
 * 
 * @since 2.1
 */
public abstract class AbstractSqlModelResolver implements SqlModelResolver {

	/**
	 * 默认的条件运算符
	 */
	public static final String DEFAULT_OPERATOR = "=";

	/**
	 * 表别名与列名的连接符
	 */
	public static final String DOT = ".";

	protected final ModelManager modelManager;

	protected final ConditionResolver conditionResolver;

	protected final SqlFragmentFactory sqlFragmentFactory;

	protected final ModelProperty modelProperty;

	protected final ConditionalOperatorResolver conditionalOperatorResolver;

	public AbstractSqlModelResolver(ModelManager modelManager, ConditionResolver conditionResolver,
			SqlFragmentFactory sqlFragmentFactory, ModelProperty modelProperty) {
		this.modelManager = Objects.requireNonNull(modelManager);
		this.conditionResolver = Objects.requireNonNull(conditionResolver);
		this.sqlFragmentFactory = Objects.requireNonNull(sqlFragmentFactory);
		this.modelProperty = Objects.requireNonNull(modelProperty);
		this.conditionalOperatorResolver = conditionResolver.getSqlFragmentFactory().getDialect()
				.getConditionalOperatorResolver();
	}

	/**
	 * 列添加表别名
	 * 
	 * @param column     列名
	 * @param tableAlias 表别名 如果别名为null 直接返回列名
	 * @return 如果列存在“.”字符则之间返回，否则返回 tableAlias.column
	 */
	protected String columnAddTableAlias(String column, @Nullable String tableAlias) {
		if (StringUtils.isBlank(tableAlias)) {
			return column;
		}
		if (column.contains(DOT)) {
			return column;
		}
		return tableAlias + DOT + column;
	}

	protected <M extends Modelable, V> V getModelProperty(M model, String property) {
		return modelProperty.get(model, property);
	}

	@Override
	public ModelManager getModelManager() {
		return this.modelManager;
	}

	@Override
	public SqlFragmentFactory getSqlFragmentFactory() {
		return this.sqlFragmentFactory;
	}

	@Override
	public ModelProperty getModelProperty() {
		return this.modelProperty;
	}

	@Override
	public ConditionResolver getConditionResolver() {
		return this.conditionResolver;
	}
	
}
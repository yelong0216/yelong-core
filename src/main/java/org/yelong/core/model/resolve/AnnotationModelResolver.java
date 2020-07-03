/**
 * 
 */
package org.yelong.core.model.resolve;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.yelong.commons.lang.Strings;
import org.yelong.core.model.ModelProperties;
import org.yelong.core.model.Modelable;
import org.yelong.core.model.annotation.SelectColumnConditionalOnProperty;
import org.yelong.core.model.annotation.Table;
import org.yelong.core.model.annotation.Transient;
import org.yelong.core.model.exception.ModelException;

/**
 * 注解模型解析器
 * 
 * 通过注解方式解析model映射为ModelAndTable
 * 
 * 如果此modelClass未标注{@link Table}注解，将通过父类来尝试获取{@link Table}注解，直至{@link Object}类。<br/>
 * 均未找到Table注解则表示无法解析此类为ModelAndTable
 * 
 * @author PengFei
 */
@SuppressWarnings("deprecation")
public class AnnotationModelResolver implements ModelResolver {

	@SuppressWarnings("unused")
	private ModelProperties modelProperties;

	public AnnotationModelResolver() {

	}

	public AnnotationModelResolver(ModelProperties modelProperties) {
		this.modelProperties = modelProperties;
	}

	@Override
	public <M extends Modelable> ModelAndTable resolve(Class<M> modelClass) throws ModelResolveException {
		ModelClassAnnotation modelClassAnnotation = new ModelClassAnnotation(modelClass);
		if (null == modelClassAnnotation.getTable()) {
			throw new ModelResolveException("[" + modelClass.getName() + "]及其父类均未标有Table注解，无法进行解析！");
		}
		DefaultModelAndTable modelAndTable = new DefaultModelAndTable(modelClass,
				Strings.requireNonBlank(modelClassAnnotation.getTableName(), "[" + modelClass + "]声明的表名不允许为空!"));
		List<Field> fields = FieldUtils.getAllFieldsList(modelClass);
		fields = fields.stream().filter(x -> !x.isAnnotationPresent(Transient.class))// 排除忽略的字段
				.filter(x -> !Modifier.isStatic(x.getModifiers()))// 排除静态字段
				.filter(x -> !Modifier.isFinal(x.getModifiers()))// 排除常量
				.collect(Collectors.toList());
		// 不允许存在相同的字段名称（父子类中存在相同的字段名称）
		List<String> fieldNames = new LinkedList<String>();
		List<FieldAndColumn> fieldAndColumns = new ArrayList<FieldAndColumn>(fields.size());
		for (Field field : fields) {
			String fieldName = field.getName();
			if (fieldNames.contains(fieldName)) {
				throw new ModelResolveException(modelClass + "中存在相同的字段名称[" + fieldName + "]");
			}
			FieldAndColumn fieldAndColumn = resolveToFieldAndColumn(modelAndTable, field);
			fieldAndColumns.add(fieldAndColumn);
			fieldNames.add(fieldName);
		}
		modelAndTable.setFieldAndColumns(fieldAndColumns);
		abstractModelAndTableSetProperty(modelAndTable, modelClassAnnotation);
		return modelAndTable;
	}

	/**
	 * 解析字段为字段列
	 * 
	 * @param modelAndTable 字段列所属的模型表
	 * @param field         字段
	 * @return 字段列
	 */
	public FieldAndColumn resolveToFieldAndColumn(ModelAndTable modelAndTable, Field field) throws ModelException {
		ModelFieldAnnotation modelFieldAnnotation = new ModelFieldAnnotation(field);
		SelectColumnCondition selectColumnCondition = null;
		if (field.isAnnotationPresent(SelectColumnConditionalOnProperty.class)) {
			SelectColumnConditionalOnProperty selectColumnConditionalOnProperty = field
					.getAnnotation(SelectColumnConditionalOnProperty.class);
			String property = StringUtils.isNotEmpty(selectColumnConditionalOnProperty.value()) ? // 如果value不为空则为value
					selectColumnConditionalOnProperty.value()
					: StringUtils.isNotEmpty(selectColumnConditionalOnProperty.name()) ? // 如果name不为空则为name
							selectColumnConditionalOnProperty.name() : field.getName();// 否则为字段名称
			selectColumnCondition = new SelectColumnCondition(property, selectColumnConditionalOnProperty.havingValue(),
					selectColumnConditionalOnProperty.matchIfMissing());
		}
		DefaultFieldAndColumn fieldAndColumn = new DefaultFieldAndColumn(modelAndTable, field,
				modelFieldAnnotation.getColumnCode());
		abstractFieldAndColumnSetProperty(fieldAndColumn, modelFieldAnnotation);
		fieldAndColumn.setSelectColumnCondition(selectColumnCondition);
		return fieldAndColumn;
	}

	/**
	 * field and column 设置属性
	 * 
	 * @param fieldAndColumn       field and column
	 * @param modelFieldAnnotation model field annotation
	 */
	protected void abstractFieldAndColumnSetProperty(AbstractFieldAndColumn fieldAndColumn,
			ModelFieldAnnotation modelFieldAnnotation) {
		fieldAndColumn.setAllowBlank(modelFieldAnnotation.isAllowBlank());
		fieldAndColumn.setAllowNull(modelFieldAnnotation.isAllowNull());
		fieldAndColumn.setColumnName(modelFieldAnnotation.getColumnName());
		fieldAndColumn.setDesc(modelFieldAnnotation.getDesc());
		fieldAndColumn.setExtend(modelFieldAnnotation.isExtendColumn());
		fieldAndColumn.setJdbcType(modelFieldAnnotation.getJdbcType());
		fieldAndColumn.setMaxLength(modelFieldAnnotation.getMaxLength());
		fieldAndColumn.setMinLength(modelFieldAnnotation.getMinLength());
		fieldAndColumn.setPrimaryKey(modelFieldAnnotation.isPrimaryKey());
		fieldAndColumn.setSelectColumn(modelFieldAnnotation.getSelectColumnCode());
		fieldAndColumn.setSelectMapping(modelFieldAnnotation.isSelectMapping());
		// 是拓展列 ，设置拓展列属性
		if (fieldAndColumn.isExtend()) {
			fieldAndColumn.setExtendColumnAttribute(getExtendColumnAttribute(fieldAndColumn, modelFieldAnnotation));
		}
	}

	/**
	 * 获取拓展列属性
	 * 
	 * @param modelFieldAnnotation model field 注解属性
	 * @return 拓展列属性
	 */
	protected ExtendColumnAttribute getExtendColumnAttribute(FieldAndColumn fieldAndColumn,
			ModelFieldAnnotation modelFieldAnnotation) {
		if (!modelFieldAnnotation.isExtendColumn()) {
			return null;
		}
		return new ExtendColumnAttribute(fieldAndColumn, modelFieldAnnotation.getExtendColumnModelClass(),
				modelFieldAnnotation.getExtendColumnTableName(), modelFieldAnnotation.getExtendColumnTableAlias());
	}

	/**
	 * model and table 设置属性
	 * 
	 * @param modelAndTable        model and table
	 * @param modelClassAnnotation model class annotation
	 */
	protected void abstractModelAndTableSetProperty(AbstractModelAndTable modelAndTable,
			ModelClassAnnotation modelClassAnnotation) {
		modelAndTable.setTableAlias(modelClassAnnotation.getTableAlias());
		modelAndTable.setTableDesc(modelClassAnnotation.getTableDese());
	}

}

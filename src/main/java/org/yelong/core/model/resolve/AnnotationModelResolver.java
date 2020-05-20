/**
 * 
 */
package org.yelong.core.model.resolve;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
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
 * 通过注解方式解析model映射为ModelAndTable
 * 
 * 如果此modelClass未标注{@link Table}注解，将通过父类来尝试获取{@link Table}注解，直至{@link Object}类。
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
	public <M extends Modelable> ModelAndTable resolve(Class<M> modelClass) throws ModelResolveException{
		ModelClassAnnotation modelClassAnnotation = new ModelClassAnnotation(modelClass);
		if( null == modelClassAnnotation.getTable()) {
			throw new ModelResolveException("["+modelClass.getName()+"]及其父类均未标有Table注解，无法进行解析！");
		}
		DefaultModelAndTable modelAndTable = new DefaultModelAndTable(modelClass, Strings.requireNonBlank(modelClassAnnotation.getTableName(), "["+modelClass+"]声明的表名不允许为空!"));
		List<Field> fields = FieldUtils.getAllFieldsList(modelClass);
		fields = fields.stream().filter(x->!x.isAnnotationPresent(Transient.class))//排除忽略的字段
				.filter(x->!Modifier.isStatic(x.getModifiers()))//排除静态字段
				.filter(x->!Modifier.isFinal(x.getModifiers()))//排除常量
				.collect(Collectors.toList());
		List<FieldAndColumn> fieldAndColumns = new ArrayList<FieldAndColumn>(fields.size());
		for (Field field : fields) {
			FieldAndColumn fieldAndColumn = resolveToFieldAndColumn(modelAndTable,field);
			fieldAndColumns.add(fieldAndColumn);
		}
		modelAndTable.setFieldAndColumns(fieldAndColumns);
		abstractModelAndTableSetProperty(modelAndTable, modelClassAnnotation);
		return modelAndTable;
	}

	/**
	 * 解析字段为字段列
	 * @param modelAndTable 字段列所属的模型表
	 * @param field 字段
	 * @return 字段列
	 */
	public FieldAndColumn resolveToFieldAndColumn(ModelAndTable modelAndTable ,Field field) throws ModelException{
		ModelFieldAnnotation modelFieldAnnotation = new ModelFieldAnnotation(field);
		SelectColumnCondition selectColumnCondition = null;
		if(field.isAnnotationPresent(SelectColumnConditionalOnProperty.class)) {
			SelectColumnConditionalOnProperty selectColumnConditionalOnProperty = field.getAnnotation(SelectColumnConditionalOnProperty.class);
			String property = StringUtils.isNotEmpty(selectColumnConditionalOnProperty.value()) ? //如果value不为空则为value
					selectColumnConditionalOnProperty.value() : StringUtils.isNotEmpty(selectColumnConditionalOnProperty.name()) ? //如果name不为空则为name
							selectColumnConditionalOnProperty.name() : field.getName();//否则为字段名称
			selectColumnCondition = new SelectColumnCondition(property, selectColumnConditionalOnProperty.havingValue(), selectColumnConditionalOnProperty.matchIfMissing());
		}
		DefaultFieldAndColumn fieldAndColumn = new DefaultFieldAndColumn(modelAndTable,field,modelFieldAnnotation.getColumnCode());
		abstractFieldAndColumnSetProperty(fieldAndColumn, modelFieldAnnotation);
		fieldAndColumn.setSelectColumnCondition(selectColumnCondition);
		return fieldAndColumn;
	}
	
	protected void abstractFieldAndColumnSetProperty(AbstractFieldAndColumn fieldAndColumn , ModelFieldAnnotation modelFieldAnnotation) {
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
	}
	
	protected void abstractModelAndTableSetProperty(AbstractModelAndTable modelAndTable , ModelClassAnnotation modelClassAnnotation) {
		modelAndTable.setTableAlias(modelClassAnnotation.getTableAlias());
		modelAndTable.setTableDesc(modelClassAnnotation.getTableDese());
	}
	
}

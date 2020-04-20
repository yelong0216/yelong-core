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
import org.yelong.commons.annotation.AnnotationUtils;
import org.yelong.core.model.Model;
import org.yelong.core.model.ModelProperties;
import org.yelong.core.model.annotation.Column;
import org.yelong.core.model.annotation.ExtendColumn;
import org.yelong.core.model.annotation.Id;
import org.yelong.core.model.annotation.PrimaryKey;
import org.yelong.core.model.annotation.SelectColumn;
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
public class AnnotationModelResolver implements ModelResolver {

	private ModelProperties modelProperties;

	public AnnotationModelResolver(ModelProperties modelProperties) {
		this.modelProperties = modelProperties;
	}

	@Override
	public <M extends Model> ModelAndTable resolve(Class<M> modelClass) throws ModelResolveException{
		Table table = AnnotationUtils.getAnnotation(modelClass, Table.class, true);
		if( null == table ) {
			throw new ModelResolveException("["+modelClass.getName()+"]及其父类均未标有Table注解，无法进行解析！");
		}
		String tableName = table.value();
		String tableAlias = table.alias();
		if(StringUtils.isEmpty(tableAlias)) {
			tableAlias = modelClass.getSimpleName().substring(0, 1).toLowerCase()+modelClass.getSimpleName().substring(1);
		}
		String tableDesc = table.desc();
		DefaultModelAndTable modelAndTable = new DefaultModelAndTable(modelClass, tableName);
		List<Field> fields = FieldUtils.getAllFieldsList(modelClass);
		fields = fields.stream().filter(x->!x.isAnnotationPresent(Transient.class))//排除忽略的字段
				.filter(x->!Modifier.isStatic(x.getModifiers()))//排除静态字段
				.filter(x->!Modifier.isFinal(x.getModifiers()))//排除常量
				.collect(Collectors.toList());
		List<FieldAndColumn> fieldAndColumns = new ArrayList<FieldAndColumn>(fields.size());
		for (Field field : fields) {
//			FieldAndColumn fieldAndColumn = getFieldAndColumn(field);
			FieldAndColumn fieldAndColumn = resolveToFieldAndColumn(modelAndTable,field);
			fieldAndColumns.add(fieldAndColumn);
		}
		modelAndTable.setFieldAndColumns(fieldAndColumns);
//		DefaultModelAndTable modelAndTable = new DefaultModelAndTable(modelClass, tableName, fieldAndColumns);
		modelAndTable.setTableAlias(tableAlias);
		modelAndTable.setTableDesc(tableDesc);
		return modelAndTable;
	}

	/**
	 * 获取字段映射的字段列
	 * @param field
	 * @return
	 */
	@Deprecated
	public FieldAndColumn getFieldAndColumn(Field field) throws ModelException{
		//列名称
		String column = "";
		//查询时映射的列名称
		String selectColumn = "";
		//列名称
		String columnName = "";
		Long minLength = 0L;
		Long maxLength = Long.MAX_VALUE;
		boolean allowBlank = true;
		boolean allowNull = true;
		String jdbcType = "";
		String desc = "";
		boolean isPrimaryKey = false;
		boolean isExtend = false;
		if( field.isAnnotationPresent(Column.class) ){
			Column c = field.getAnnotation(Column.class);
			column = c.column();
			if(StringUtils.isBlank(column)) {
				column = c.value();
			}
			minLength = c.minLength();
			maxLength = c.maxLength();
			if( CharSequence.class.isAssignableFrom(field.getType()) ) {
				allowBlank = c.allowBlank();
				if( !allowBlank ) {
					allowNull = false;
				} else {
					allowNull = c.allowNull();
				}
			} else {
				allowNull = c.allowNull();
			}
			columnName = c.columnName();
			jdbcType = c.jdbcType();
			desc = c.desc();
		}
		//默认列名称为字段名称
		if(StringUtils.isEmpty(column)) {
			column = field.getName();
		}
		if( modelProperties.isCamelCaseToUnderscore() ) {
			column = org.yelong.commons.lang.StringUtils.camelCaseToUnderscore(column);
		} 
		//id列默认不允许为空
		if( field.isAnnotationPresent(Id.class) || field.isAnnotationPresent(PrimaryKey.class)) {
			allowBlank = false;
			allowNull = false;
			isPrimaryKey = true;
			isExtend = false;
		} else if(field.isAnnotationPresent(ExtendColumn.class)) {
			isExtend = true;
		}
		SelectColumnCondition selectColumnCondition = null;
		if(field.isAnnotationPresent(SelectColumnConditionalOnProperty.class)) {
			SelectColumnConditionalOnProperty selectColumnConditionalOnProperty = field.getAnnotation(SelectColumnConditionalOnProperty.class);
			String property = StringUtils.isNotEmpty(selectColumnConditionalOnProperty.value()) ? //如果value不为空则为value
					selectColumnConditionalOnProperty.value() : StringUtils.isNotEmpty(selectColumnConditionalOnProperty.name()) ? //如果name不为空则为name
							selectColumnConditionalOnProperty.name() : field.getName();//否则为字段名称
			selectColumnCondition = new SelectColumnCondition(property, selectColumnConditionalOnProperty.havingValue(), selectColumnConditionalOnProperty.matchIfMissing());
		}
		selectColumn = getSelectColumnName(field, column);
		DefaultFieldAndColumn fieldAndColumn = new DefaultFieldAndColumn(field, column);
		fieldAndColumn.setAllowBlank(allowBlank);
		fieldAndColumn.setAllowNull(allowNull);
		fieldAndColumn.setDesc(desc);
		fieldAndColumn.setColumnName(columnName);
		fieldAndColumn.setExtend(isExtend);
		fieldAndColumn.setJdbcType(jdbcType);
		fieldAndColumn.setMaxLength(maxLength);
		fieldAndColumn.setMinLength(minLength);
		fieldAndColumn.setPrimaryKey(isPrimaryKey);
		fieldAndColumn.setSelectColumn(selectColumn);
		fieldAndColumn.setSelectColumnCondition(selectColumnCondition);
		return fieldAndColumn;
	}

	/**
	 * 解析字段为字段列
	 * @param modelAndTable 字段列所属的模型表
	 * @param field 字段
	 * @return 字段列
	 */
	public FieldAndColumn resolveToFieldAndColumn(ModelAndTable modelAndTable ,Field field) throws ModelException{
		//列名称
		String column = "";
		//查询时映射的列名称
		String selectColumn = "";
		//列名称
		String columnName = "";
		Long minLength = 0L;
		Long maxLength = Long.MAX_VALUE;
		boolean allowBlank = true;
		boolean allowNull = true;
		String jdbcType = "";
		String desc = "";
		boolean isPrimaryKey = false;
		boolean isExtend = false;
		if( field.isAnnotationPresent(Column.class) ){
			Column c = field.getAnnotation(Column.class);
			column = c.column();
			if(StringUtils.isBlank(column)) {
				column = c.value();
			}
			minLength = c.minLength();
			maxLength = c.maxLength();
			if( CharSequence.class.isAssignableFrom(field.getType()) ) {
				allowBlank = c.allowBlank();
				if( !allowBlank ) {
					allowNull = false;
				} else {
					allowNull = c.allowNull();
				}
			} else {
				allowNull = c.allowNull();
			}
			columnName = c.columnName();
			jdbcType = c.jdbcType();
			desc = c.desc();
		}
		//默认列名称为字段名称
		if(StringUtils.isEmpty(column)) {
			column = field.getName();
		}
		if( modelProperties.isCamelCaseToUnderscore() ) {
			column = org.yelong.commons.lang.StringUtils.camelCaseToUnderscore(column);
		} 
		//id列默认不允许为空
		if( field.isAnnotationPresent(Id.class) || field.isAnnotationPresent(PrimaryKey.class)) {
			allowBlank = false;
			allowNull = false;
			isPrimaryKey = true;
			isExtend = false;
		} else if(field.isAnnotationPresent(ExtendColumn.class)) {
			isExtend = true;
		}
		SelectColumnCondition selectColumnCondition = null;
		if(field.isAnnotationPresent(SelectColumnConditionalOnProperty.class)) {
			SelectColumnConditionalOnProperty selectColumnConditionalOnProperty = field.getAnnotation(SelectColumnConditionalOnProperty.class);
			String property = StringUtils.isNotEmpty(selectColumnConditionalOnProperty.value()) ? //如果value不为空则为value
					selectColumnConditionalOnProperty.value() : StringUtils.isNotEmpty(selectColumnConditionalOnProperty.name()) ? //如果name不为空则为name
							selectColumnConditionalOnProperty.name() : field.getName();//否则为字段名称
			selectColumnCondition = new SelectColumnCondition(property, selectColumnConditionalOnProperty.havingValue(), selectColumnConditionalOnProperty.matchIfMissing());
		}
		selectColumn = getSelectColumnName(field, column);
		DefaultFieldAndColumn fieldAndColumn = new DefaultFieldAndColumn(modelAndTable,field, column);
		fieldAndColumn.setAllowBlank(allowBlank);
		fieldAndColumn.setAllowNull(allowNull);
		fieldAndColumn.setDesc(desc);
		fieldAndColumn.setColumnName(columnName);
		fieldAndColumn.setExtend(isExtend);
		fieldAndColumn.setJdbcType(jdbcType);
		fieldAndColumn.setMaxLength(maxLength);
		fieldAndColumn.setMinLength(minLength);
		fieldAndColumn.setPrimaryKey(isPrimaryKey);
		fieldAndColumn.setSelectColumn(selectColumn);
		fieldAndColumn.setSelectColumnCondition(selectColumnCondition);
		return fieldAndColumn;
	}
	
	/**
	 * 获取该字段映射查询的列名称
	 * @param field 字段
	 * @param column 列名称
	 * @return 查询列名称
	 */
	public String getSelectColumnName(Field field,String column) {
		String selectColumn = "";
		//是否映射查询列名称
		if( field.isAnnotationPresent(SelectColumn.class)) {
			selectColumn = field.getAnnotation(SelectColumn.class).value();
			if(StringUtils.isEmpty(selectColumn)) {
				selectColumn = column;
			}
		} else {
			selectColumn = column;
		}
		return selectColumn;
	}

}

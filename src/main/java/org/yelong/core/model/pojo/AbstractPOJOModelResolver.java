/**
 * 
 */
package org.yelong.core.model.pojo;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.yelong.core.model.Modelable;
import org.yelong.core.model.manage.FieldAndColumn;
import org.yelong.core.model.pojo.field.FieldResolveScopePOJO;
import org.yelong.core.model.pojo.field.FieldResolver;
import org.yelong.core.model.pojo.field.FieldResolverException;
import org.yelong.core.model.resolve.ModelResolveException;

/**
 * 抽象的POJO模型解析器实现
 * 
 * @since 2.0
 */
public abstract class AbstractPOJOModelResolver implements POJOModelResolver {

	private final Map<ModelFieldNameEntry, FieldResolver> modelFieldName_fieldResolverMap = new HashMap<>();

	private final Map<Class<? extends Modelable>, FieldResolver> model_fieldResolverMap = new HashMap<>();

	protected <M extends Modelable> List<FieldAndColumn> getFieldAndColumns(Class<M> modelClass) {
		List<Field> fields = FieldUtils.getAllFieldsList(modelClass);
		fields = fields.stream().filter(x -> !Modifier.isStatic(x.getModifiers()))// 排除静态字段
				.filter(x -> !Modifier.isFinal(x.getModifiers()))// 排除常量
				.collect(Collectors.toList());
		// 不允许存在相同的字段名称（父子类中存在相同的字段名称）
		List<String> fieldNames = new LinkedList<String>();
		List<FieldAndColumn> fieldAndColumns = new ArrayList<FieldAndColumn>();
		for (Field field : fields) {
			String fieldName = field.getName();
			if (fieldNames.contains(fieldName)) {
				throw new ModelResolveException(this, modelClass, modelClass + "中存在相同的字段名称[" + fieldName + "]");
			}
			FieldAndColumn fieldAndColumn = resolveFieldToFiledAndColumn(modelClass, field);
			fieldAndColumns.add(fieldAndColumn);
			fieldNames.add(fieldName);
		}
		return fieldAndColumns;
	}

	@Override
	public FieldResolver getOptimalFieldResolver(Class<? extends Modelable> modelClass, Field field) {
		if (!existFieldResolver()) {
			throw new FieldResolverException("没有注册字段解析器");
		}
		ModelFieldNameEntry modelFieldNameEntry = new ModelFieldNameEntry(modelClass, field.getName());
		FieldResolver fieldResolver = modelFieldName_fieldResolverMap.get(modelFieldNameEntry);
		if (null == fieldResolver) {
			List<Class<?>> classes = new ArrayList<Class<?>>();
			classes.add(modelClass);
			classes.addAll(ClassUtils.getAllSuperclasses(modelClass));
			classes.addAll(ClassUtils.getAllInterfaces(modelClass));
			for (Class<?> cls : classes) {
				fieldResolver = model_fieldResolverMap.get(cls);
				if (null != fieldResolver) {
					return fieldResolver;
				}
			}
		}
		if (null != fieldResolver) {
			return fieldResolver;
		}
		throw new ModelResolveException(this, modelClass,
				"没有找到可以解析字段 " + modelClass + " . " + field.getName() + " 的解析器");
	}

	@Override
	public void registerFieldResovler(FieldResolver fieldResolver) {
		FieldResolveScopePOJO[] fieldResolveScopes = fieldResolver.getResolveScopes();
		if (ArrayUtils.isEmpty(fieldResolveScopes)) {
			throw new FieldResolverException(fieldResolver + "没有指定解析范围");
		}
		for (FieldResolveScopePOJO fieldResolveScope : fieldResolveScopes) {
			Class<? extends Modelable> modelClass = fieldResolveScope.getModelClass();
			String[] fieldNames = fieldResolveScope.getFieldNames();
			if (ArrayUtils.isEmpty(fieldNames)) {
				FieldResolver _fieldResolver = model_fieldResolverMap.get(modelClass);
				if (null != _fieldResolver) {
					throw new FieldResolverException(
							fieldResolver + " 指定的模型类型 " + modelClass + " 已经存在解析器 " + _fieldResolver);
				} else {
					model_fieldResolverMap.put(modelClass, fieldResolver);
				}
			} else {
				for (String fieldName : fieldNames) {
					ModelFieldNameEntry modelFieldNameEntry = new ModelFieldNameEntry(modelClass, fieldName);
					FieldResolver _fieldResolver = modelFieldName_fieldResolverMap.get(modelFieldNameEntry);
					if (null != _fieldResolver) {
						throw new FieldResolverException(fieldResolver + " 支持的解析类型 " + modelClass + "." + fieldName
								+ " 已经存在解析器 " + _fieldResolver);
					} else {
						modelFieldName_fieldResolverMap.put(modelFieldNameEntry, fieldResolver);
					}
				}
			}
		}
	}

	protected FieldAndColumn resolveFieldToFiledAndColumn(Class<? extends Modelable> modelClass, Field field) {
		return getOptimalFieldResolver(modelClass, field).resolve(modelClass, field);
	}

	@Override
	public List<FieldResolver> getAllFieldResolver() {
		List<FieldResolver> fieldResolvers = new ArrayList<FieldResolver>();
		fieldResolvers.addAll(model_fieldResolverMap.values());
		fieldResolvers.addAll(modelFieldName_fieldResolverMap.values());
		return fieldResolvers.stream().distinct().collect(Collectors.toList());
	}

	@Override
	public boolean existFieldResolver() {
		return !model_fieldResolverMap.isEmpty() || !modelFieldName_fieldResolverMap.isEmpty();
	}

}

/**
 * 
 */
package org.yelong.core.model.pojo.annotation;

import java.lang.reflect.Field;

import org.yelong.core.model.Modelable;
import org.yelong.core.model.annotation.support.ModelFieldAnnotation;
import org.yelong.core.model.annotation.support.ModelFieldAnnotations;
import org.yelong.core.model.manage.AbstractFieldAndColumn;
import org.yelong.core.model.manage.FieldAndColumn;
import org.yelong.core.model.manage.support.DefaultFieldAndColumn;
import org.yelong.core.model.manage.wrapper.FieldAndColumnWrapper;
import org.yelong.core.model.pojo.field.FieldResolveException;
import org.yelong.core.model.pojo.field.FieldResolveScope;
import org.yelong.core.model.pojo.field.FieldResolver;

/**
 * 注解字段解析器。解析范围：所有模型下面的字段
 * 
 * @since 2.0
 */
@FieldResolveScope(value = Modelable.class)
public class AnnotationFieldResolver implements FieldResolver {

	@Override
	public FieldAndColumn resolve(Class<? extends Modelable> modelClass, Field field) throws FieldResolveException {
		ModelFieldAnnotation modelFieldAnnotation = new ModelFieldAnnotation(modelClass, field);
		AbstractFieldAndColumn fieldAndColumn = new DefaultFieldAndColumn(field, modelFieldAnnotation.getColumnCode());
		ModelFieldAnnotations.setProperty(fieldAndColumn, modelFieldAnnotation);
		Class<? extends FieldAndColumnWrapper> fieldAndColumnWrapperClass = modelFieldAnnotation
				.getFieldAndColumnWrapperClass();
		if (null != fieldAndColumnWrapperClass) {
			try {
				return FieldAndColumnWrapper.newInstance(fieldAndColumnWrapperClass, fieldAndColumn);
			} catch (Exception e) {
				String fieldName = fieldAndColumn.getFieldName();
				throw new FieldResolveException(modelClass, field, fieldName + "包装失败，实例化" + fieldAndColumnWrapperClass
						+ "错误，包装器类必须存在单一参数类型[" + FieldAndColumn.class + "]的构造方法！", e);
			}
		}
		return fieldAndColumn;
	}

}

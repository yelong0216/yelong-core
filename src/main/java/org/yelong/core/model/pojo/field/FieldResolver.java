/**
 * 
 */
package org.yelong.core.model.pojo.field;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.yelong.commons.lang.annotation.AnnotationUtilsE;
import org.yelong.core.model.Modelable;
import org.yelong.core.model.manage.FieldAndColumn;

/**
 * 字段解析器,将字段解析为框架中所使用的对象 {@link FieldAndColumn}。 每个字段解析器实现类都要指名自己所能解析的模型、字段范围。
 * “范围”表示自己能解析什么类型的模型、字段
 * 
 * @since 2.0
 */
public interface FieldResolver {

	/**
	 * 解析字段
	 * 
	 * @param modelClass 解析的模型
	 * @param field      解析的模型的字段
	 * @return 字段列
	 */
	FieldAndColumn resolve(Class<? extends Modelable> modelClass, Field field) throws FieldResolveException;

	/**
	 * 获取该解析器的解析范围。 这个解析范围可以指定某个模型或者某型模型中指定的字段
	 * 
	 * @return 解析的范围
	 */
	default FieldResolveScopePOJO[] getResolveScopes() {
		List<FieldResolveScope> fieldScopeList = new ArrayList<>();
		fieldScopeList.add(AnnotationUtilsE.getAnnotation(getClass(), FieldResolveScope.class, true));
		FieldResolveScopes fieldScopes = AnnotationUtilsE.getAnnotation(getClass(), FieldResolveScopes.class, true);
		if (null != fieldScopes) {
			FieldResolveScope[] values = fieldScopes.value();
			for (FieldResolveScope fieldScope : values) {
				fieldScopeList.add(fieldScope);
			}
		}
		fieldScopeList.removeIf(x -> x == null);
		if (fieldScopeList.isEmpty()) {
			throw new FieldResolverException(this + "没有指定解析范围！");
		}
		FieldResolveScopePOJO[] fieldResolveScopes = new FieldResolveScopePOJO[fieldScopeList.size()];
		for (int i = 0; i < fieldScopeList.size(); i++) {
			FieldResolveScope fieldScope = fieldScopeList.get(i);
			fieldResolveScopes[i] = new FieldResolveScopePOJO(fieldScope.value(), fieldScope.fieldNames());
		}
		return fieldResolveScopes;
	}

}

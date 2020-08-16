/**
 * 
 */
package org.yelong.core.model.resolve;

import org.yelong.commons.lang.annotation.AnnotationUtilsE;
import org.yelong.core.model.Modelable;
import org.yelong.core.model.manage.ModelAndTable;

/**
 * 模型解析器，将model解析为框架中所使用的对象 {@link ModelAndTable}。 每个模型解析器实现类都要指名自己所能解析的模型范围。
 * “范围”表示自己能解析什么类型的模型
 * 
 * @see Modelable
 * @see ModelAndTable
 * @see #getResolveScopes()
 * @since 2.0
 */
public interface ModelResolver {

	/**
	 * 解析模型
	 * 
	 * @param modelClass model class
	 * @return 模型与表映射
	 * @throws ModelResolveException 不能解析的模型或者解析失败
	 */
	ModelAndTable resolve(Class<? extends Modelable> modelClass) throws ModelResolveException;

	/**
	 * 获取自己所能解析的模型范围，及自己可以解析那些类型的模型。
	 * 
	 * 可以重写此方法替换从注解{@link ModelResolveScope}获取模型范围
	 * 
	 * @return 可以解析的模型类型数组
	 * @see ModelResolveScope
	 * @see ModelResolverManager
	 */
	default Class<? extends Modelable>[] getResolveScopes() {
		ModelResolveScope supportModelType = AnnotationUtilsE.getAnnotation(getClass(), ModelResolveScope.class, true);
		if (null == supportModelType) {
			throw new ModelResolverException(this + "没有指定解析的模型范围");
		}
		return supportModelType.value();
	}

}

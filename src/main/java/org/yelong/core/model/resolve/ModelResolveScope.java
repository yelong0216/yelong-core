/**
 * 
 */
package org.yelong.core.model.resolve;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.yelong.core.model.Modelable;

@Documented
@Retention(RUNTIME)
@Target(TYPE)
/**
 * 模型解析范围。声明在模型解析器的实现类上。这个注释应用于声明的类及其子类。注意，它不适用于类层次结构中的祖先类
 * 
 * @since 2.0
 */
public @interface ModelResolveScope {

	/**
	 * 模型解析器的解析范围。这个范围中的类型应用于指定的类型和其类型的子类。
	 * 
	 * @return 解析范围
	 */
	Class<? extends Modelable>[] value();

}

/**
 * 
 */
package org.yelong.core.model.pojo.field;

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
 * 字段解析器的解析范围
 * 
 * @since 2.0
 */
public @interface FieldResolveScope {

	/**
	 * 支持解析的模型
	 * 
	 * @return 可以解析的模型
	 */
	Class<? extends Modelable> value();

	/**
	 * 该值为空值，默认支持解析 {@link #value()}模型中的所有字段。 不为空时为{@link #value()}模型中字段名称为指定字段的字段
	 * 
	 * @return 指定模型中可以解析的字段名称数组
	 */
	String[] fieldNames() default {};

}

/**
 * 
 */
package org.yelong.core.model.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.yelong.core.model.Models;

@Retention(RUNTIME)
@Target(FIELD)
/**
 * 
 * 指定列是否进行映射。
 */
public @interface SelectColumnConditionalOnProperty {

	/**
	 * Alias for {@link #name()}
	 * 
	 * @return the names
	 */
	String value() default "";

	/**
	 * 列测试的属性名称。此属性在验证时通过{@link Models#getProperty(String)}去获取<br/>
	 * 这个名称默认值为字段名称
	 * 
	 * @return the names
	 */
	String name() default "";

	/**
	 * 属性期望值的字符串表示形式。如果没有指定，属性必须不等于false
	 * 
	 * @return the expected value
	 */
	String havingValue() default "true";

	/**
	 * 如果属性不存在，是否应该映射
	 * 
	 * @return 如果属性不存在，是否应该映射
	 */
	boolean matchIfMissing() default false;

}

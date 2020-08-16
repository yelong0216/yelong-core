/**
 * 
 */
package org.yelong.core.model.annotation;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Documented
@Retention(RUNTIME)
@Target({})
/**
 * 字段列包装指派的映射
 * 
 * @since 2.0
 * @see FieldAndColumnWrapperAppoints
 */
public @interface FACWrapperAppointMap {

	/**
	 * 指定被包装的字段名称
	 * 
	 * @return 需要被包装的字段名称
	 */
	String fieldName();

	/**
	 * 指派的字段列包装器
	 * 
	 * @return 被指派的字段列包装器
	 */
	FACWrapperAppoint appoint();

}

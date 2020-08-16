/**
 * 
 */
package org.yelong.core.model.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.yelong.core.model.manage.wrapper.FieldAndColumnWrapper;

@Documented
@Retention(RUNTIME)
@Target(FIELD)
/**
 * 指定字段的字段列包装器
 * 
 * @since 2.0
 * @see FieldAndColumnWrapper
 */
public @interface FACWrapperAppoint {

	/**
	 * 指定包装此字段列的包装器类型
	 * 
	 * @return 包装此字段列的包装器类型
	 */
	Class<? extends FieldAndColumnWrapper> value();

}

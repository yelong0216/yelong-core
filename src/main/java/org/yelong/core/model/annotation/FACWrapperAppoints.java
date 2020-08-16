/**
 * 
 */
package org.yelong.core.model.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Documented
@Retention(RUNTIME)
@Target(TYPE)
/**
 * 一般用在子类对父类的字段列进行包装的功能。指定字段列的包映射。该注解应用在类上。
 * 
 * @since 2.0
 * @see FieldAndColumnWrapperAppoint
 * @see FieldAndColumnWrapperAppointMap
 */
public @interface FACWrapperAppoints {

	FACWrapperAppointMap[] value();

}

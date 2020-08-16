/**
 * 
 */
package org.yelong.core.model.pojo.field;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Documented
@Retention(RUNTIME)
@Target(TYPE)
/**
 * 可以指定多个字段解析范围
 * 
 * @since 2.0
 */
public @interface FieldResolveScopes {

	FieldResolveScope[] value();

}

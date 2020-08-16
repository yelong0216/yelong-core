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
 * 标注在 model 类型上面，指定当前类及其父类中指定字段名称的字段为主键列。在类型的层级关系上面，该注解不会影响下级类，只影响上级类
 * 功能上该注解可以被覆盖（及子类标注后父类标注的将不起作用）
 * 
 * @since 2.0
 * @see PrimaryKey
 */
public @interface PrimaryKeys {

	/**
	 * @return 主键的字段名称数组
	 */
	String[] value() default {};

}

/**
 * 
 */
package org.yelong.core.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.CLASS;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Documented
@Retention(CLASS)
@Target({ FIELD, PARAMETER ,METHOD})
/**
 * 这只是一个标注属性。
 * 1、被标注的方法参数可以为null
 * 2、被标注的属性值可能为null
 * 3、被标注的方法返回值可能为null
 * @author 彭飞
 * @date 2019年10月21日下午4:15:33
 * @version 1.2
 */
public @interface Nullable {

	String value () default "";
	
}

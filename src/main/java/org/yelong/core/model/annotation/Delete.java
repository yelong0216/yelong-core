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
 * 标注在model上面。<br/>
 * 替换默认的删除SQL
 * 
 * @since 2.1.4
 */
public @interface Delete {

	/**
	 * @return 删除SQL语句。默认根据不同数据方言来设置
	 */
	String value() default "";

}

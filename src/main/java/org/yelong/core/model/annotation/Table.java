/**
 * 
 */
package org.yelong.core.model.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target({ TYPE })
/**
 * 映射的表名
 * @author PengFei
 */
public @interface Table {
	
	/**
	 * @return 表名称
	 */
	String value();
	
	/**
	 * 别名
	 * 默认值：model类名称首字母小写
	 * @return 表别名
	 */
	String alias() default "";
	
	/**
	 * 描述
	 * @return 表描述
	 */
	String desc() default "";
	
}

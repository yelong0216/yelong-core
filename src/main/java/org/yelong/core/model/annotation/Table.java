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
 * @author 彭飞
 * @date 2019年8月1日上午11:54:51
 * @version 1.0
 */
public @interface Table {
	
	/**
	 * 表名称
	 * @author 彭飞
	 * @date 2019年8月1日上午11:57:18
	 * @version 1.0
	 * @return
	 */
	String value();
	
	
	/**
	 * 别名
	 * 默认值：model类名称首字母小写
	 * @author 彭飞
	 * @date 2019年8月1日上午11:57:36
	 * @version 1.0
	 * @return
	 */
	String alias() default "";
	
	/**
	 * 描述
	 * @author 彭飞
	 * @date 2019年9月27日下午5:57:50
	 * @version 1.2
	 * @return
	 */
	String desc() default "";
	
	
}

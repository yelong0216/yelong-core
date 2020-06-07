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
 * 标注在model上面。
 * 替换默认的 select sql
 * 
 * @author PengFei
 */
public @interface Select {

	/**
	 * 查询语句。默认： select alias.* from tableName alias
	 * 
	 * @return select sql
	 */
	String value() default "";
	
}

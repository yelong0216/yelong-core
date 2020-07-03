/**
 * 
 */
package org.yelong.core.model.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(TYPE)
/**
 * 标注在model上面。 替换默认的查询记录语句
 * 
 * @author PengFei
 */
public @interface Count {

	/**
	 * 查询记录的sql。默认： select count(alias.*) from tableName alias
	 * 
	 * @return count sql
	 */
	String value() default "";

}

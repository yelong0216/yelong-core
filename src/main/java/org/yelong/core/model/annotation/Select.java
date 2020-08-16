/**
 * 
 */
package org.yelong.core.model.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.yelong.core.model.sql.SelectSqlColumnMode;

@Documented
@Retention(RUNTIME)
@Target(TYPE)
/**
 * 标注在model上面。<br/>
 * 替换默认的 select sql
 */
public @interface Select {

	/**
	 * 查询语句。默认： select alias.* from tableName alias
	 * 
	 * @return select sql
	 */
	String value() default "";

	/**
	 * 指定模型的查询列模式
	 * 
	 * @return 查询列模式
	 * @since 2.0
	 */
	SelectSqlColumnMode columnMode() default SelectSqlColumnMode.STAR;
	
}

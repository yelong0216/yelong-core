/**
 * 
 */
package org.yelong.core.model.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Documented
@Retention(RUNTIME)
@Target(FIELD)
/**
 * 通过该注解修改查询映射时此字段与列的映射名称，以及是否进行映射
 * 
 * @author PengFei
 */
public @interface SelectColumn {

	/**
	 * 查询时，此字段映射的列名称。默认为{@link Column#value()}
	 * 
	 * @return 列名称
	 */
	String value() default "";
	
	/**
	 * 与 value功能相同，优先级高于 {@link #value()}
	 * 
	 * @return 列名称
	 */
	String column() default "";
	
	/**
	 * 是否在查询后映射到model的字段上。
	 * 
	 * @return <tt>true</tt> 映射
	 */
	boolean mapping() default true;
}

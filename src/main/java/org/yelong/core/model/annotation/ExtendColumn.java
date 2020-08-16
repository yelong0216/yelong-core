/**
 * 
 */
package org.yelong.core.model.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.yelong.core.model.Modelable;

@Retention(RUNTIME)
@Target(FIELD)
/**
 * 拓展列<br/>
 * 该列仅在查询时映射。<br/>
 * 此不映射仅包含增删改。其查询可以映射。
 */
public @interface ExtendColumn {

	/**
	 * 默认的 model class
	 */
	Class<? extends Modelable> DEFAULT_MODEL_CLASS = Modelable.class;

	/**
	 * 表示该拓展列所属的model
	 * 
	 * @return 该列所属的 model
	 * @see #modelClass()
	 */
	Class<? extends Modelable> value() default Modelable.class;

	/**
	 * 表示该拓展列所属的model
	 * 
	 * @return 该列所属的model
	 * @see #value()
	 */
	Class<? extends Modelable> modelClass() default Modelable.class;

	/**
	 * 表示该列所属的表名
	 * 
	 * @return 该列所属的表名
	 */
	String tableName() default "";

	/**
	 * 
	 * 表示该列所属的表的别名
	 * 
	 * @return 该列所属的表的别名
	 */
	String tableAlias() default "";

}

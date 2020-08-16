/**
 * 
 */
package org.yelong.core.model.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 标注字段映射的列名<br/>
 * 如果为空或者没有注释该注解的字段将默认字段名称为列名称
 */
@Retention(RUNTIME)
@Target({ FIELD })
public @interface Column {

	/**
	 * 映射的列名称<br/>
	 * 默认为字段名称
	 * 
	 * @return 列名
	 */
	String value() default "";

	/**
	 * 列名称 与{@link #value()}相同。<br/>
	 * 优先级大于{@link #value()} <br/>
	 * 默认列名称为字段名称
	 * 
	 * @return 列名
	 */
	String column() default "";

	/**
	 * 列描述名称
	 * 
	 * @return 列描述名称 如： name:姓名,age:年龄
	 */
	String columnName() default "";

	/**
	 * 指定列的最大长度，这个数值只有在正数且大于0时才会生效
	 * 
	 * @return 列的最大长度
	 */
	long maxLength() default -1;

	/**
	 * 指定列的最小长度，这个数值只有在正数时才会生效
	 * 
	 * @return 列的最小长度
	 */
	long minLength() default -1;

	/**
	 * 是否允许为空。<br/>
	 * 此属性仅对字符类型生效。 <br/>
	 * 不支持为空白字符时，默认不支持为null。（此属性为false时，allowNull也为false）
	 * 
	 * @return <tt>true</tt> 允许为空白字符
	 */
	boolean allowBlank() default true;

	/**
	 * 是否允许为null
	 * 
	 * @return <tt>true</tt> 允许为null
	 */
	boolean allowNull() default true;

	/**
	 * 列描述
	 * 
	 * @return 描述
	 */
	String desc() default "";

	/**
	 * 字段映射的jdbc数据类型
	 * 
	 * @return jdbc数据类型 。如VARCHAR等
	 */
	String jdbcType() default "";

}

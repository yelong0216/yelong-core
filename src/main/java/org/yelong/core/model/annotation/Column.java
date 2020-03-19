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
 * @author 彭飞
 * @date 2019年8月1日上午11:53:12
 * @version 1.0
 */
@Retention(RUNTIME)
@Target({ FIELD })
public @interface Column {
	
	/**
	 * 映射的列名称<br/>
	 * 默认为字段名称
	 * @author 彭飞
	 * @date 2019年8月1日上午11:54:19
	 * @version 1.0
	 * @return
	 */
	String value() default "";
	
	/**
	 * 列名称 与{@link #value()}相同。优先级大于{@link #value()}
	 * 默认列名称为字段名称
	 * @author 彭飞
	 * @date 2019年9月27日下午5:59:42
	 * @version 1.2
	 * @return
	 */
	String column() default "";
	
	/**
	 * 列名称
	 * @return column name 如： name 为姓名
	 */
	String columnName() default "";
	
	/**
	 * 字段允许的最大长度
	 * @author 彭飞
	 * @date 2019年9月27日下午6:03:00
	 * @version 1.2
	 * @return
	 */
	long maxLength() default Integer.MAX_VALUE;
	
	/**
	 * 字段允许的最小长度
	 * @author 彭飞
	 * @date 2019年9月27日下午6:03:40
	 * @version 1.2
	 * @return
	 */
	long minLength() default 0;
	
	/**
	 * 是否允许为空。
	 * 此属性仅对字符类型生效。
	 * 不支持为空白字符时，默认不支持为null。（此属性为false时，allowNull也为false）
	 * @author 彭飞
	 * @date 2019年9月27日下午6:04:25
	 * @version 1.2
	 * @return
	 */
	boolean allowBlank() default true;
	
	/**
	 * 是否允许为null
	 * @author 彭飞
	 * @date 2019年9月27日下午6:05:12
	 * @version 1.2
	 * @return
	 */
	boolean allowNull() default true;

	/**
	 * 列描述
	 * @author 彭飞
	 * @date 2019年9月29日下午12:04:21
	 * @version 1.2
	 * @return
	 */
	String desc() default "";
	
	/**
	 * 字段映射的jdbc数据类型
	 * @author 彭飞
	 * @date 2019年9月29日下午12:04:42
	 * @version 1.2
	 * @return
	 */
	String jdbcType() default "";
	
	
}

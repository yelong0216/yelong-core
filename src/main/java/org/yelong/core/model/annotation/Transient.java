/**
 * 
 */
package org.yelong.core.model.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target({ TYPE, FIELD })
/**
 * 标注的字段不进行映射
 * 这个属性会直接排除，不映射。
 * 此注解优先级最高
 * @author 彭飞
 * @date 2019年8月1日上午11:52:44
 * @version 1.0
 */
public @interface Transient {
	
	
	
}

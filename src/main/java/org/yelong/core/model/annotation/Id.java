/**
 * 
 */
package org.yelong.core.model.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target({ FIELD })
/**
 * 标注这个字段列是id
 * 注意：id字段默认不允许为null和空白值
 * @author 彭飞
 * @date 2019年8月1日下午2:01:19
 * @version 1.0
 */
public @interface Id {

}

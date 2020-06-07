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
 * 拓展列忽略
 * 这个注解会排除 ExtendTable 注解解析为的拓展列， 使列进行正常的映射,而非一个拓展列进行映射
 * 不会影响 ExtendColumn 。或者可以说该注解比 ExtendColumn 注解优先级低
 * 
 * @author PengFei
 * @since 1.2.0
 */
public @interface ExtendColumnIgnore {

}

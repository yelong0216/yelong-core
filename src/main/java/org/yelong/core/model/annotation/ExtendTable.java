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
 * 拓展表声明,表声明的类中所有进行映射的字段均视为拓展列<br/>
 * 
 * 在类级别上，该注解默认应用于声明类。注意，它不适用类层次结构上的子类和父类；
 * 
 * 
 * @since 1.2
 * @see ExtendColumn
 * @see ExtendColumnIgnore
 */
public @interface ExtendTable {

}

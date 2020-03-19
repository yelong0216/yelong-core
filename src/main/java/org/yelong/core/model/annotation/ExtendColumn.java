/**
 * 
 */
package org.yelong.core.model.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(FIELD)
/**
 * 拓展列
 * 该列仅在查询时映射。
 * 此不映射仅包含增删改。其查询可以映射。
 * @author PengFei
 */
public @interface ExtendColumn {
	
}

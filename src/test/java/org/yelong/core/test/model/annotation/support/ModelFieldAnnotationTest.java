/**
 * 
 */
package org.yelong.core.test.model.annotation.support;

import java.lang.reflect.Field;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.yelong.core.model.annotation.support.ModelFieldAnnotation;
import org.yelong.core.test.model.UserDTO;

/**
 * @since
 */
public class ModelFieldAnnotationTest {

	public static void main(String[] args) {
		Field field = FieldUtils.getField(UserDTO.class, "creatorRealName", true);
		ModelFieldAnnotation modelFieldAnnotation = new ModelFieldAnnotation(UserDTO.class, field);
		System.out.println(modelFieldAnnotation.isTransient());
		ModelFieldAnnotation modelFieldAnnotation1 = new ModelFieldAnnotation(UserDTO.class, FieldUtils.getField(UserDTO.class, "username", true));
		System.out.println(modelFieldAnnotation1.isTransient());
	}
	
	
}
